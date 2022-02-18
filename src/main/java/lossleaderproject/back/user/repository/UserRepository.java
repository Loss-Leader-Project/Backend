package lossleaderproject.back.user.repository;

import lossleaderproject.back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    User findByUserName(String userName);


}
