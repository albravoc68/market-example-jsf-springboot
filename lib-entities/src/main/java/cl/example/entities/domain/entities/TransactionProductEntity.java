package cl.example.entities.domain.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "_transaction_product")
@Getter
@Setter
public class TransactionProductEntity {

    @EmbeddedId
    private TransactionProductPK id;

    @EqualsAndHashCode
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class TransactionProductPK implements Serializable {

        public static final long serialVersionUID = 1;

        @ManyToOne
        @JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false)
        private TransactionEntity transaction;

        @ManyToOne
        @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
        private ProductEntity product;

    }

    @Basic
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Basic
    @Column(name = "unit_price", length = 11, nullable = false)
    private Integer unitPrice;

    public int getTotal() {
        return amount * unitPrice;
    }

}
