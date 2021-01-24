package cl.example.entities.services;

import cl.example.entities.entities.TransactionProductEntity;
import cl.example.entities.repositories.TransactionProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionProductRepository transactionProductRepository;

    public List<TransactionProductEntity> getTransactionProducts(int transactionId) {
        return transactionProductRepository.findById_Transaction_Id(transactionId);
    }

}
