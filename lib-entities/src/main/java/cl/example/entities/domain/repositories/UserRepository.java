package cl.example.entities.domain.repositories;

import cl.example.entities.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByIdAndClient_Id(int userId, int clientId);

    UserEntity findByUsernameAndClient_Id(String username, int clientId);

    Integer countByClient_Id(int clientId);

    Integer countByClient_IdAndEnable(int clientId, boolean enable);

}
