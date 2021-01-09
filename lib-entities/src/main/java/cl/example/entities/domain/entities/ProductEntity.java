package cl.example.entities.domain.entities;

import cl.example.entities.domain.entities.vo.ProductVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "_product")
@NoArgsConstructor
@Setter
@Getter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "_name", length = 40, nullable = false)
    private String name;

    @Basic
    @Column(name = "picture_url", columnDefinition = "text")
    private String pictureUrl;

    @Basic
    @Column(name = "price", length = 11, nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private ClientEntity client;

    public ProductVO toVO() {
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(this, vo);
        vo.setClientId(client.getId());

        return vo;
    }

}
