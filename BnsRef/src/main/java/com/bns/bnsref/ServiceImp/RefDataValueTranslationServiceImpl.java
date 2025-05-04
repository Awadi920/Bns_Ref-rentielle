package com.bns.bnsref.ServiceImp;


import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dao.RefDataValueTranslationDAO;
import com.bns.bnsref.dao.Ref_DataValueDAO;
import com.bns.bnsref.dto.RefDataValueTranslationDTO;
import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.Entity.Ref_DataValueTranslation;
import com.bns.bnsref.Entity.Ref_DataValue;
import com.bns.bnsref.Mappers.RefDataValueTranslationMapper;
import com.bns.bnsref.Service.RefDataValueTranslationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefDataValueTranslationServiceImpl implements RefDataValueTranslationService {

    private final RefDataValueTranslationDAO refdatavaluetranslationdao;
    private final Ref_DataValueDAO refDataValueRepository;
    private final LanguageDAO languageRepository;
    private final RefDataValueTranslationMapper mapper;

    @Override
    @Transactional
    public RefDataValueTranslationDTO createRefDataValueTranslation(RefDataValueTranslationDTO dto) {
        if (dto == null || dto.getCodeRefDataValue() == null || dto.getLanguageCode() == null) {
            throw new IllegalArgumentException("codeRefDataValue and languageCode must not be null");
        }

        Ref_DataValue refDataValue = refDataValueRepository.findById(dto.getCodeRefDataValue())
                .orElseThrow(() -> new RuntimeException("RefDataValue not found: " + dto.getCodeRefDataValue()));

        Language language = languageRepository.findById(dto.getLanguageCode())
                .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));

        Ref_DataValueTranslation translation = mapper.toEntity(dto);
        translation.setRefDataValue(refDataValue);
        translation.setLanguage(language);

        Ref_DataValueTranslation savedTranslation = refdatavaluetranslationdao.save(translation);
        return mapper.toDTO(savedTranslation);
    }

    @Override
    @Transactional
    public RefDataValueTranslationDTO updateRefDataValueTranslation(Long id, RefDataValueTranslationDTO dto) {
        Ref_DataValueTranslation translation = refdatavaluetranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataValueTranslation not found"));

        if (dto.getValue() != null) {
            translation.setValue(dto.getValue());
        }
        if (dto.getLanguageCode() != null) {
            Language language = languageRepository.findById(dto.getLanguageCode())
                    .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));
            translation.setLanguage(language);
        }

        Ref_DataValueTranslation updatedTranslation = refdatavaluetranslationdao.save(translation);
        return mapper.toDTO(updatedTranslation);
    }

    @Override
    public RefDataValueTranslationDTO getRefDataValueTranslationById(Long id) {
        Ref_DataValueTranslation translation = refdatavaluetranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataValueTranslation not found"));
        return mapper.toDTO(translation);
    }

    @Override
    public List<RefDataValueTranslationDTO> getAllRefDataValueTranslations() {
        return refdatavaluetranslationdao.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteRefDataValueTranslation(Long id) {
        if (!refdatavaluetranslationdao.existsById(id)) {
            throw new RuntimeException("RefDataValueTranslation not found");
        }
        refdatavaluetranslationdao.deleteById(id);
    }
}
