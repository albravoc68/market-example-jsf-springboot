package cl.example.dashboard.model;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class EntityLazyDataModel<T> extends LazyDataModel<T> {

    private final Class<T> entityClass;

    public EntityLazyDataModel(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected List<Predicate> applyCustomQuery(CriteriaBuilder cb, CriteriaQuery<?> query, Root<T> root) {
        return null;
    }

    protected EntityManager getEntityManager() {
        return null;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);

        Root<T> root = cq.from(entityClass);

        List<Predicate> ps = new ArrayList<>();
        if (!filters.isEmpty()) {
            for (Map.Entry<String, FilterMeta> e : filters.entrySet()) {
                String key = e.getKey().trim();
                String val = String.valueOf(e.getValue()).trim();

                if (!StringUtils.isBlank(val)) {
                    ps.add(cb.like(createNestedPath(root, key).as(String.class), "%" + val + "%"));
                }
            }
        }

        List<Predicate> prds = applyCustomQuery(cb, cq, root);
        if (prds != null && !prds.isEmpty()) {
            ps.addAll(prds);
        }

        if (!ps.isEmpty()) {
            cq.where(cb.and(ps.toArray(new Predicate[0])));
        }

        if (!StringUtils.isEmpty(sortField)) {
            if (sortOrder == SortOrder.ASCENDING) {
                cq.orderBy(cb.asc(createNestedPath(root, sortField)));
            } else if (sortOrder == SortOrder.DESCENDING) {
                cq.orderBy(cb.desc(createNestedPath(root, sortField)));
            }
        }

        TypedQuery<T> q = getEntityManager().createQuery(cq.select(root));
        q.setMaxResults(pageSize);
        q.setFirstResult(first);

        List<T> list = q.getResultList();
        for (T t : list) {
            getEntityManager().detach(t);
        }

        int totalCount = (int) countTotal(cb);
        setRowCount(totalCount);
        return list;
    }

    private long countTotal(CriteriaBuilder cb) {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root));
        List<Predicate> ps = applyCustomQuery(cb, cq, root);
        if (ps != null && !ps.isEmpty()) {
            cq.where(ps.toArray(new Predicate[0]));
        }
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    protected Path<?> createNestedPath(Root<T> root, String key) {
        String[] props = key.split("\\.");
        Path path = root;
        for (String p : props) {
            path = path.get(p);
        }
        return path;
    }

}
