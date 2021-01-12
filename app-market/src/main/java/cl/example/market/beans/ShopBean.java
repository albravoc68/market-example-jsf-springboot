package cl.example.market.beans;

import cl.example.entities.domain.entities.vo.ProductVO;
import cl.example.market.MarketConfigurationProperties;
import cl.example.market.services.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Scope("view")
public class ShopBean {

    @Autowired
    private ProductService productService;

    @Autowired
    private MarketConfigurationProperties props;

    @Getter
    private List<ProductVO> products;

    @PostConstruct
    public void init() {
        products = productService.getProducts(props.getClientId());
    }

}
