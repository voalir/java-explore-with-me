package ru.practicum.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.repository.CustomEventRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomEventRepositoryImpl implements CustomEventRepository {


    private final EntityManager entityManager;

    @Autowired
    public CustomEventRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Event> getEventsByAdminFilter(List<Long> users, List<EventState> states, List<Long> categories,
                                              LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (users != null && users.size() > 0) {
            predicates.add(eventRoot.get("initiator").in(users));
        }
        if (states != null && states.size() > 0) {
            predicates.add(eventRoot.get("state").in(states));
        }
        if (categories != null && categories.size() > 0) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        if (rangeStart != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(eventRoot.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(eventRoot.get("eventDate"), rangeEnd));
        }
        query.select(eventRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(criteriaBuilder.asc(eventRoot.get("eventDate")));
        return entityManager.createQuery(query).setFirstResult(from).setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Event> getEventsByPublicFilter(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (text != null) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("title")), "%" + text.toLowerCase() + "%"),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("annotation")), "%" + text.toLowerCase() + "%"),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("description")), "%" + text.toLowerCase() + "%")
            ));
        }
        if (categories != null && categories.size() > 0) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        if (rangeStart != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(eventRoot.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(eventRoot.get("eventDate"), rangeEnd));
        }
        if (paid != null) {
            predicates.add(criteriaBuilder.equal(eventRoot.get("paid"), paid));
        }
        query.select(eventRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(criteriaBuilder.asc(eventRoot.get("eventDate")));
        return entityManager.createQuery(query).setFirstResult(from).setMaxResults(size)
                .getResultList();
    }

}
