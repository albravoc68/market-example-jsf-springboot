package cl.example.market.beans;

import cl.example.market.models.ShoppingCartModel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;

@Component
@SessionScope
public class SessionBean extends BaseBean {

    @Getter
    private ShoppingCartModel shoppingCart;

    @PostConstruct
    public void init() {
        cleanShoppingCart();
    }

    public void cleanShoppingCart() {
        shoppingCart = new ShoppingCartModel();
    }

}
