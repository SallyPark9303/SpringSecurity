package springCom.springJWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springCom.springJWT.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);


}
