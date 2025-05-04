package com.bns.bnsref.Service;

import com.bns.bnsref.dto.RefDataSpecValueTranslationDTO;

import java.util.List;

public interface RefDataSpecValueTranslationService {

    RefDataSpecValueTranslationDTO createRefDataSpecValueTranslation(RefDataSpecValueTranslationDTO dto);
    RefDataSpecValueTranslationDTO updateRefDataSpecValueTranslation(Long id, RefDataSpecValueTranslationDTO dto);
    RefDataSpecValueTranslationDTO getRefDataSpecValueTranslationById(Long id);
    List<RefDataSpecValueTranslationDTO> getAllRefDataSpecValueTranslations();
    void deleteRefDataSpecValueTranslation(Long id);
}
