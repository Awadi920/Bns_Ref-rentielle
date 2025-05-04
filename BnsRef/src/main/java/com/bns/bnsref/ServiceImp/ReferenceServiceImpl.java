package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.Entity.CodeListTranslation;
import com.bns.bnsref.Mappers.ListCodeRelationMapper;
import com.bns.bnsref.Mappers.Ref_DataMapper;
import com.bns.bnsref.Mappers.Ref_DataSpecMapper;
import com.bns.bnsref.dao.CodeListDAO;
import com.bns.bnsref.dao.ListCodeRelationDAO;
import com.bns.bnsref.dao.Ref_DataDAO;
import com.bns.bnsref.dao.Ref_DataSpecDAO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Mappers.ReferenceMapper;
import com.bns.bnsref.Service.ReferenceService;
import com.bns.bnsref.dto.CodeListTranslationDTO;
import com.bns.bnsref.dto.RefDataValueTranslationDTO;
import com.bns.bnsref.dto.ReferenceDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional; // <-- Correct
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferenceServiceImpl implements ReferenceService {

    private final CodeListDAO codeListDAO;
    private final ListCodeRelationDAO listCodeRelationDAO;
    private final ReferenceMapper referenceMapper;
    private final Ref_DataMapper refDataMapper;
    private final Ref_DataSpecMapper refDataSpecMapper;
    private final ListCodeRelationMapper listCodeRelationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CodeList> getAllReferences() {
      //  return codeListDAO.findAll();
        return codeListDAO.findAllWithRelations(); // Use custom query with eager fetching

    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferenceDTO> getAllReferencesAsDTO() {
        return getAllReferences().stream()
                .map(referenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferenceDTO> getAllReferencesWithLang(String lang) {
        return getAllReferences().stream()
                .map(cl -> getReferenceWithLang(cl.getCodeList(), lang))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferenceDTO> getAllReferencesWithAllTranslations() {
        return getAllReferences().stream()
                .map(cl -> getReferenceDetailsWithAllTranslations(cl.getCodeList()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReferenceDTO getReferenceDetailsWithAllTranslations(String codeListCode) {
        CodeList codeList = codeListDAO.findById(codeListCode)
                .orElseThrow(() -> new RuntimeException("CodeList not found: " + codeListCode));

        ReferenceDTO dto = new ReferenceDTO();
        dto.setCodeList(codeList.getCodeList());
        dto.setLabelList(codeList.getLabelList());
        dto.setDescription(codeList.getDescription());

        dto.setTranslations(codeList.getTranslations().stream()
                .map(t -> new CodeListTranslationDTO(
                        t.getCodeListTranslation(),
                        t.getCodeList().getCodeList(),
                        t.getLanguage().getCodeLanguage(),
                        t.getLabelList(),
                        t.getDescription()))
                .collect(Collectors.toList()));

        setRelations(dto, codeList, null, true);
        return dto;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public ReferenceDTO getReferenceWithLang(String codeListCode, String lang) {
//        CodeList codeList = codeListDAO.findById(codeListCode)
//                .orElseThrow(() -> new RuntimeException("CodeList not found: " + codeListCode));
//
//        System.out.println("CodeList loaded: " + codeList.getCodeList());
//        System.out.println("Source Relations before setRelations: " + codeList.getSourceRelations());
//
//        ReferenceDTO dto = new ReferenceDTO();
//        dto.setCodeList(codeList.getCodeList());
//
//        if (lang != null) {
//            CodeListTranslation translation = codeList.getTranslations().stream()
//                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
//                    .findFirst()
//                    .orElse(null);
//            if (translation != null) {
//                dto.setLabelList(translation.getLabelList());
//                dto.setDescription(translation.getDescription());
//            } else {
//                dto.setLabelList(null);
//                dto.setDescription(null);
//            }
//        } else {
//            dto.setLabelList(codeList.getLabelList());
//            dto.setDescription(codeList.getDescription());
//        }
//
//        setRelations(dto, codeList, lang, false);
//        return dto;
//    }

    @Override
    @Transactional(readOnly = true)
    public ReferenceDTO getReferenceWithLang(String codeListCode, String lang) {
        CodeList codeList = codeListDAO.findById(codeListCode)
                .orElseThrow(() -> new RuntimeException("CodeList not found: " + codeListCode));

        System.out.println("CodeList loaded: " + codeList.getCodeList());
        System.out.println("Source Relations size before setRelations: " + codeList.getSourceRelations().size()); // Affiche juste la taille

        ReferenceDTO dto = new ReferenceDTO();
        dto.setCodeList(codeList.getCodeList());

        if (lang != null) {
            CodeListTranslation translation = codeList.getTranslations().stream()
                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
                    .findFirst()
                    .orElse(null);
            if (translation != null) {
                dto.setLabelList(translation.getLabelList());
                dto.setDescription(translation.getDescription());
            } else {
                dto.setLabelList(null);
                dto.setDescription(null);
            }
        } else {
            dto.setLabelList(codeList.getLabelList());
            dto.setDescription(codeList.getDescription());
        }

        setRelations(dto, codeList, lang, false);
        return dto;
    }

    private void setRelations(ReferenceDTO dto, CodeList codeList, String lang, boolean withAllTranslations) {
        dto.setDomainCode(codeList.getDomain() != null ? codeList.getDomain().getCodeDomain() : null);
        dto.setCategoryCode(codeList.getCategory() != null ? codeList.getCategory().getCodeCategory() : null);
        dto.setProducerCode(codeList.getProducer() != null ? codeList.getProducer().getCodeProd() : null);

        if (withAllTranslations) {
            dto.setRefData(codeList.getRefData().stream()
                    .map(refDataMapper::toDTOWithAllTranslations)
                    .collect(Collectors.toList()));
            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
                    .map(refDataSpecMapper::toDTOWithAllTranslations)
                    .collect(Collectors.toList()));
        } else if (lang != null) {
            dto.setRefData(codeList.getRefData().stream()
                    .map(r -> refDataMapper.toDTOWithLang(r, lang))
                    .map(refDataDTO -> {
                        // Ne filtre pas refDataDTO, mais ajuste ses values
                        refDataDTO.setValues(refDataDTO.getValues().stream()
                                .map(v -> {
                                    // Appliquer la traduction si disponible
                                    RefDataValueTranslationDTO translation = v.getTranslations().stream()
                                            .filter(t -> t.getLanguageCode().equalsIgnoreCase(lang))
                                            .findFirst()
                                            .orElse(null);
                                    if (translation != null) {
                                        v.setValue(translation.getValue());
                                    }
                                    return v;
                                })
                                .collect(Collectors.toList()));
                        return refDataDTO;
                    })
                    .collect(Collectors.toList()));
            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
                    .map(r -> refDataSpecMapper.toDTOWithLang(r, lang))
                    .collect(Collectors.toList()));
        } else {
            dto.setRefData(codeList.getRefData().stream()
                    .map(refDataMapper::toDTO)
                    .collect(Collectors.toList()));
            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
                    .map(refDataSpecMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        dto.setSourceRelations(listCodeRelationDAO.findByCodeListSourceCodeList(codeList.getCodeList()).stream()
                .map(listCodeRelationMapper::toDTO)
                .collect(Collectors.toList()));
        dto.setTargetRelations(listCodeRelationDAO.findByCodeListCibleCodeList(codeList.getCodeList()).stream()
                .map(listCodeRelationMapper::toDTO)
                .collect(Collectors.toList()));
    }

//    private void setRelations(ReferenceDTO dto, CodeList codeList, String lang, boolean withAllTranslations) {
//        dto.setDomainCode(codeList.getDomain() != null ? codeList.getDomain().getCodeDomain() : null);
//        dto.setCategoryCode(codeList.getCategory() != null ? codeList.getCategory().getCodeCategory() : null);
//        dto.setProducerCode(codeList.getProducer() != null ? codeList.getProducer().getCodeProd() : null);
//
//        if (withAllTranslations) {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(refDataMapper::toDTOWithAllTranslations)
//                    .collect(Collectors.toList()));
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(refDataSpecMapper::toDTOWithAllTranslations)
//                    .collect(Collectors.toList()));
//        } else if (lang != null) {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(r -> refDataMapper.toDTOWithLang(r, lang))
//                    .filter(refDataDTO -> !refDataDTO.getTranslations().isEmpty())
//                    .map(refDataDTO -> {
//                        refDataDTO.setValues(refDataDTO.getValues().stream()
//                                .filter(v -> !v.getTranslations().isEmpty())
//                                .collect(Collectors.toList()));
//                        return refDataDTO;
//                    })
//                    .collect(Collectors.toList()));
//
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(r -> refDataSpecMapper.toDTOWithLang(r, lang))
//                    .filter(refDataSpecDTO -> !refDataSpecDTO.getTranslations().isEmpty())
//                    .map(refDataSpecDTO -> {
//                        refDataSpecDTO.setSpecValues(refDataSpecDTO.getSpecValues().stream()
//                                .filter(v -> !v.getTranslations().isEmpty())
//                                .collect(Collectors.toList()));
//                        return refDataSpecDTO;
//                    })
//                    .collect(Collectors.toList()));
//        } else {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(refDataMapper::toDTO)
//                    .collect(Collectors.toList()));
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(refDataSpecMapper::toDTO)
//                    .collect(Collectors.toList()));
//        }
//
//        // Charger les relations directement depuis ListCodeRelationDAO
//        dto.setSourceRelations(listCodeRelationDAO.findByCodeListSourceCodeList(codeList.getCodeList()).stream()
//                .map(listCodeRelationMapper::toDTO)
//                .collect(Collectors.toList()));
//        dto.setTargetRelations(listCodeRelationDAO.findByCodeListCibleCodeList(codeList.getCodeList()).stream()
//                .map(listCodeRelationMapper::toDTO)
//                .collect(Collectors.toList()));
//    }




//
//    private final CodeListDAO codeListDAO;
//    private final ListCodeRelationDAO listCodeRelationDAO; // Ajouter cette dépendance
//    private final ReferenceMapper referenceMapper;
//    private final Ref_DataMapper refDataMapper;
//    private final Ref_DataSpecMapper refDataSpecMapper;
//    private final ListCodeRelationMapper listCodeRelationMapper;
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<CodeList> getAllReferences() {
//        return codeListDAO.findAll();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ReferenceDTO> getAllReferencesAsDTO() {
//        List<CodeList> codeLists = getAllReferences();
//        return codeLists.stream()
//                .map(referenceMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ReferenceDTO> getAllReferencesWithLang(String lang) {
//        List<CodeList> codeLists = getAllReferences();
//        return codeLists.stream()
//                .map(cl -> getReferenceWithLang(cl.getCodeList(), lang))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ReferenceDTO> getAllReferencesWithAllTranslations() {
//        List<CodeList> codeLists = getAllReferences();
//        return codeLists.stream()
//                .map(cl -> getReferenceDetailsWithAllTranslations(cl.getCodeList()))
//                .collect(Collectors.toList());
//    }
//
//
//    @Override
//    @Transactional(readOnly = true)
//    public ReferenceDTO getReferenceDetailsWithAllTranslations(String codeListCode) {
//        CodeList codeList = codeListDAO.findById(codeListCode)
//                .orElseThrow(() -> new RuntimeException("CodeList not found: " + codeListCode));
//        if (codeList == null) return null;
//
//        ReferenceDTO dto = new ReferenceDTO();
//        dto.setCodeList(codeList.getCodeList());
//        dto.setLabelList(codeList.getLabelList());
//        dto.setDescription(codeList.getDescription());
//
//        // Ajout de toutes les traductions disponibles
//        dto.setTranslations(codeList.getTranslations().stream()
//                .map(t -> new CodeListTranslationDTO(
//                        t.getCodeListTranslation(),
//                        t.getCodeList().getCodeList(),
//                        t.getLanguage().getCodeLanguage(),
//                        t.getLabelList(),
//                        t.getDescription()))
//                .collect(Collectors.toList()));
//
//        // Ajout de toutes les relations et données associées
//        setRelations(dto, codeList, null, true);
//        return dto;
//    }
//
//
//    @Override
//    @Transactional(readOnly = true)
//    public ReferenceDTO getReferenceWithLang(String codeListCode, String lang) {
//        CodeList codeList = codeListDAO.findById(codeListCode)
//                .orElseThrow(() -> new RuntimeException("CodeList not found: " + codeListCode));
//        if (codeList == null) return null;
//
//
//        System.out.println("CodeList loaded: " + codeList.getCodeList());
//        System.out.println("Source Relations before setRelations: " + codeList.getSourceRelations());
//
//        ReferenceDTO dto = new ReferenceDTO();
//        dto.setCodeList(codeList.getCodeList());
//
//        if (lang != null) {
//            CodeListTranslation translation = codeList.getTranslations().stream()
//                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
//                    .findFirst()
//                    .orElse(null);
//            if (translation != null) {
//                dto.setLabelList(translation.getLabelList());
//                dto.setDescription(translation.getDescription());
//            } else {
//                dto.setLabelList(null);
//                dto.setDescription(null);
//            }
//        } else {
//            dto.setLabelList(codeList.getLabelList());
//            dto.setDescription(codeList.getDescription());
//        }
//
//        setRelations(dto, codeList, lang, false);
//        return dto;
//    }
//
//    private void setRelations(ReferenceDTO dto, CodeList codeList, String lang, boolean withAllTranslations) {
//        dto.setDomainCode(codeList.getDomain() != null ? codeList.getDomain().getCodeDomain() : null);
//        dto.setCategoryCode(codeList.getCategory() != null ? codeList.getCategory().getCodeCategory() : null);
//        dto.setProducerCode(codeList.getProducer() != null ? codeList.getProducer().getCodeProd() : null);
//
//        if (withAllTranslations) {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(refDataMapper::toDTOWithAllTranslations)
//                    .collect(Collectors.toList()));
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(refDataSpecMapper::toDTOWithAllTranslations)
//                    .collect(Collectors.toList()));
//        } else if (lang != null) {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(r -> refDataMapper.toDTOWithLang(r, lang))
//                    .filter(refDataDTO -> !refDataDTO.getTranslations().isEmpty())
//                    .map(refDataDTO -> {
//                        refDataDTO.setValues(refDataDTO.getValues().stream()
//                                .filter(v -> !v.getTranslations().isEmpty())
//                                .collect(Collectors.toList()));
//                        return refDataDTO;
//                    })
//                    .collect(Collectors.toList()));
//
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(r -> refDataSpecMapper.toDTOWithLang(r, lang))
//                    .filter(refDataSpecDTO -> !refDataSpecDTO.getTranslations().isEmpty())
//                    .map(refDataSpecDTO -> {
//                        refDataSpecDTO.setSpecValues(refDataSpecDTO.getSpecValues().stream()
//                                .filter(v -> !v.getTranslations().isEmpty())
//                                .collect(Collectors.toList()));
//                        return refDataSpecDTO;
//                    })
//                    .collect(Collectors.toList()));
//        } else {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(refDataMapper::toDTO)
//                    .collect(Collectors.toList()));
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(refDataSpecMapper::toDTO)
//                    .collect(Collectors.toList()));
//        }
//
//        // Charger les relations directement depuis ListCodeRelationDAO
//        dto.setSourceRelations(listCodeRelationDAO.findByCodeListSourceCodeList(codeList.getCodeList()).stream()
//                .map(listCodeRelationMapper::toDTO)
//                .collect(Collectors.toList()));
//        dto.setTargetRelations(listCodeRelationDAO.findByCodeListCibleCodeList(codeList.getCodeList()).stream()
//                .map(listCodeRelationMapper::toDTO)
//                .collect(Collectors.toList()));
//    }
}