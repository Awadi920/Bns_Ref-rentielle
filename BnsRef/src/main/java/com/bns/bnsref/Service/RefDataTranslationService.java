package com.bns.bnsref.Service;

import com.bns.bnsref.dto.RefDataTranslationDTO;

import java.util.List;

public interface RefDataTranslationService {

    RefDataTranslationDTO createRefDataTranslation(RefDataTranslationDTO dto);
    RefDataTranslationDTO updateRefDataTranslation(Long id, RefDataTranslationDTO dto);
    RefDataTranslationDTO getRefDataTranslationById(Long id);
    List<RefDataTranslationDTO> getAllRefDataTranslations();
    void deleteRefDataTranslation(Long id);
}
