package com.bns.bnsref.Service;


import com.bns.bnsref.dto.RefDataValueTranslationDTO;

import java.util.List;

public interface RefDataValueTranslationService {

    RefDataValueTranslationDTO createRefDataValueTranslation(RefDataValueTranslationDTO dto);
    RefDataValueTranslationDTO updateRefDataValueTranslation(Long id, RefDataValueTranslationDTO dto);
    RefDataValueTranslationDTO getRefDataValueTranslationById(Long id);
    List<RefDataValueTranslationDTO> getAllRefDataValueTranslations();
    void deleteRefDataValueTranslation(Long id);
}
