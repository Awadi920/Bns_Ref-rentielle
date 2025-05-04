package com.bns.bnsref.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private String codeCategory;
    private String categoryName;
    private String categoryDescription;
}
