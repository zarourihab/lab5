package com.example.repository;

import com.example.model.Salle;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalleRepositoryImpl implements SalleRepository {

    private EntityManager entityManager;

    public SalleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end) {
        String jpql = "SELECT DISTINCT s FROM Salle s WHERE s.id NOT IN " +
                "(SELECT r.salle.id FROM Reservation r " +
                "WHERE (r.dateDebut <= :end AND r.dateFin >= :start))";

        return entityManager.createQuery(jpql, Salle.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    @Override
    public List<Salle> findByCriteria(Map<String, Object> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Salle> query = cb.createQuery(Salle.class);
        Root<Salle> salle = query.from(Salle.class);

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "nom":
                    predicates.add(cb.like(salle.get("nom"), "%" + value + "%"));
                    break;
                case "capaciteMin":
                    predicates.add(cb.greaterThanOrEqualTo(salle.get("capacite"), (Integer) value));
                    break;
                case "capaciteMax":
                    predicates.add(cb.lessThanOrEqualTo(salle.get("capacite"), (Integer) value));
                    break;
            }
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Salle> findAllPaginated(int page, int size) {
        return entityManager.createQuery("SELECT s FROM Salle s ORDER BY s.id", Salle.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(s) FROM Salle s", Long.class)
                .getSingleResult();
    }

    @Override
    public Salle findById(Long id) {
        return entityManager.find(Salle.class, id);
    }

    @Override
    public List<Salle> findAll() {
        return entityManager.createQuery("SELECT s FROM Salle s", Salle.class)
                .getResultList();
    }

    @Override
    public void save(Salle salle) {
        entityManager.getTransaction().begin();
        entityManager.persist(salle);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Salle salle) {
        entityManager.getTransaction().begin();
        entityManager.merge(salle);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Salle salle) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(salle) ? salle : entityManager.merge(salle));
        entityManager.getTransaction().commit();
    }
}