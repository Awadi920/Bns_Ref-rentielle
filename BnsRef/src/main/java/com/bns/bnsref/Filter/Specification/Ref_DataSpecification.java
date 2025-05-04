package com.bns.bnsref.Filter.Specification;

import com.bns.bnsref.Entity.Ref_Data;
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
import java.util.ArrayList;
import java.util.List;
public class Ref_DataSpecification {

    private static final Logger logger = LoggerFactory.getLogger(Ref_DataSpecification.class);

    public static Specification<Ref_Data> applyFiltersAndSort(List<Filter> filters, List<SortCriteria> sortCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Filter filter : filters) {
                logger.debug("Applying filter: {}", filter);
                String[] fieldParts = filter.getFieldName().split("\\.");
                Path<?> path = root;
                for (String part : fieldParts) {
                    path = path.get(part);
                }
                switch (filter.getOperator()) {
                    case EQUALS:
                        predicates.add(criteriaBuilder.equal(path, filter.getValue()));
                        break;
                    case CONTAINS:
                        logger.debug("Applying CONTAINS filter on field {} with value {}", filter.getFieldName(), filter.getValue());
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(path.as(String.class)),
                                "%" + filter.getValue().toLowerCase() + "%"
                        ));
                        break;
                    case GREATER_THAN:
                        if (path.getJavaType() == LocalDateTime.class) {
                            predicates.add(criteriaBuilder.greaterThan(
                                    path.as(LocalDateTime.class),
                                    LocalDateTime.parse(filter.getValue())
                            ));
                        } else {
                            predicates.add(criteriaBuilder.greaterThan(
                                    path.as(String.class),
                                    filter.getValue()
                            ));
                        }
                        break;
                    default:
                        logger.warn("Unsupported operator: {}", filter.getOperator());
                }
            }

            List<Order> orders = new ArrayList<>();
            for (SortCriteria sort : sortCriteria) {
                logger.debug("Applying sort: {}", sort);
                String[] fieldParts = sort.getFieldName().split("\\.");
                Path<?> path = root;
                for (String part : fieldParts) {
                    path = path.get(part);
                }
                orders.add(sort.getDirection() == SortDirection.ASC ?
                        criteriaBuilder.asc(path) :
                        criteriaBuilder.desc(path));
            }

            query.orderBy(orders);
            return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
