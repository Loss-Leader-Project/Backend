package lossleaderproject.back.user.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.exception.ErrorCode;
import lossleaderproject.back.user.exception.UserCustomException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public String emailVerification(HttpSession session, int inputCode) {
        try {
            int emailVerification = (int) session.getAttribute("emailVerification");
            if (emailVerification == inputCode) {
                System.out.println("session = " + session.getAttribute("emailVerification"));
                session.invalidate();
                return "인증 성공";
            }
            return "인증 실패";
        } catch (NullPointerException e) {
            throw new UserCustomException(ErrorCode.EMAIL_SESSION_INVALIDATE);
        }
    }


    public void sendMail(HttpSession session, String userEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        int numVerification = (int) (Math.random() * 99999) + 1;
        simpleMailMessage.setFrom("cousim55@gmail.com");
        simpleMailMessage.setSubject("[Lossleader 이메일 인증 번호] ");
        simpleMailMessage.setText("[인증 번호] : " + numVerification);
        javaMailSender.send(simpleMailMessage);
        session.setAttribute("emailVerification", numVerification);
        session.setMaxInactiveInterval(3*60);

    }
}
