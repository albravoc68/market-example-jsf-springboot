package cl.example.market.payments;

import cl.example.entities.entities.ClientEntity;
import cl.example.entities.entities.ProductEntity;
import cl.example.entities.entities.TransactionEntity;
import cl.example.entities.entities.TransactionProductEntity;
import cl.example.entities.entities.UserEntity;
import cl.example.entities.repositories.TransactionProductRepository;
import cl.example.entities.repositories.TransactionRepository;
import cl.example.entities.entities.enums.PaymentMethod;
import cl.example.market.payments.impl.WebpayPlusRP;
import cl.example.market.payments.vos.PostVO;
import cl.example.market.vos.AmountProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private WebpayPlusRP webpayPlusRP;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionProductRepository transactionProductRepository;

    private IRemotePayment resolve(PaymentMethod paymentMethod) {
        if (paymentMethod == PaymentMethod.WEBPAYPLUS) {
            return webpayPlusRP;
        }
        throw new RuntimeException("Payment method " + paymentMethod + " not supported!");
    }

    public int createTransaction(int clientId, int userId, Collection<AmountProductVO> products, PaymentMethod paymentMethod) {
        TransactionEntity tx = new TransactionEntity();
        tx.setClient(entityManager.getReference(ClientEntity.class, clientId));
        tx.setCreationDatetime(new Date());
        tx.setResolve(false);
        tx.setPaymentMethod(paymentMethod);
        tx.setUser(entityManager.getReference(UserEntity.class, userId));
        tx = transactionRepository.saveAndFlush(tx);

        ArrayList<TransactionProductEntity> tps = new ArrayList<>();
        for (AmountProductVO ap : products) {
            TransactionProductEntity.TransactionProductPK pk =
                    new TransactionProductEntity.TransactionProductPK();
            pk.setProduct(entityManager.getReference(ProductEntity.class, ap.getProduct().getId()));
            pk.setTransaction(tx);

            TransactionProductEntity tp = new TransactionProductEntity();
            tp.setAmount(ap.getAmount());
            tp.setUnitPrice(ap.getProduct().getPrice());
            tp.setId(pk);

            tps.add(tp);
        }
        transactionProductRepository.saveAll(tps);

        return tx.getId();
    }

    public PostVO getPaymentPost(int transactionId, String redirectBaseUrl) {
        List<TransactionProductEntity> tps = transactionProductRepository.findById_Transaction_Id(transactionId);
        int total = tps.stream().mapToInt(tp -> tp.getAmount() * tp.getUnitPrice()).sum();

        TransactionEntity tx = getTransaction(transactionId);
        return resolve(tx.getPaymentMethod()).getPaymentPost(tx.getUser().getId(), transactionId, total, redirectBaseUrl);
    }

    public void resolvePayment(int transactionId) {
        TransactionEntity tx = getTransaction(transactionId);
        tx.setResolve(true);
        transactionRepository.saveAndFlush(tx);
    }

    public void validateTransaction(int transactionId, int userId) {
        TransactionEntity tx = getTransaction(transactionId);
        if (tx.getUser().getId() != userId) {
            throw new RuntimeException("transaction does not correspond to user id " + userId);
        }
    }

    public PaymentMethod getTransactionPaymentMethod(int transactionId) {
        return getTransaction(transactionId).getPaymentMethod();
    }

    private TransactionEntity getTransaction(int transactionId) {
        Optional<TransactionEntity> otx = transactionRepository.findById(transactionId);
        if (!otx.isPresent()) {
            throw new RuntimeException("Cannot find transaction id " + transactionId);
        }
        return otx.get();
    }
}
