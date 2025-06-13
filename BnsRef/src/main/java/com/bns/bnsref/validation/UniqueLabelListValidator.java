package com.bns.bnsref.validation;


import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.dao.CodeListDAO;
import jakarta.validation.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueLabelListValidator implements ConstraintValidator<UniqueLabelList, String> {

    @Autowired
    private CodeListDAO codeListDAO;

    private String codeListId;

    public void setCodeListId(String codeListId) {
        this.codeListId = codeListId;
    }

    @Override
    public void initialize(UniqueLabelList constraintAnnotation) {
        // Initialisation si nécessaire
    }

    @Override
    public boolean isValid(String labelList, ConstraintValidatorContext context) {
        if (labelList == null) {
            return true; // Gérer les cas où le champ est nullable
        }
        Optional<CodeList> existingCodeList = codeListDAO.findByLabelList(labelList);
        return existingCodeList.isEmpty() ||
                (codeListId != null && existingCodeList.get().getCodeList().equals(codeListId));
    }
}