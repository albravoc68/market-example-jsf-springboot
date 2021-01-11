package cl.example.dashboard.model;

import cl.example.entities.domain.entities.UserEntity;
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
public class UserDataModel extends EntityLazyDataModel<UserEntity> {

    @Autowired
    private EntityManager em;

    public UserDataModel() {
        super(UserEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Setter
    private Integer clientId;

    @Override
    protected List<Predicate> applyCustomQuery(CriteriaBuilder cb, CriteriaQuery<?> query, Root<UserEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (clientId != null) {
            Predicate p = cb.equal(createNestedPath(root, "client.id"), clientId);
            predicates.add(p);
        }

        return Collections.singletonList(cb.and(predicates.toArray(new Predicate[0])));
    }

}
