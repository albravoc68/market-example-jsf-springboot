package cl.example.market.beans;

import cl.example.market.payments.PaymentService;
import cl.example.market.payments.vos.PostVO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Map;

@Component
@Scope("view")
public class PaymentRedirectBean extends BaseBean {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SessionBean sessionBean;

    @Getter
    private PostVO paymentPost;

    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> paramMap = context.getRequestParameterMap();

        int txId = Integer.parseInt(paramMap.get("tx"));
        paymentService.validateTransaction(txId, getSessionUser().getId());

        paymentPost = paymentService.getPaymentPost(txId, getBaseUrl());
        sessionBean.cleanShoppingCart();
    }

}
