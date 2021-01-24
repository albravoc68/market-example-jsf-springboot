package cl.example.dashboard.beans;

import cl.example.entities.entities.TransactionProductEntity;
import cl.example.entities.repositories.ProductRepository;
import cl.example.entities.repositories.TransactionProductRepository;
import cl.example.entities.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Scope("view")
public class DashboardBean extends BaseBean {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionProductRepository transactionProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Getter
    private Integer products;

    @Getter
    private Integer transactions;

    @Getter
    private Integer profits;

    @Getter
    private Integer users;

    @Getter
    private Integer usersEnable;

    @Getter
    private Integer usersDisable;

    @Getter
    private List<TransactionProductEntity> txps;

    @PostConstruct
    public void init() {
        products = productRepository.countByClient_Id(getClientId());

        txps = transactionProductRepository.findById_Transaction_Client_Id(getClientId());
        transactions = txps.size();
        profits = txps.stream().mapToInt(TransactionProductEntity::getTotal).sum();

        users = userRepository.countByClient_Id(getClientId());
        usersEnable = userRepository.countByClient_IdAndEnable(getClientId(), true);
        usersDisable = users - usersEnable;
    }

}
