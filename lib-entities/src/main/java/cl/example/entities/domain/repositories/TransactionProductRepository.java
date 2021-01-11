package cl.example.entities.domain.repositories;

import cl.example.entities.domain.entities.TransactionProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionProductRepository  extends JpaRepository<TransactionProductEntity, Integer> {

    List<TransactionProductEntity> findById_Transaction_Id(int transactionId);

    List<TransactionProductEntity> findById_Product_Id(int productId);

    List<TransactionProductEntity> findById_Transaction_Client_Id(int clientId);

}
