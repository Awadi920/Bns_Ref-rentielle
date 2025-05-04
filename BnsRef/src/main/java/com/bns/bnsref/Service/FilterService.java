package com.bns.bnsref.Service;

import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import com.bns.bnsref.dto.EntityCriteriaDTO;

import java.util.List;

public interface FilterService {
    Filter createFilter(Filter filter);
    void deleteFilter(Long filterId);
    SortCriteria createSortCriteria(SortCriteria sortCriteria);
    void deleteSortCriteria(Long sortCriteriaId);
    void clearAllCriteria(String entityName);

    EntityCriteriaDTO getAllCriteria(String entityName); // Nouvelle méthode

}