package com.bns.bnsref.ServiceImp;


import com.bns.bnsref.dao.CodeListDAO;
import com.bns.bnsref.dao.CodeListTranslationDAO;
import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dto.CodeListTranslationDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.CodeListTranslation;
import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.Mappers.CodeListTranslationMapper;
import com.bns.bnsref.Service.CodeListTranslationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeListTranslationServiceImpl implements CodeListTranslationService {

    private final CodeListTranslationDAO codeListTranslationDAO;
    private final CodeListDAO codeListDAO;
    private final LanguageDAO languageDAO;
    private final CodeListTranslationMapper codeListTranslationMapper;

    @Override
    @Transactional
    public CodeListTranslationDTO createCodeListTranslation(CodeListTranslationDTO dto) {
        if (dto == null || dto.getCodeListCode() == null || dto.getLanguageCode() == null) {
            throw new IllegalArgumentException("CodeListCode and LanguageCode must not be null");
        }

        CodeList codeList = codeListDAO.findById(dto.getCodeListCode())
                .orElseThrow(() -> new RuntimeException("CodeList not found: " + dto.getCodeListCode()));

        Language language = languageDAO.findById(dto.getLanguageCode())
                .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageCode()));

        CodeListTranslation translation = codeListTranslationMapper.toEntity(dto);
        translation.setCodeList(codeList);
        translation.setLanguage(language);

        CodeListTranslation savedTranslation = codeListTranslationDAO.save(translation);

        if (savedTranslation.getCodeList() == null) {
            throw new IllegalStateException("CodeList association is null after saving CodeListTranslation");
        }

        return codeListTranslationMapper.toDTO(savedTranslation);
    }

    @Override
    @Transactional
    public CodeListTranslationDTO updateCodeListTranslation(Long id, CodeListTranslationDTO dto) {
        Optional<CodeListTranslation> existing = codeListTranslationDAO.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("CodeListTranslation not found");
        }
        CodeListTranslation toUpdate = existing.get();

        // Mettre à jour les champs à partir du DTO
        toUpdate.setLabelList(dto.getLabelList());
        toUpdate.setDescription(dto.getDescription());

        // Sauvegarder l'entité mise à jour
        CodeListTranslation updatedTranslation = codeListTranslationDAO.save(toUpdate);

        // Retourner le DTO
        return codeListTranslationMapper.toDTO(updatedTranslation);
    }


    @Override
    public CodeListTranslationDTO getCodeListTranslationById(Long id) {
        CodeListTranslation translation = codeListTranslationDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("CodeListTranslation not found"));
        return codeListTranslationMapper.toDTO(translation);
    }

    @Override
    public List<CodeListTranslationDTO> getAllCodeListTranslations() {
        return codeListTranslationDAO.findAll().stream()
                .map(codeListTranslationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCodeListTranslation(Long id) {
        if (!codeListTranslationDAO.existsById(id)) {
            throw new RuntimeException("CodeListTranslation not found");
        }
        codeListTranslationDAO.deleteById(id);
    }
}