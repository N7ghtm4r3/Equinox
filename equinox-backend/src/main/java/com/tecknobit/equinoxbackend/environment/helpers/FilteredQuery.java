package com.tecknobit.equinoxbackend.environment.helpers;

import com.tecknobit.equinoxcore.annotations.Structure;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code FilteredQuery<T>} class is an abstract helper designed to facilitate the dynamic creation and execution of filtered
 * queries using JPA Criteria API. It is structured to simplify filtering, querying, and pagination of entities from a
 * database while allowing custom predicates to be added dynamically by subclasses
 *
 * @param <T> Type of the entities to retrieve
 * @author Tecknobit - N7ghtm4r3
 * @since 1.0.5
 */
@Structure
public abstract class FilteredQuery<T> {

    /**
     * {@code entityManager} manage the entities
     */
    protected final EntityManager entityManager;

    /**
     * {@code rawFilters} the filters all together in raw format
     */
    protected final CopyOnWriteArraySet<String> rawFilters;

    /**
     * {@code criteriaBuilder} the builder to add the criteria to filter the query
     */
    protected final CriteriaBuilder criteriaBuilder;

    /**
     * {@code query} the criteria prepared query to execute
     */
    protected final CriteriaQuery<T> query;

    /**
     * {@code root} the root table
     */
    protected final Root<T> root;

    /**
     * {@code predicates} the list of predicates to apply to the query
     */
    protected final ArrayList<Predicate> predicates;

    /**
     * Constructor to init the {@link FilteredQuery}
     *
     * @param entityType    The type of the entity to retrieve
     * @param entityManager Manage the entities
     * @param rawFilters    The filters all together in raw format
     */
    public FilteredQuery(Class<T> entityType, EntityManager entityManager, Set<String> rawFilters) {
        this.entityManager = entityManager;
        this.rawFilters = new CopyOnWriteArraySet<>(rawFilters);
        criteriaBuilder = entityManager.getCriteriaBuilder();
        query = criteriaBuilder.createQuery(entityType);
        root = query.from(entityType);
        predicates = new ArrayList<>();
    }

    /**
     * Method used to get the paginated entities executing the query
     *
     * @param pageable The pageable value to use to paginate the query
     * @return the entities retrieved from database as {@link List} of {@link T}
     */
    public List<T> getEntities(Pageable pageable) {
        prepareQuery();
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        return typedQuery.getResultList();
    }

    /**
     * Method used to get the entities executing the query
     *
     * @return the entities retrieved from database as {@link List} of {@link T}
     */
    public List<T> getEntities() {
        if (predicates.isEmpty())
            prepareQuery();
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    /**
     * Method to prepare the query adding the predicates
     */
    private void prepareQuery() {
        fillPredicates();
        query.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    }

    /**
     * Wrapper method to fill all the predicates dynamically
     */
    @Wrapper
    protected abstract void fillPredicates();

    /**
     * Method to extract from the {@link #rawFilters} the specific set of filters
     *
     * @param pattern The pattern to use to extract the specific set
     * @return the specific filters as {@link HashSet} of {@link String}
     */
    protected HashSet<String> extractFiltersByPattern(Pattern pattern) {
        HashSet<String> filtersList = new HashSet<>();
        for (String filter : rawFilters) {
            Matcher matcher = pattern.matcher(filter);
            if (matcher.matches()) {
                filtersList.add(filter.trim());
                rawFilters.remove(filter);
            }
        }
        if (filtersList.isEmpty())
            return null;
        return filtersList;
    }

}