package com.bns.bnsref.Mappers;


import com.bns.bnsref.dto.CodeListDTO;
import com.bns.bnsref.Entity.Category;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Domain;
import com.bns.bnsref.Entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class CodeListMapper {

    public CodeList toEntity(CodeListDTO dto, Domain domain, Category category, Producer producer) {
        return CodeList.builder()
                .labelList(dto.getLabelList())
                .description(dto.getDescription())
                .domain(domain)
                .category(category)
                .producer(producer)
                .creationDate(dto.getCreationDate()) // Directement LocalDateTime
                .build();
    }

    public CodeListDTO toDTO(CodeList entity) {
        return CodeListDTO.builder()
                .codeList(entity.getCodeList())
                .labelList(entity.getLabelList())
                .description(entity.getDescription())
                .creationDate(entity.getCreationDate())  // Assure-toi qu'on la récupère bien
                .domainCode(entity.getDomain() != null ? entity.getDomain().getCodeDomain() : null)
                .codeCategory(entity.getCategory() != null ? entity.getCategory().getCodeCategory() : null)
                .producerCode(entity.getProducer() != null ? entity.getProducer().getCodeProd() : null)
                .build();
    }

}

