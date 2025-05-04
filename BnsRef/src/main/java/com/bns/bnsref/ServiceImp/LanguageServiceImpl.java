package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dto.LanguageDTO;
import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.Mappers.LanguageMapper;
import com.bns.bnsref.Service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageDAO languageDAO;
    private final LanguageMapper languageMapper;

    @Override
    public LanguageDTO addLanguage(LanguageDTO languageDTO) {
        // Vérifier si la Language existe déjà en fonction du code
        Optional<Language> existingLanguage = languageDAO.findById(languageDTO.getCodeLanguage());
        if (existingLanguage.isPresent()) {
            throw new RuntimeException("Language already exists with code: " + languageDTO.getCodeLanguage());
        }

        // Convertir le DTO en entité
        Language language = languageMapper.toEntity(languageDTO);
        languageDAO.save(language); // Sauvegarder l'entité dans la base de données

        // Retourner le DTO de la Language ajoutée
        return languageMapper.toDTO(language);
    }

    @Override
    public LanguageDTO updateLanguage(String codeLanguage, LanguageDTO languageDTO) {
        Language language = languageDAO.findById(codeLanguage)
                .orElseThrow(() -> new RuntimeException("Language not found"));
        language.setLanguageName(languageDTO.getLanguageName());
        return languageMapper.toDTO(languageDAO.save(language));
    }

    @Override
    public void deleteLanguage(String codeLanguage) {
        languageDAO.deleteById(codeLanguage);
    }

    @Override
    public LanguageDTO getLanguageById(String codeLanguage) {
        return languageMapper.toDTO(languageDAO.findById(codeLanguage)
                .orElseThrow(() -> new RuntimeException("Language not found")));
    }

    @Override
    public List<LanguageDTO> getAllLanguages() {
        return languageDAO.findAll().stream()
                .map(languageMapper::toDTO)
                .collect(Collectors.toList());
    }
}
