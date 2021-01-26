package cl.example.market.payments.impl;

import cl.example.market.payments.IRemotePayment;
import cl.example.market.payments.vos.PostVO;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNormal;
import cl.transbank.webpay.configuration.Configuration;
import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WebpayPlusRP implements IRemotePayment {

    private static WebpayNormal normalTransaction;

    private WebpayNormal getNormalTransaction() {
        if (normalTransaction == null) {
            try {
                normalTransaction = new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
            } catch (Exception e) {
                throw new RuntimeException("Impossible to get transaction from WebPay.");
            }
        }
        return normalTransaction;
    }

    @Override
    public PostVO getPaymentPost(int userId, int transactionId, int amount, String redirectBaseUrl) {
        String returnUrl = redirectBaseUrl + "/payment_confirm.xhtml?tx=" + transactionId;
        String finalUrl = redirectBaseUrl + "/index.xhtml";
        WsInitTransactionOutput initResult = getNormalTransaction().initTransaction(
                amount,
                String.valueOf(userId),
                String.valueOf(transactionId),
                returnUrl,
                finalUrl
        );

        String action = initResult.getUrl();
        String paymentToken = initResult.getToken();

        HashMap<String, String> params = new HashMap<>();
        params.put("token_ws", paymentToken);

        return new PostVO(action, params);
    }

    public TransactionResultOutput getTransactionResult(String tokenWs) {
        return getNormalTransaction().getTransactionResult(tokenWs);
    }

}
