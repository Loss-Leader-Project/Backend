package lossleaderproject.back.user.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.UserLoginIdFindRequest;
import lossleaderproject.back.user.dto.UserLoginIdResponse;
import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.dto.UserResponse;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.exception.ErrorCode;
import lossleaderproject.back.user.exception.UserCustomException;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

}
