package cl.example.market.beans;

import cl.example.entities.entities.enums.PaymentMethod;
import cl.example.market.payments.PaymentService;
import cl.example.market.payments.impl.WebpayPlusRP;
import cl.example.market.payments.vos.PostVO;
import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.util.Collections;
import java.util.Map;

@Component
@Scope("view")
public class PaymentConfirmBean extends BaseBean {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WebpayPlusRP webpayPlusRP;

    @Getter
    private Integer txId;

    @Getter
    private Boolean success;

    @Getter
    private PostVO detailPost;

    @PostConstruct
    public void init() {
        detailPost = new PostVO();
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        txId = Integer.parseInt(paramMap.get("tx"));
        PaymentMethod pm = paymentService.getTransactionPaymentMethod(txId);
        switch (pm) {
            case WEBPAYPLUS:
                String tokenWs = paramMap.get("token_ws");
                TransactionResultOutput result = webpayPlusRP.getTransactionResult(tokenWs);
                WsTransactionDetailOutput output = result.getDetailOutput().get(0);
                if (output.getResponseCode() == 0) {
                    paymentService.resolvePayment(txId);
                    detailPost.setAction(result.getUrlRedirection());
                    detailPost.setParams(Collections.singletonMap("token_ws", tokenWs));
                    success = true;
                } else {
                    success = false;
                }
                break;
            default:
                success = false;
                break;
        }
    }

}
