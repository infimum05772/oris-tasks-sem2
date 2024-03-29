package ru.kpfu.itis.arifulina.repository;

import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.arifulina.model.User;
import ru.kpfu.itis.arifulina.model.User_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Getter
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    @Setter
    private EntityManager entityManager;
    @Override
    public List<User> findAllByNameMatch(String name, double factor) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.distinct(true);
        Predicate predicate = criteriaBuilder.isNotNull(root.get(User_.name));

        query.where(predicate);
        query.orderBy(criteriaBuilder.asc(root.get(User_.name)));
        return entityManager.createQuery(query).getResultList();
    }
}
