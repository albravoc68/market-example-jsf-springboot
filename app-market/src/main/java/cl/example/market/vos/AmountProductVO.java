package cl.example.market.vos;

import cl.example.entities.entities.vo.ProductVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmountProductVO {
    private ProductVO product;
    private int amount;

    public int getTotal() {
        return amount * product.getPrice();
    }
}