package lossleaderproject.back.user.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.security.auth.PrincipalDetails;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.user.dto.*;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.exception.ErrorCode;
import lossleaderproject.back.user.exception.UserCustomException;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder encoder;
    @Transactional
    public Long save(UserRequest userRequest, HttpSession session) {
        boolean emailNumber = false;
        String encoderPw = encoder.encode(userRequest.getPassword());
        User newUser = userRequest.toEntity();
        if(userRepository.existsByLoginId(userRequest.getLoginId())) {
            throw new UserCustomException(ErrorCode.DUPLICATE_ID);
        }
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new UserCustomException(ErrorCode.DISMATCH_PASSWORD);
        }
        if (newUser.getRecommendedPerson() != null) {
            if(userRepository.existsByLoginId(newUser.getRecommendedPerson()) == false) {
                throw new UserCustomException(ErrorCode.RECOMMENDED_USER_NOT_FOUND);
            }
            newUser.recommendedMileage();
            User findRecommendLoginId = userRepository.findByLoginId(newUser.getRecommendedPerson());
            findRecommendLoginId.recommendedMileage();
        }
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserCustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        if(session.getAttribute("success") == null || session.getAttribute("success").equals(false)) {
            throw new UserCustomException(ErrorCode.RECONFIRM_NUMBER);
        }else {
            emailNumber = true;
            session.invalidate();
        }
        if(emailNumber == false) {
            throw new UserCustomException(ErrorCode.RECONFIRM_NUMBER);
        }
        newUser.encodePassword(encoderPw);
        userRepository.save(newUser);
        return newUser.getId();
    }

    @Transactional(readOnly = true)
    public String checkLoginId(String loginId) {
        if(userRepository.existsByLoginId(loginId)) {
            throw new UserCustomException(ErrorCode.DUPLICATE_ID);
        }
        return "사용가능한 아이디 입니다.";
    }


    @Transactional(readOnly = true)
    public UserResponse.MyPageUserInfo userInfoDetail(PrincipalDetails principalDetails) {
        User user = userRepository.findByLoginId(principalDetails.getUsername());
        return new UserResponse.MyPageUserInfo(user.getLoginId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getBirthDate(), user.getRecommendedPerson(),user.getRole());
    }

    @Transactional(readOnly = true)
    public UserResponse.MyPageForOrderListing userInfoOrderPaging(PrincipalDetails principalDetails) {
        User user = userRepository.findByLoginId(principalDetails.getUsername());
        return new UserResponse.MyPageForOrderListing(user.getLoginId(), user.getUserName(), user.getMileage());
    }

    @Transactional
    public Long userInfoEdit(String loginId, UserResponse.MyPageUserInfo userResponse) {
        User user = userRepository.findByLoginId(loginId);
        if (userResponse.getUserName() != null) {
            user.userInfoEditUserName(userResponse.getUserName());
        }
        if (userResponse.getEmail() != null) {
            user.userInfoEditEmail(userResponse.getEmail());
        }
        if (userResponse.getBirthDate() != null) {
            user.userInfoEditBirthDate(userResponse.getBirthDate());
        }
        if (userResponse.getPhoneNumber() != null) {
            user.userInfoEditPhoneNumber(userResponse.getPhoneNumber());
        }
        if (userResponse.getRecommendedPerson() != null) {
            if(userRepository.existsByLoginId(userResponse.getRecommendedPerson()) == false) {
                throw new UserCustomException(ErrorCode.RECOMMENDED_USER_NOT_FOUND);
            }
            user.userInfoRecommendPerson(userResponse.getRecommendedPerson());

        }
        if (userResponse.getOldPassword() != null) {
            if (encoder.matches(userResponse.getOldPassword(), user.getPassword()) == false) {
                throw new UserCustomException(ErrorCode.DISMATCH_PASSWORD);
            }
        }
        if (userResponse.getNewPassword() != null && userResponse.getNewPasswordConfirm() != null) {
            if (userResponse.getNewPassword().equals(userResponse.getNewPasswordConfirm()) == false) {
                throw new UserCustomException(ErrorCode.DISMATCH_PASSWORD);
            }
            user.changePassword(encoder.encode(userResponse.getNewPassword()));
        }
        return user.getId();
    }

    public UserLoginIdResponse findLoginId(UserLoginIdFindRequest userLoginIdFindRequest) {

        if (userRepository.existsByUserNameAndBirthDateAndEmail(userLoginIdFindRequest.getUserName()
                , userLoginIdFindRequest.getBirthDate(),
                userLoginIdFindRequest.getEmail()) == false) {
            throw new UserCustomException(ErrorCode.NO_EXIST_USERNAME_BIRTHDATE_EMAIL);
        }
        String loginId = userRepository.findLoginId(userLoginIdFindRequest.getUserName(), userLoginIdFindRequest.getBirthDate(), userLoginIdFindRequest.getEmail());
        String replaceLoginId = loginId.replace(loginId.substring(loginId.length() - 3), "***");
        return new UserLoginIdResponse(replaceLoginId);
    }
    @Transactional
    public String findPassword(UserFindPassword userFindPassword) {

        if(userRepository.existsByLoginIdAndBirthDateAndEmail(userFindPassword.getLoginId(), userFindPassword.getBirthDate(), userFindPassword.getEmail())== false) {
            throw new UserCustomException(ErrorCode.NOT_EXIST_USER);
        }
        String password = randomPassword();
        User user = userRepository.findByLoginIdAndBirthDateAndEmail(userFindPassword.getLoginId(), userFindPassword.getBirthDate(), userFindPassword.getEmail());
        sendMail(userFindPassword.getEmail(),password);
        user.changePassword(password);
        return "임시 비밀번호 발송 완료";
    }

    public void sendMail(String email,String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("cousim55@gmail.com");
        message.setSubject("[LossLeader 비밀번호 찾기]");
        message.setText("[임시 비밀번호] :" + password);
        javaMailSender.send(message);

    }

    public String randomPassword() {
        String password = "";
        int randomNum = (int)(Math.random() * 200) + 1;
        for(int i = 0; i < 11; i++) {
            password += (char)((int)(Math.random() * 97)+40) ;
        }
        if(randomNum >= 100 && randomNum <= 150) {
            password += "$"+ randomNum;

        }else if(randomNum >150) {
            password += "%"+ randomNum;
        }
        else {
            password += randomNum +"&#";
        }
        return password;
    }
    // 리뷰 등록에 따른 유저 별점 갱신
    public void reviewPosting(Long userId, Float star){
        User user = userRepository.findById(userId).get();
        Integer newReviewCount = user.getReviewCount() + 1;
        Float newAvgStar = (user.getAvgStar() * user.getReviewCount() + star) / newReviewCount;
        user.setReviewCount(newReviewCount);
        user.setAvgStar(newAvgStar);
        userRepository.save(user);
    }


}
