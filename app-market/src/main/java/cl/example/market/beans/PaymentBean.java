package cl.example.market.beans;

import cl.example.market.payments.PaymentService;
import cl.example.entities.entities.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

@Component
@Scope("view")
public class PaymentBean extends BaseBean {

    @Autowired
    private SessionBean sessionBean;

    @Autowired
    private PaymentService paymentService;

    @Getter
    @Setter
    private PaymentMethod paymentMethod;

    @PostConstruct
    public void init() {
        paymentMethod = PaymentMethod.WEBPAYPLUS;
    }

    public void preparePaymentPage() throws IOException {
        int userId = getSessionUser().getId();
        int txId = paymentService.createTransaction(
                getClientId(),
                userId,
                sessionBean.getShoppingCart().getAmountProducts(),
                paymentMethod
        );

        String url = "/payment_redirect.xhtml?tx=" + txId;
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + url);
    }

}
