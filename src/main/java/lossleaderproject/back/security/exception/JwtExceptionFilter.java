package lossleaderproject.back.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lossleaderproject.back.security.jwt.Token;
import lossleaderproject.back.error.userException.UserErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            setErrorResponse(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE, response, e);
        } catch(NullPointerException e) {
            setUserNullPointException(UserErrorCode.BAD_REQUEST_LOGINID_PASSWORD.getHttpStatus(),response, UserErrorCode.BAD_REQUEST_LOGINID_PASSWORD.getDetail());
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        Token jwtExceptionResponse = new Token(419, ex.getMessage());
        String tokenException = mapper.writeValueAsString(jwtExceptionResponse);
        res.getWriter().write(tokenException);
    }

    public void setUserNullPointException(HttpStatus status, HttpServletResponse res, String message) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        Token jwtExceptionResponse = new Token(400, message);
        String tokenException = mapper.writeValueAsString(jwtExceptionResponse);
        res.getWriter().write(tokenException);
    }


}
