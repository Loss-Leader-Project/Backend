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
        User newUser = userRepository.save(userRequest.toEntity());
        return newUser.getId();

    }

    public boolean checkLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }


}
