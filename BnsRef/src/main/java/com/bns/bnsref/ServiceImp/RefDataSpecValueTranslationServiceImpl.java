package com.bns.bnsref.ServiceImp;


import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dao.RefDataSpecValueTranslationDAO;
import com.bns.bnsref.dao.Ref_DataSpecValueDAO;
import com.bns.bnsref.dto.RefDataSpecValueTranslationDTO;
import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.Entity.Ref_DataSpecValueTranslation;
import com.bns.bnsref.Entity.Ref_DataSpecValue;
import com.bns.bnsref.Mappers.RefDataSpecValueTranslationMapper;
import com.bns.bnsref.Service.RefDataSpecValueTranslationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefDataSpecValueTranslationServiceImpl implements RefDataSpecValueTranslationService {

    private final RefDataSpecValueTranslationDAO refdataspecvaluetranslationdao;
    private final Ref_DataSpecValueDAO refDataSpecValueRepository;
    private final LanguageDAO languageRepository;
    private final RefDataSpecValueTranslationMapper mapper;

    @Override
    @Transactional
    public RefDataSpecValueTranslationDTO createRefDataSpecValueTranslation(RefDataSpecValueTranslationDTO dto) {
        if (dto == null || dto.getCodeRefDataSpecValue() == null || dto.getLanguageCode() == null) {
            throw new IllegalArgumentException("codeRefDataSpecValue and languageCode must not be null");
        }

        Ref_DataSpecValue refDataSpecValue = refDataSpecValueRepository.findById(dto.getCodeRefDataSpecValue())
                .orElseThrow(() -> new RuntimeException("RefDataSpecValue not found: " + dto.getCodeRefDataSpecValue()));

        Language language = languageRepository.findById(dto.getLanguageCode())
                .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));

        Ref_DataSpecValueTranslation translation = mapper.toEntity(dto);
        translation.setRefDataSpecValue(refDataSpecValue);
        translation.setLanguage(language);

        Ref_DataSpecValueTranslation savedTranslation = refdataspecvaluetranslationdao.save(translation);
        return mapper.toDTO(savedTranslation);
    }

    @Override
    @Transactional
    public RefDataSpecValueTranslationDTO updateRefDataSpecValueTranslation(Long id, RefDataSpecValueTranslationDTO dto) {
        Ref_DataSpecValueTranslation translation = refdataspecvaluetranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataSpecValueTranslation not found"));

        if (dto.getValue() != null) {
            translation.setValue(dto.getValue());
        }

        if (dto.getLanguageCode() != null) {
            Language language = languageRepository.findById(dto.getLanguageCode())
                    .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));
            translation.setLanguage(language);
        }

        Ref_DataSpecValueTranslation updatedTranslation = refdataspecvaluetranslationdao.save(translation);
        return mapper.toDTO(updatedTranslation);
    }

    @Override
    public RefDataSpecValueTranslationDTO getRefDataSpecValueTranslationById(Long id) {
        Ref_DataSpecValueTranslation translation = refdataspecvaluetranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataSpecValueTranslation not found"));
        return mapper.toDTO(translation);
    }

    @Override
    public List<RefDataSpecValueTranslationDTO> getAllRefDataSpecValueTranslations() {
        return refdataspecvaluetranslationdao.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteRefDataSpecValueTranslation(Long id) {
        if (!refdataspecvaluetranslationdao.existsById(id)) {
            throw new RuntimeException("RefDataSpecValueTranslation not found");
        }
        refdataspecvaluetranslationdao.deleteById(id);
    }
//    @Override
//    public RefDataSpecValueTranslation createRefDataSpecValueTranslation(RefDataSpecValueTranslation translation) {
//        Optional<Ref_DataSpecValue> refDataSpecValue = refDataSpecValueRepository.findById(translation.getRefDataSpecValue().getCodeRefDataSpecValue());
//        if (refDataSpecValue.isEmpty()) {
//            throw new RuntimeException("RefDataSpecValue not found");
//        }
//        translation.setRefDataSpecValue(refDataSpecValue.get());
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
//    @Override
//    public RefDataSpecValueTranslation updateRefDataSpecValueTranslation(Long id, RefDataSpecValueTranslation translation) {
//        Optional<RefDataSpecValueTranslation> existing = translationRepository.findById(id);
//        if (existing.isEmpty()) {
//            throw new RuntimeException("RefDataSpecValueTranslation not found");
//        }
//        RefDataSpecValueTranslation toUpdate = existing.get();
//        toUpdate.setValue(translation.getValue());
//        return translationRepository.save(toUpdate);
//    }
//
//    @Override
//    public RefDataSpecValueTranslation getRefDataSpecValueTranslationById(Long id) {
//        return translationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("RefDataSpecValueTranslation not found"));
//    }
//
//    @Override
//    public List<RefDataSpecValueTranslation> getAllRefDataSpecValueTranslations() {
//        return translationRepository.findAll();
//    }
//
//    @Override
//    public void deleteRefDataSpecValueTranslation(Long id) {
//        if (!translationRepository.existsById(id)) {
//            throw new RuntimeException("RefDataSpecValueTranslation not found");
//        }
//        translationRepository.deleteById(id);
//    }
}
