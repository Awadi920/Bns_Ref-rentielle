package com.bns.bnsref.Service;

import com.bns.bnsref.dto.LanguageDTO;

import java.util.*;

public interface LanguageService {
    LanguageDTO addLanguage(LanguageDTO languageDTO);
    LanguageDTO updateLanguage(String codeLanguage, LanguageDTO languageDTO);
    void deleteLanguage(String codeLanguage);
    LanguageDTO getLanguageById(String codeLanguage);
    List<LanguageDTO> getAllLanguages();
}
