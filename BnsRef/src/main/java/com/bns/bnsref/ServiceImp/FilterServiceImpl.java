package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import com.bns.bnsref.Service.FilterService;
import com.bns.bnsref.dao.FilterRepository;
import com.bns.bnsref.dao.SortCriteriaRepository;
import com.bns.bnsref.dto.EntityCriteriaDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    private static final Logger logger = LoggerFactory.getLogger(FilterServiceImpl.class);

    private final FilterRepository filterRepository;
    private final SortCriteriaRepository sortCriteriaRepository;

    @Override
    @Transactional
    public Filter createFilter(Filter filter) {
        if (filter.getEntityName() == null || filter.getEntityName().trim().isEmpty()) {
            logger.error("Invalid entityName: {}", filter.getEntityName());
            throw new IllegalArgumentException("entityName cannot be null or empty");
        }

        List<Filter> existingFilters = filterRepository.findByEntityName(filter.getEntityName());
        logger.debug("Found {} existing filters for entity {}: {}", existingFilters.size(), filter.getEntityName(), existingFilters);
        if (!existingFilters.isEmpty()) {
            logger.info("Deleting {} existing filters for entity {}", existingFilters.size(), filter.getEntityName());
            filterRepository.deleteAll(existingFilters);
            filterRepository.flush();
        }

        List<SortCriteria> existingSortCriteria = sortCriteriaRepository.findByEntityName(filter.getEntityName());
        logger.debug("Found {} existing sort criteria for entity {}: {}", existingSortCriteria.size(), filter.getEntityName(), existingSortCriteria);
        if (!existingSortCriteria.isEmpty()) {
            logger.info("Deleting {} existing sort criteria for entity {}", existingSortCriteria.size(), filter.getEntityName());
            sortCriteriaRepository.deleteAll(existingSortCriteria);
            sortCriteriaRepository.flush();
        }

        logger.info("Creating new filter: {}", filter);
        Filter savedFilter = filterRepository.save(filter);
        logger.debug("Saved filter: {}", savedFilter);
        return savedFilter;
    }

    @Override
    @Transactional
    public SortCriteria createSortCriteria(SortCriteria sortCriteria) {
        if (sortCriteria.getEntityName() == null || sortCriteria.getEntityName().trim().isEmpty()) {
            logger.error("Invalid entityName: {}", sortCriteria.getEntityName());
            throw new IllegalArgumentException("entityName cannot be null or empty");
        }

        List<Filter> existingFilters = filterRepository.findByEntityName(sortCriteria.getEntityName());
        logger.debug("Found {} existing filters for entity {}: {}", existingFilters.size(), sortCriteria.getEntityName(), existingFilters);
        if (!existingFilters.isEmpty()) {
            logger.info("Deleting {} existing filters for entity {}", existingFilters.size(), sortCriteria.getEntityName());
            filterRepository.deleteAll(existingFilters);
            filterRepository.flush();
        }

        List<SortCriteria> existingSortCriteria = sortCriteriaRepository.findByEntityName(sortCriteria.getEntityName());
        logger.debug("Found {} existing sort criteria for entity {}: {}", existingSortCriteria.size(), sortCriteria.getEntityName(), existingSortCriteria);
        if (!existingSortCriteria.isEmpty()) {
            logger.info("Deleting {} existing sort criteria for entity {}", existingSortCriteria.size(), sortCriteria.getEntityName());
            sortCriteriaRepository.deleteAll(existingSortCriteria);
            sortCriteriaRepository.flush();
        }

        logger.info("Creating new sort criteria: {}", sortCriteria);
        SortCriteria savedSortCriteria = sortCriteriaRepository.save(sortCriteria);
        logger.debug("Saved sort criteria: {}", savedSortCriteria);
        return savedSortCriteria;
    }

    @Override
    public void deleteFilter(Long filterId) {
        logger.info("Deleting filter with ID {}", filterId);
        filterRepository.deleteById(filterId);
    }

    @Override
    public void deleteSortCriteria(Long sortCriteriaId) {
        logger.info("Deleting sort criteria with ID {}", sortCriteriaId);
        sortCriteriaRepository.deleteById(sortCriteriaId);
    }

    @Override
    @Transactional
    public void clearAllCriteria(String entityName) {
        if (entityName == null || entityName.trim().isEmpty()) {
            logger.error("Invalid entityName: {}", entityName);
            throw new IllegalArgumentException("entityName cannot be null or empty");
        }

        List<Filter> filters = filterRepository.findByEntityName(entityName);
        if (!filters.isEmpty()) {
            logger.info("Deleting {} filters for entity {}", filters.size(), entityName);
            filterRepository.deleteAll(filters);
        } else {
            logger.info("No filters found for entity {}", entityName);
        }

        List<SortCriteria> sortCriteria = sortCriteriaRepository.findByEntityName(entityName);
        if (!sortCriteria.isEmpty()) {
            logger.info("Deleting {} sort criteria for entity {}", sortCriteria.size(), entityName);
            sortCriteriaRepository.deleteAll(sortCriteria);
        } else {
            logger.info("No sort criteria found for entity {}", entityName);
        }
    }

    @Override
    public EntityCriteriaDTO getAllCriteria(String entityName) {
        if (entityName == null || entityName.trim().isEmpty()) {
            logger.error("Invalid entityName: {}", entityName);
            throw new IllegalArgumentException("entityName cannot be null or empty");
        }

        logger.info("Retrieving all criteria for entity {}", entityName);
        List<Filter> filters = filterRepository.findByEntityName(entityName);
        List<SortCriteria> sortCriteria = sortCriteriaRepository.findByEntityName(entityName);
        logger.debug("Retrieved {} filters and {} sort criteria for entity {}", filters.size(), entityName);
        return EntityCriteriaDTO.builder()
                .entityName(entityName)
                .filters(filters)
                .sortCriteria(sortCriteria)
                .build();
    }
}