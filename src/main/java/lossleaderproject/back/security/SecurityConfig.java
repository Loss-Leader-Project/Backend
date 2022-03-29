package lossleaderproject.back.security;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.security.exception.JwtExceptionFilter;
import lossleaderproject.back.security.jwt.JwtAuthenticationFilter;
import lossleaderproject.back.security.jwt.JwtAuthorizationFilter;
import lossleaderproject.back.security.oauth.provider.TokenProvider;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        http.formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),tokenProvider))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_KAKAO') or hasRole('ROLE_NAVER')")
                .antMatchers("/order/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_KAKAO') or hasRole('ROLE_NAVER')")
                .anyRequest().permitAll()
                .and()
                .logout(logout -> {
                    try {
                        logout
                                .permitAll()
                                .logoutSuccessHandler((request, response, authentication) -> {
                                            response.setStatus(HttpServletResponse.SC_OK);
                                        }
                                ).and()
                        .oauth2Login()
                        .loginPage("/login").and().logout().logoutUrl("/logouts");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
    }
}
