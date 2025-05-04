package com.bns.bnsref.ServiceImp;


import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dao.RefDataSpecTranslationDAO;
import com.bns.bnsref.dao.Ref_DataSpecDAO;
import com.bns.bnsref.dto.RefDataSpecTranslationDTO;
import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.Entity.Ref_DataSpecTranslation;
import com.bns.bnsref.Entity.Ref_DataSpec;
import com.bns.bnsref.Mappers.RefDataSpecTranslationMapper;
import com.bns.bnsref.Service.RefDataSpecTranslationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefDataSpecTranslationServiceImpl implements RefDataSpecTranslationService {

    private final RefDataSpecTranslationDAO refdataspectranslationdao;
    private final Ref_DataSpecDAO refDataSpecDAO;
    private final LanguageDAO languageDAO;
    private final RefDataSpecTranslationMapper mapper;


    @Override
    @Transactional
    public RefDataSpecTranslationDTO createRefDataSpecTranslation(RefDataSpecTranslationDTO dto) {
        if (dto == null || dto.getCodeRefDataSpec() == null || dto.getLanguageCode() == null) {
            throw new IllegalArgumentException("codeRefDataSpec and languageCode must not be null");
        }

        Ref_DataSpec refDataSpec = refDataSpecDAO.findById(dto.getCodeRefDataSpec())
                .orElseThrow(() -> new RuntimeException("RefDataSpec not found: " + dto.getCodeRefDataSpec()));

        Language language = languageDAO.findById(dto.getLanguageCode())
                .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));

        Ref_DataSpecTranslation translation = mapper.toEntity(dto);
        translation.setRefDataSpec(refDataSpec);
        translation.setLanguage(language);

        Ref_DataSpecTranslation savedTranslation = refdataspectranslationdao.save(translation);
        return mapper.toDTO(savedTranslation);
    }

    @Override
    @Transactional
    public RefDataSpecTranslationDTO updateRefDataSpecTranslation(Long id, RefDataSpecTranslationDTO dto) {
        // Vérifier si l'entité existe
        Ref_DataSpecTranslation translation = refdataspectranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataSpecTranslation not found"));

        // Mettre à jour les champs designation et description s'ils sont fournis
        if (dto.getDesignation() != null) {
            translation.setDesignation(dto.getDesignation());
        }
        if (dto.getDescription() != null) {
            translation.setDescription(dto.getDescription());
        }

        // Mettre à jour la langue si languageCode est fourni
        if (dto.getLanguageCode() != null) {
            Language language = languageDAO.findById(dto.getLanguageCode())
                    .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));
            translation.setLanguage(language);
        }

        // Sauvegarder les modifications et retourner le DTO
        Ref_DataSpecTranslation updatedTranslation = refdataspectranslationdao.save(translation);
        return mapper.toDTO(updatedTranslation);
    }

    @Override
    public RefDataSpecTranslationDTO getRefDataSpecTranslationById(Long id) {
        Ref_DataSpecTranslation translation = refdataspectranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataSpecTranslation not found"));
        return mapper.toDTO(translation);
    }

    @Override
    public List<RefDataSpecTranslationDTO> getAllRefDataSpecTranslations() {
        return refdataspectranslationdao.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRefDataSpecTranslation(Long id) {
        if (!refdataspectranslationdao.existsById(id)) {
            throw new RuntimeException("RefDataSpecTranslation not found");
        }
        refdataspectranslationdao.deleteById(id);
    }

//    @Override
//    public RefDataSpecTranslation createRefDataSpecTranslation(RefDataSpecTranslation translation) {
//        Optional<Ref_DataSpec> refDataSpec = refDataSpecdao.findById(translation.getRefDataSpec().getCodeRefDataSpec());
//        if (refDataSpec.isEmpty()) {
//            throw new RuntimeException("RefDataSpec not found");
//        }
//        translation.setRefDataSpec(refDataSpec.get());
//
//        Optional<Language> language = languageRepository.findById(translation.getLanguage().getCodeLanguage());
//        if (language.isEmpty()) {
//            throw new RuntimeException("Language not found");
//        }
//        translation.setLanguage(language.get());
//
//        return translationRepository.save(translation);
//    }
//
//
//
//    @Override
//    public RefDataSpecTranslation updateRefDataSpecTranslation(Long id, RefDataSpecTranslation translation) {
//        Optional<RefDataSpecTranslation> existing = translationRepository.findById(id);
//        if (existing.isEmpty()) {
//            throw new RuntimeException("RefDataSpecTranslation not found");
//        }
//        RefDataSpecTranslation toUpdate = existing.get();
//        toUpdate.setDesignation(translation.getDesignation());
//        toUpdate.setDescription(translation.getDescription());
//        return translationRepository.save(toUpdate);
//    }
//
//    @Override
//    public RefDataSpecTranslation getRefDataSpecTranslationById(Long id) {
//        return translationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("RefDataSpecTranslation not found"));
//    }
//
//    @Override
//    public List<RefDataSpecTranslation> getAllRefDataSpecTranslations() {
//        return translationRepository.findAll();
//    }
//
//    @Override
//    public void deleteRefDataSpecTranslation(Long id) {
//        if (!translationRepository.existsById(id)) {
//            throw new RuntimeException("RefDataSpecTranslation not found");
//        }
//        translationRepository.deleteById(id);
//    }
}