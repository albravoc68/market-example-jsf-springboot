package cl.example.entities.domain.repositories;

import cl.example.entities.domain.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    AdminEntity findByUsernameAndClient_Id(String username, int clientId);

}
