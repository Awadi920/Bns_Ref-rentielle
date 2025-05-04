package com.bns.bnsref.dto;

import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityCriteriaDTO {
    private String entityName;
    private List<Filter> filters;
    private List<SortCriteria> sortCriteria;
}
