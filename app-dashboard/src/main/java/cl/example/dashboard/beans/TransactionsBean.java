package cl.example.dashboard.beans;

import cl.example.dashboard.model.TransactionDataModel;
import cl.example.entities.services.TransactionService;
import cl.example.entities.entities.TransactionEntity;
import cl.example.entities.entities.TransactionProductEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Scope("view")
public class TransactionsBean extends BaseBean {

    @Autowired
    @Getter
    private TransactionDataModel transactionDM;

    @Autowired
    private TransactionService transactionService;

    @Getter
    @Setter
    private TransactionEntity selectedTransaction;

    private HashMap<Integer, List<TransactionProductEntity>> productsByTransaction;

    @PostConstruct
    public void init() {
        productsByTransaction = new HashMap<>();
        transactionDM.setClientId(getClientId());
    }

    public List<TransactionProductEntity> getProducts() {
        if (selectedTransaction == null) {
            return new ArrayList<>();
        }

        int transactionId = selectedTransaction.getId();
        if (productsByTransaction.containsKey(transactionId)) {
            return productsByTransaction.get(transactionId);
        }

        List<TransactionProductEntity> products = transactionService.getTransactionProducts(transactionId);
        productsByTransaction.put(transactionId, products);

        return products;
    }

}
