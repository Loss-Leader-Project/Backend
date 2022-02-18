package lossleaderproject.back.user.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.user.dto.UserRequest;
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
            System.out.println("findRecommendLoginId = " + findRecommendLoginId);
            System.out.println("커밋 2");
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


}
