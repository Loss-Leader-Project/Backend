package lossleaderproject.back.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.JwtException;
import lossleaderproject.back.security.auth.PrincipalDetails;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.error.userException.UserErrorCode;
import lossleaderproject.back.error.userException.UserCustomException;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || authorizationHeader.startsWith("Bearer")) {
                chain.doFilter(request, response);
                return;
            }
            String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
            String loginId = JWT.require(Algorithm.HMAC512("lossleader"))
                    .build()
                    .verify(jwtToken)
                    .getClaim("loginId")
                    .asString();
            if (loginId != null) {
                User userEntity = userRepository.findByLoginId(loginId);
                PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            throw new JwtException("토큰 기한 만료");
        } catch (NullPointerException e) {
            throw new UserCustomException(UserErrorCode.BAD_REQUEST_LOGINID_PASSWORD);
        }
    }
}
