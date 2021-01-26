package cl.example.market.payments;

import cl.example.market.payments.vos.PostVO;

public interface IRemotePayment {

    PostVO getPaymentPost(int userId, int transactionId, int amount, String redirectBaseUrl);

}
