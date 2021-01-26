package cl.example.market.models;

import cl.example.entities.entities.vo.ProductVO;
import cl.example.market.vos.AmountProductVO;

import java.util.Collection;
import java.util.HashMap;

public class ShoppingCartModel {

    private HashMap<Integer, AmountProductVO> amountByProductId;

    public ShoppingCartModel() {
        amountByProductId = new HashMap<>();
    }

    public boolean isEmpty() {
        return amountByProductId.isEmpty();
    }

    public Collection<AmountProductVO> getAmountProducts() {
        return amountByProductId.values();
    }

    public void addProductUnit(ProductVO product) {
        AmountProductVO ap = amountByProductId.get(product.getId());
        if (ap == null) {
            ap = new AmountProductVO(product, 1);
        } else {
            ap.setAmount(ap.getAmount() + 1);
        }
        amountByProductId.put(product.getId(), ap);
    }

    public void removeProductUnit(int productId) {
        AmountProductVO ap = amountByProductId.get(productId);
        if (ap != null) {
            ap.setAmount(ap.getAmount() - 1);
            if (ap.getAmount() <= 0) {
                amountByProductId.remove(productId);
            }
        }
    }

    public void removeProduct(int productId) {
        amountByProductId.remove(productId);
    }

    public int getAmountTotal() {
        int total = 0;
        for (HashMap.Entry<Integer, AmountProductVO> entry : amountByProductId.entrySet()) {
            AmountProductVO ap = entry.getValue();
            total += ap.getAmount();
        }
        return total;
    }

    public int getTotal() {
        int total = 0;
        for (HashMap.Entry<Integer, AmountProductVO> entry : amountByProductId.entrySet()) {
            AmountProductVO ap = entry.getValue();
            total += ap.getAmount() * ap.getProduct().getPrice();
        }
        return total;
    }

}
