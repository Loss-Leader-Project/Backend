package lossleaderproject.back.user.mail.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.exception.ErrorCode;
import lossleaderproject.back.user.exception.UserCustomException;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Transactional
    public String emailVerification(HttpSession session, int inputCode) {
        try {

            int emailVerification = (int) session.getAttribute("emailVerification");
            boolean verification = false;
            if (emailVerification == inputCode) {
                System.out.println("session = " + session.getAttribute("emailVerification"));
                verification = true;
                session.setAttribute("success",verification);

                return "인증 성공";
            }
            session.setAttribute("success",verification);
            return "인증 실패";
        } catch (NullPointerException e) {
            throw new UserCustomException(ErrorCode.EMAIL_SESSION_INVALIDATE);
        }
    }



    @Transactional
    public void sendMail(HttpSession session, String userEmail) {
        if(userRepository.existsByEmail(userEmail)) {
            throw new UserCustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        int numVerification = (int) (Math.random() * 99999) + 1;
        simpleMailMessage.setFrom("cousim55@gmail.com");
        simpleMailMessage.setSubject("[Lossleader 이메일 인증 번호] ");
        simpleMailMessage.setText("[인증 번호] : " + numVerification);
        javaMailSender.send(simpleMailMessage);
        session.setAttribute("emailVerification", numVerification);
        session.setMaxInactiveInterval(10*60);

    }
}
