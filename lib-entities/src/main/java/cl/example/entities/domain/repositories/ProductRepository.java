package cl.example.entities.domain.repositories;

import cl.example.entities.domain.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    ProductEntity findByIdAndClient_Id(int productId, int clientId);

    List<ProductEntity> findByClient_IdAndEnable(int clientId, boolean enable);

    void deleteByIdAndClient_Id(int productId, int clientId);

    Integer countByClient_Id(int clientId);

}
