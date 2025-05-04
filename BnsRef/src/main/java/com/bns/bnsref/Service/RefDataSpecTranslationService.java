package com.bns.bnsref.Service;

import com.bns.bnsref.dto.RefDataSpecTranslationDTO;

import java.util.List;

public interface RefDataSpecTranslationService {

//    RefDataSpecTranslation createRefDataSpecTranslation(RefDataSpecTranslation translation);
//    RefDataSpecTranslation updateRefDataSpecTranslation(Long id, RefDataSpecTranslation translation);
//    RefDataSpecTranslation getRefDataSpecTranslationById(Long id);
//    List<RefDataSpecTranslation> getAllRefDataSpecTranslations();
//    void deleteRefDataSpecTranslation(Long id);

    RefDataSpecTranslationDTO createRefDataSpecTranslation(RefDataSpecTranslationDTO dto);
    RefDataSpecTranslationDTO updateRefDataSpecTranslation(Long id, RefDataSpecTranslationDTO dto);
    RefDataSpecTranslationDTO getRefDataSpecTranslationById(Long id);
    List<RefDataSpecTranslationDTO> getAllRefDataSpecTranslations();
    void deleteRefDataSpecTranslation(Long id);
}
