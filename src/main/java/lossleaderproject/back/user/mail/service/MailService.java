package lossleaderproject.back.user.mail.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.security.oauth.provider.TokenProvider;
import lossleaderproject.back.user.exception.ErrorCode;
import lossleaderproject.back.user.exception.UserCustomException;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public String emailVerification(HttpServletRequest request, int number) {
        String emailVerificationToken = request.getHeader("EmailVerification");

        try {
            Jws<Claims> claimsJwts = Jwts.parser().setSigningKey("lossleader".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(emailVerificationToken);
            int emailNumber = (int) claimsJwts.getBody().get("emailNumber");
            if (emailNumber == number) {
                return "인증 성공";
            }
            return "인증 실패";
        } catch (NullPointerException e) {
            throw new UserCustomException(ErrorCode.EMAIL_SESSION_INVALIDATE);
        } catch (TokenExpiredException e) {
            throw new JwtException("토큰 기한 만료");
        }
    }


    @Transactional
    public void sendMail(HttpServletResponse response, String userEmail) {
        if (userRepository.existsByEmailAndRole(userEmail,"ROLE_USER")) {
            throw new UserCustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        int numVerification = (int) (Math.random() * 99999) + 1;
        simpleMailMessage.setFrom("cousim55@gmail.com");
        simpleMailMessage.setSubject("[Lossleader 이메일 인증 번호] ");
        simpleMailMessage.setText("[인증 번호] : " + numVerification);
        javaMailSender.send(simpleMailMessage);
        String emailToken = tokenProvider.emailCreate(numVerification);
        response.addHeader("EmailVerification", emailToken);

    }
}