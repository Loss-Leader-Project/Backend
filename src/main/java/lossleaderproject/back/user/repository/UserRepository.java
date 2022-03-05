package lossleaderproject.back.user.repository;

import lossleaderproject.back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    boolean existsByLoginIdAndBirthDateAndEmail(String loginId,String birthDate, String email);
    User findByUserName(String userName);

    User findByLoginIdAndBirthDateAndEmail(String loginId, String birthDate, String email);

    boolean existsByUserNameAndBirthDateAndEmail(String userName,String birthDate, String email);

    @Query("select u.loginId from User u where u.userName = :userName and u.birthDate = :birthDate and u.email = :email")
    String findLoginId(@Param("userName") String userName,@Param("birthDate") String birthDate,@Param("email") String email);

}
