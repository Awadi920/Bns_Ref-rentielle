package com.bns.bnsref.Service;

import com.bns.bnsref.dto.CodeListTranslationDTO;

import java.util.List;

public interface CodeListTranslationService {

    CodeListTranslationDTO createCodeListTranslation(CodeListTranslationDTO dto);
    CodeListTranslationDTO updateCodeListTranslation(Long id, CodeListTranslationDTO dto);
    CodeListTranslationDTO getCodeListTranslationById(Long id);
    List<CodeListTranslationDTO> getAllCodeListTranslations();
    void deleteCodeListTranslation(Long id);
}
