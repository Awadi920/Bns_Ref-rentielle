package com.bns.bnsref.Filter.Specification;

import com.bns.bnsref.Entity.Ref_DataSpec;
import com.bns.bnsref.Filter.Enums.FilterOperator;
import com.bns.bnsref.Filter.Enums.SortDirection;
import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;


import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Ref_DataSpecSpecification {

    private static final Logger logger = LoggerFactory.getLogger(Ref_DataSpecSpecification.class);

    public static Specification<Ref_DataSpec> applyFiltersAndSort(List<Filter> filters, List<SortCriteria> sortCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            logger.info("Applying {} filters and {} sort criteria for Ref_DataSpec", filters.size(), sortCriteria.size());

            // Appliquer les filtres
            for (Filter filter : filters) {
                String fieldName = filter.getFieldName();
                String value = filter.getValue();
                FilterOperator operator = filter.getOperator();
                logger.debug("Processing filter: fieldName={}, operator={}, value={}", fieldName, operator, value);

                try {
                    Path<?> path;
                    if (fieldName.contains(".")) {
                        String[] parts = fieldName.split("\\.");
                        path = root.join(parts[0]).get(parts[1]);
                    } else {
                        path = root.get(fieldName);
                    }

                    switch (operator) {
                        case EQUALS:
                            predicates.add(criteriaBuilder.equal(path, value));
                            break;
                        case CONTAINS:
                            if (path.getJavaType() != String.class) {
                                logger.error("CONTAINS operator is only supported for String fields, got {}", path.getJavaType());
                                throw new IllegalArgumentException("CONTAINS operator is only supported for String fields");
                            }
                            logger.debug("Applying CONTAINS filter on field {} with value {}", fieldName, value);
                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(path.as(String.class)),
                                    "%" + value.toLowerCase() + "%"
                            ));
                            break;
                        case GREATER_THAN:
                            if (path.getJavaType() == LocalDateTime.class) {
                                predicates.add(criteriaBuilder.greaterThan(
                                        path.as(LocalDateTime.class), LocalDateTime.parse(value)));
                            } else {
                                predicates.add(criteriaBuilder.greaterThan(path.as(String.class), value));
                            }
                            break;
                        case LESS_THAN:
                            if (path.getJavaType() == LocalDateTime.class) {
                                predicates.add(criteriaBuilder.lessThan(
                                        path.as(LocalDateTime.class), LocalDateTime.parse(value)));
                            } else {
                                predicates.add(criteriaBuilder.lessThan(path.as(String.class), value));
                            }
                            break;
                        case NOT_EQUALS:
                            predicates.add(criteriaBuilder.notEqual(path, value));
                            break;
                        default:
                            logger.warn("Unsupported operator: {}", operator);
                            throw new IllegalArgumentException("Opérateur non supporté : " + operator);
                    }
                } catch (DateTimeParseException e) {
                    logger.error("Invalid date format for field {}: {}", fieldName, value, e);
                    throw new IllegalArgumentException("Format de date invalide pour " + fieldName + ": " + value);
                } catch (Exception e) {
                    logger.error("Error processing filter: fieldName={}, operator={}, value={}", fieldName, operator, value, e);
                    throw new IllegalArgumentException("Erreur lors du traitement du filtre: " + fieldName);
                }
            }

            // Appliquer le tri
            List<Order> orders = new ArrayList<>();
            for (SortCriteria sort : sortCriteria) {
                String fieldName = sort.getFieldName();
                logger.debug("Processing sort: fieldName={}, direction={}", fieldName, sort.getDirection());
                try {
                    Path<?> path;
                    if (fieldName.contains(".")) {
                        String[] parts = fieldName.split("\\.");
                        path = root.join(parts[0]).get(parts[1]);
                    } else {
                        path = root.get(fieldName);
                    }
                    orders.add(sort.getDirection() == SortDirection.ASC ?
                            criteriaBuilder.asc(path) :
                            criteriaBuilder.desc(path));
                } catch (Exception e) {
                    logger.error("Error processing sort: fieldName={}, direction={}", fieldName, sort.getDirection(), e);
                    throw new IllegalArgumentException("Erreur lors du traitement du tri: " + fieldName);
                }
            }
            query.orderBy(orders);

            return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}