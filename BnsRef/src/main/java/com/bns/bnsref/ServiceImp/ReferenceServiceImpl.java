package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.Entity.CodeListTranslation;
import com.bns.bnsref.Entity.Ref_Data;
import com.bns.bnsref.Entity.Ref_DataSpec;
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
import com.bns.bnsref.dto.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional; // <-- Correct
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReferenceServiceImpl implements ReferenceService {

    private final CodeListDAO codeListDAO;
    private final ListCodeRelationDAO listCodeRelationDAO;
    private final Ref_DataDAO refDataDAO;
    private final Ref_DataSpecDAO refDataSpecDAO;

    private final ReferenceMapper referenceMapper;
    private final Ref_DataMapper refDataMapper;
    private final Ref_DataSpecMapper refDataSpecMapper;
    private final ListCodeRelationMapper listCodeRelationMapper;


    private static final Logger logger = LoggerFactory.getLogger(ReferenceServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public List<ReferenceDTO> getAllReferencesAsDTO() {
        List<CodeList> codeLists = codeListDAO.findAll();
        return codeLists.stream()
                .map(cl -> {
                    ReferenceDTO dto = referenceMapper.toDTO(cl);
                    // Charger manuellement les Ref_DataSpec
                    List<Ref_DataSpec> refDataSpecs = refDataSpecDAO.findByCodeListCodeList(cl.getCodeList());
                    log.info("Manually loaded {} Ref_DataSpec for CodeList {}: {}",
                            refDataSpecs.size(),
                            cl.getCodeList(),
                            refDataSpecs.stream().map(Ref_DataSpec::getCodeRefDataSpec).collect(Collectors.toList()));
                    dto.setRefDataSpec(refDataSpecs.stream()
                            .map(referenceMapper::toRefDataSpecDTO) // Assurez-vous que ReferenceMapper a cette méthode
                            .sorted(Comparator.comparing(Ref_DataSpecDTO::getOrderPosition, Comparator.nullsLast(Integer::compareTo)))
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodeList> getAllReferences() {
        List<CodeList> codeLists = codeListDAO.findAll();
        codeLists.forEach(cl -> {
            Hibernate.initialize(cl.getRefDataSpec());
            log.info("CodeList {} has {} Ref_DataSpec: {} and {} Ref_Data",
                    cl.getCodeList(),
                    cl.getRefDataSpec().size(),
                    cl.getRefDataSpec().stream().map(Ref_DataSpec::getCodeRefDataSpec).collect(Collectors.toList()),
                    cl.getRefData().size());
        });
        return codeLists;
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
        logger.info("CodeList {} has translated: {}", codeList.getCodeList(), codeList.isTranslated());

        // Utiliser le ReferenceMapper pour créer le DTO
        ReferenceDTO dto = referenceMapper.toDTO(codeList);

        // Ajouter les traductions
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

    @Override
    @Transactional
    public void updateColumnOrder(String codeListCode, List<ColumnOrderDTO> columnOrders) {
        // Récupérer le CodeList avec ses relations chargées
        CodeList codeList = codeListDAO.findById(codeListCode)
                .orElseThrow(() -> new RuntimeException("CodeList not found: " + codeListCode));

        // Charger explicitement les relations pour éviter les problèmes de FetchType.LAZY
        Hibernate.initialize(codeList.getRefData());
        Hibernate.initialize(codeList.getRefDataSpec());

        // Récupérer toutes les colonnes existantes
        Set<Ref_Data> existingRefData = codeList.getRefData();
        Set<Ref_DataSpec> existingRefDataSpec = codeList.getRefDataSpec();

        // Créer une map des codes pour vérifier les colonnes fournies
        Map<String, ColumnOrderDTO> orderMap = columnOrders.stream()
                .collect(Collectors.toMap(ColumnOrderDTO::getCode, dto -> dto));

        // Valider les orderPosition (doivent être uniques et continus)
        Set<Integer> positions = columnOrders.stream()
                .map(ColumnOrderDTO::getOrderPosition)
                .collect(Collectors.toSet());
        if (positions.size() != columnOrders.size()) {
            throw new IllegalArgumentException("Duplicate orderPosition values detected");
        }
        if (!positions.containsAll(IntStream.range(0, columnOrders.size()).boxed().collect(Collectors.toSet()))) {
            throw new IllegalArgumentException("orderPosition must be a continuous sequence starting from 0");
        }

        // Mettre à jour Ref_Data
        List<Ref_Data> updatedRefData = new ArrayList<>();
        for (Ref_Data refData : existingRefData) {
            ColumnOrderDTO orderDTO = orderMap.get(refData.getCodeRefData());
            if (orderDTO != null && "REF_DATA".equalsIgnoreCase(orderDTO.getType())) {
                if (!refData.getCodeList().getCodeList().equals(codeListCode)) {
                    throw new RuntimeException("Ref_Data " + refData.getCodeRefData() + " does not belong to CodeList " + codeListCode);
                }
                refData.setOrderPosition(orderDTO.getOrderPosition());
                updatedRefData.add(refData);
                orderMap.remove(refData.getCodeRefData());
            } else {
                // Supprimer la colonne si elle n'est pas dans columnOrders
                refDataDAO.delete(refData);
            }
        }
        refDataDAO.saveAll(updatedRefData);

        // Mettre à jour Ref_DataSpec
        List<Ref_DataSpec> updatedRefDataSpec = new ArrayList<>();
        for (Ref_DataSpec refDataSpec : existingRefDataSpec) {
            ColumnOrderDTO orderDTO = orderMap.get(refDataSpec.getCodeRefDataSpec());
            if (orderDTO != null && "REF_DATA_SPEC".equalsIgnoreCase(orderDTO.getType())) {
                if (!refDataSpec.getCodeList().getCodeList().equals(codeListCode)) {
                    throw new RuntimeException("Ref_DataSpec " + refDataSpec.getCodeRefDataSpec() + " does not belong to CodeList " + codeListCode);
                }
                refDataSpec.setOrderPosition(orderDTO.getOrderPosition());
                updatedRefDataSpec.add(refDataSpec);
                orderMap.remove(refDataSpec.getCodeRefDataSpec());
            } else {
                // Supprimer la colonne si elle n'est pas dans columnOrders
                refDataSpecDAO.delete(refDataSpec);
            }
        }
        refDataSpecDAO.saveAll(updatedRefDataSpec);

        // Log les colonnes non reconnues au lieu de lever une exception
        if (!orderMap.isEmpty()) {
            logger.warn("Columns provided but not found in CodeList {}: {}", codeListCode, orderMap.keySet());
            // Optionnel : créer de nouvelles colonnes si nécessaire (voir ci-dessous)
        }
    }

}