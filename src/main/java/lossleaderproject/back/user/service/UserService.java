package lossleaderproject.back.user.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.*;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.exception.ErrorCode;
import lossleaderproject.back.user.exception.UserCustomException;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public Long save(UserRequest userRequest) {
        User newUser = userRequest.toEntity();
        if (newUser.getRecommendedPerson() != null) {
            newUser.recommendedMileage();
            User findRecommendLoginId = userRepository.findByLoginId(newUser.getRecommendedPerson());
            findRecommendLoginId.recommendedMileage();
        }
        userRepository.save(newUser);
        return newUser.getId();

    }

    @Transactional(readOnly = true)
    public boolean checkLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Transactional(readOnly = true) // 추천인 검사
    public boolean checkRecommendedPerson(String recommendedPerson) {
        if (recommendedPerson != null) {
            return userRepository.existsByLoginId(recommendedPerson);
        }
        return true;
    }

    @Transactional(readOnly = true)
    public UserResponse userInfoDetail(Long userId) {
        User user = userRepository.findById(userId).get();
        return new UserResponse(user.getLoginId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getBirthDate(), user.getRecommendedPerson());
    }

    @Transactional
    public Long userInfoEdit(Long userId, UserResponse userResponse) {
        User user = userRepository.findById(userId).get();

        if (userResponse.getUserName() != null) {
            user.userInfoEditUserName(userResponse.getUserName());
            System.out.println("userResponse.getUserName() = " + userResponse.getUserName());
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
            user.userInfoRecommendPerson(userResponse.getRecommendedPerson());

        }
        if (userResponse.getNewPassword() != null && userResponse.getNewPasswordConfirm() != null) {
            user.changePassword(userResponse.getNewPassword());
        }
        System.out.println(user.getPassword());
        return user.getId();
    }

    @Transactional
    public boolean userInfoRePasswordOldCheck(Long userId, String oldPassword) {
        User user = userRepository.findById(userId).get();
        if (oldPassword != null) {
            if (user.getPassword().equals(oldPassword) == false) {
                return false;
            }
        }

        return true;
    }

    public boolean userInfoRePasswordNewCheck(Long userId, UserResponse userRePassword) {
        if (userRePassword.getNewPassword() != null && userRePassword.getNewPasswordConfirm() != null) {
            if (userRePassword.getNewPassword().equals(userRePassword.getNewPasswordConfirm()) == false) {
                return false;
            }
        }

        return true;
    }

    public UserLoginIdResponse findLoginId(UserLoginIdFindRequest userLoginIdFindRequest) {

        if (userRepository.existsByUserNameAndBirthDateAndEmail(userLoginIdFindRequest.getUserName()
                , userLoginIdFindRequest.getBirthDate(),
                userLoginIdFindRequest.getEmail()) == false) {
            throw new UserCustomException(ErrorCode.NO_EXIST_USERNAME_BIRTHDATE_EMAIL);
        }
        String loginId = userRepository.findLoginId(userLoginIdFindRequest.getUserName(), userLoginIdFindRequest.getBirthDate(), userLoginIdFindRequest.getEmail());
        System.out.println("loginId = " + loginId);
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


}
