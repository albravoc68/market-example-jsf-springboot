package cl.example.dashboard.beans;

import cl.example.entities.services.ProductService;
import cl.example.entities.entities.vo.ProductVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.util.Map;

@Component
@Scope("view")
public class CreateProductBean extends BaseBean {

    @Autowired
    private ProductService productService;

    @Getter
    @Setter
    private ProductVO product;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String val = params.get("productId");

        if (!StringUtils.isEmpty(val)) {
            int id = Integer.parseInt(val);
            product = productService.getProductById(id, getClientId());
        } else {
            product = new ProductVO();
            product.setClientId(getClientId());
        }
    }

    public void saveProduct() {
        try {
            product = productService.saveProduct(product);
            showInfo("El producto se ha guardado exitosamente!");
        } catch (Exception e) {
            showError("Error al guardar el producto: " + e.getMessage());
        }
    }

}
