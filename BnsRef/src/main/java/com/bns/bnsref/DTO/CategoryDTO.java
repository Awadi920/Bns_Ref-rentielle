package com.bns.bnsref.DTO;

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
