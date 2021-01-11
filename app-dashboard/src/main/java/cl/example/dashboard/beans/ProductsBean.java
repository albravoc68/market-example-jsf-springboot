package cl.example.dashboard.beans;

import cl.example.dashboard.model.ProductDataModel;
import cl.example.dashboard.services.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("view")
public class ProductsBean extends BaseBean {

    @Autowired
    @Getter
    private ProductDataModel productDM;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void init() {
        productDM.setClientId(getClientId());
    }

    public void deleteProduct(int productId) {
        try {
            productService.deleteProduct(productId, getClientId());
            showInfo("Producto borrado exitosamente!");
        } catch (Exception e) {
            showError("Imposible borrar producto si ya posee transacciones.");
        }
    }

}
