package com.bns.bnsref.ServiceImp;


import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dao.RefDataTranslationDAO;
import com.bns.bnsref.dao.Ref_DataDAO;
import com.bns.bnsref.dto.RefDataTranslationDTO;
import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.Entity.Ref_DataTranslation;
import com.bns.bnsref.Entity.Ref_Data;
import com.bns.bnsref.Mappers.RefDataTranslationMapper;
import com.bns.bnsref.Service.RefDataTranslationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefDataTranslationServiceImpl implements RefDataTranslationService {

    private final RefDataTranslationDAO refdatatranslationdao;
    private final Ref_DataDAO refDataRepository;
    private final LanguageDAO languageRepository;
    private final RefDataTranslationMapper mapper;

    @Override
    @Transactional
    public RefDataTranslationDTO createRefDataTranslation(RefDataTranslationDTO dto) {
        if (dto == null || dto.getCodeRefData() == null || dto.getLanguageCode() == null) {
            throw new IllegalArgumentException("codeRefData and languageCode must not be null");
        }

        Ref_Data refData = refDataRepository.findById(dto.getCodeRefData())
                .orElseThrow(() -> new RuntimeException("RefData not found: " + dto.getCodeRefData()));

        Language language = languageRepository.findById(dto.getLanguageCode())
                .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));

        Ref_DataTranslation translation = mapper.toEntity(dto);
        translation.setRefData(refData);
        translation.setLanguage(language);

        Ref_DataTranslation savedTranslation = refdatatranslationdao.save(translation);
        return mapper.toDTO(savedTranslation);
    }

    @Override
    @Transactional
    public RefDataTranslationDTO updateRefDataTranslation(Long id, RefDataTranslationDTO dto) {
        Ref_DataTranslation translation = refdatatranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataTranslation not found"));

        if (dto.getDesignation() != null) {
            translation.setDesignation(dto.getDesignation());
        }
        if (dto.getDescription() != null) {
            translation.setDescription(dto.getDescription());
        }
        if (dto.getLanguageCode() != null) {
            Language language = languageRepository.findById(dto.getLanguageCode())
                    .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));
            translation.setLanguage(language);
        }

        Ref_DataTranslation updatedTranslation = refdatatranslationdao.save(translation);
        return mapper.toDTO(updatedTranslation);
    }

    @Override
    public RefDataTranslationDTO getRefDataTranslationById(Long id) {
        Ref_DataTranslation translation = refdatatranslationdao.findById(id)
                .orElseThrow(() -> new RuntimeException("RefDataTranslation not found"));
        return mapper.toDTO(translation);
    }

    @Override
    public List<RefDataTranslationDTO> getAllRefDataTranslations() {
        return refdatatranslationdao.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteRefDataTranslation(Long id) {
        if (!refdatatranslationdao.existsById(id)) {
            throw new RuntimeException("RefDataTranslation not found");
        }
        refdatatranslationdao.deleteById(id);
    }
}
