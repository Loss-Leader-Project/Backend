package lossleaderproject.back.user.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.dto.UserResponse;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    // userId로 반환
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

    public UserResponse userInfoDetail(Long userId) {
        User user = userRepository.findById(userId).get();
        return new UserResponse(user.getLoginId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getBirthDate(), user.getRecommendedPerson());
    }

    public Long userInfoEdit(Long userId,UserResponse userResponse) {
        User user = userRepository.findById(userId).get();

        if(userResponse.getUserName() != null) {
            user.userInfoEditUserName(userResponse.getUserName());
            System.out.println("userResponse.getUserName() = " + userResponse.getUserName());
        }
        if(userResponse.getEmail() != null) {
            user.userInfoEditEmail(userResponse.getEmail());
        }
        if(userResponse.getBirthDate() != null) {
            user.userInfoEditBirthDate(userResponse.getBirthDate());
        }
        if(userResponse.getPhoneNumber() != null) {
            user.userInfoEditPhoneNumber(userResponse.getPhoneNumber());
        }
        if(userResponse.getRecommendedPerson() != null) {
            user.userInfoRecommendPerson(userResponse.getRecommendedPerson());
            if (user.getRecommendedPerson() != null) {
                user.recommendedMileage();
                User findRecommendLoginId = userRepository.findByLoginId(user.getRecommendedPerson());
                findRecommendLoginId.recommendedMileage();
            }
        }
        return user.getId();
    }


}
