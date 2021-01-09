package cl.example.dashboard.model;

import cl.example.entities.domain.entities.ProductEntity;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductDataModel extends EntityLazyDataModel<ProductEntity> {

    @Autowired
    private EntityManager em;

    public ProductDataModel() {
        super(ProductEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Setter
    private Integer clientId;

    @Override
    protected List<Predicate> applyCustomQuery(CriteriaBuilder cb, CriteriaQuery<?> query, Root<ProductEntity> root) {
        if (true) {
            return null;
        }
        List<Predicate> predicates = new ArrayList<>();

        if (clientId != null) {
            Predicate p = cb.equal(root.get("id").as(Integer.class), clientId);
            predicates.add(p);
        }

        return Collections.singletonList(cb.and(predicates.toArray(new Predicate[0])));
    }

}
