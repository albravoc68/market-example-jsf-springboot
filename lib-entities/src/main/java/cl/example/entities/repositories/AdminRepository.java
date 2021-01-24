package cl.example.entities.repositories;

import cl.example.entities.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    AdminEntity findByUsernameAndClient_Id(String username, int clientId);

}
