package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.Entity.*;
import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import com.bns.bnsref.Filter.Specification.CodeListSpecification;
import com.bns.bnsref.Mappers.Ref_DataMapper;
import com.bns.bnsref.dao.*;
import com.bns.bnsref.dto.CodeListDTO;
import com.bns.bnsref.Mappers.CodeListMapper;
import com.bns.bnsref.Service.CodeListService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Slf4j

public class CodeListServiceImpl implements CodeListService {

    private static final Logger logger = LoggerFactory.getLogger(CodeListServiceImpl.class);


    private final CodeListDAO codeListDAO;
    private final DomainDAO domainDAO;
    private final CategoryDAO categoryDAO;
    private final ProducerDAO producerDAO;
    private final Ref_DataDAO refDataDAO;
    private final Ref_DataSpecDAO refDataSpecDAO;


    private final CodeListMapper codeListMapper;
    private final Ref_DataMapper refDataMapper;



    private final FilterRepository filterRepository;
    private final SortCriteriaRepository sortCriteriaRepository;


    @Override
    public CodeListDTO addCodeList(CodeListDTO codeListDTO) {
        Domain domain = domainDAO.findById(codeListDTO.getDomainCode())
                .orElseThrow(() -> new RuntimeException("Domain non trouvé avec le code: " + codeListDTO.getDomainCode()));

        Category category = categoryDAO.findById(codeListDTO.getCodeCategory())
                .orElseThrow(() -> new RuntimeException("Category non trouvée avec le code: " + codeListDTO.getCodeCategory()));

        Producer producer = producerDAO.findById(codeListDTO.getProducerCode())
                .orElseThrow(() -> new RuntimeException("Producer non trouvé avec le code: " + codeListDTO.getProducerCode()));

        String lastCodeList = codeListDAO.findLastCodeList().orElse("CL000");
        int nextId = Integer.parseInt(lastCodeList.replace("CL", "")) + 1;
        String newCodeList = String.format("CL%03d", nextId);

        CodeList codeList = codeListMapper.toEntity(codeListDTO, domain, category, producer);
        codeList.setCodeList(newCodeList);
        codeList.setCreationDate(LocalDateTime.now());

        CodeList savedCodeList = codeListDAO.save(codeList);

        String lastCodeRefData = refDataDAO.findLastRefDataCode().orElse("REFD000");
        int nextRefDataId = Integer.parseInt(lastCodeRefData.replace("REFD", "")) + 1;

        String[] defaultColumns = {"code", "libellé", "description", "date début", "date fin"};
        Set<Ref_Data> defaultRefData = new HashSet<>();

        for (int i = 0; i < defaultColumns.length; i++) {
            String newCodeRefData = String.format("REFD%03d", nextRefDataId + i);
            Ref_Data refData = Ref_Data.builder()
                    .codeRefData(newCodeRefData)
                    .designation(defaultColumns[i])
                    .description("Colonne par défaut: " + defaultColumns[i])
                    .codeList(savedCodeList)
                    .orderPosition(i) // Définir l'ordre initial
                    .build();
            defaultRefData.add(refData);
        }

        refDataDAO.saveAll(defaultRefData);
        savedCodeList.setRefData(defaultRefData);
        codeListDAO.save(savedCodeList);

        return codeListMapper.toDTO(savedCodeList);
    }


    public CodeListDTO updateCodeList(String codeListId, CodeListDTO codeListDTO) {
        CodeList existingCodeList = codeListDAO.findById(codeListId)
                .orElseThrow(() -> new RuntimeException("CodeList not found"));

        // Mettre à jour les champs sans écraser la creationDate
        existingCodeList.setLabelList(codeListDTO.getLabelList());
        existingCodeList.setDescription(codeListDTO.getDescription());

        // Vérifier si les nouvelles valeurs existent avant de les mettre à jour
        if (codeListDTO.getDomainCode() != null) {
            Domain domain = domainDAO.findById(codeListDTO.getDomainCode())
                    .orElseThrow(() -> new RuntimeException("Domain not found"));
            existingCodeList.setDomain(domain);
        }

        if (codeListDTO.getCodeCategory() != null) {
            Category category = categoryDAO.findById(codeListDTO.getCodeCategory())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingCodeList.setCategory(category);
        }

        if (codeListDTO.getProducerCode() != null) {
            Producer producer = producerDAO.findById(codeListDTO.getProducerCode())
                    .orElseThrow(() -> new RuntimeException("Producer not found"));
            existingCodeList.setProducer(producer);
        }

        // Sauvegarder sans modifier creationDate
        CodeList updatedCodeList = codeListDAO.save(existingCodeList);

        return codeListMapper.toDTO(updatedCodeList);
    }

    @Transactional
    public void deleteCodeList(String codeListCode) {
        // Supprimer manuellement les Ref_DataSpec associés
        List<Ref_DataSpec> refDataSpecs = refDataSpecDAO.findByCodeListCodeList(codeListCode);
        log.info("Found {} Ref_DataSpec to delete for CodeList {}",
                refDataSpecs.size(), codeListCode);
        refDataSpecDAO.deleteAll(refDataSpecs);

        // Supprimer le CodeList
        CodeList codeList = codeListDAO.findById(codeListCode)
                .orElseThrow(() -> new EntityNotFoundException("CodeList not found: " + codeListCode));
        codeListDAO.delete(codeList);
        log.info("Deleted CodeList {}", codeListCode);
    }

    @Override
    public CodeListDTO getCodeListById(String codeListId) {
        CodeList codeList = codeListDAO.findById(codeListId)
                .orElseThrow(() -> new RuntimeException("CodeList non trouvé !"));
        return codeListMapper.toDTO(codeList);
    }

//    @Override
//    public List<CodeListDTO> getAllCodeLists() {
//        List<CodeList> codeLists = codeListDAO.findAllWithRelations();
//        return codeLists.stream()
//                .map(codeListMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<CodeListDTO> getAllCodeLists(Pageable pageable) {
        Page<CodeList> codeListPage = codeListDAO.findAll(pageable);
        return codeListPage.map(codeListMapper::toDTO);
    }



    @Override
    @Transactional(readOnly = true)
    public List<CodeListDTO> getFilteredAndSortedCodeLists() {
        List<Filter> filters = filterRepository.findByEntityName("CodeList");
        List<SortCriteria> sortCriteria = sortCriteriaRepository.findByEntityName("CodeList");
        logger.info("Retrieved {} filters for CodeList: {}", filters.size(), filters);
        logger.info("Retrieved {} sort criteria for CodeList: {}", sortCriteria.size(), sortCriteria);

        List<CodeList> codeLists;
        if (filters.isEmpty() && sortCriteria.isEmpty()) {
            logger.info("No filters or sort criteria, returning all CodeLists");
            codeLists = codeListDAO.findAllWithRelations();
        } else {
            logger.info("Applying filters and/or sort criteria");
            codeLists = codeListDAO.findAll(CodeListSpecification.applyFiltersAndSort(filters, sortCriteria));
        }

        logger.info("Found {} CodeLists after applying criteria", codeLists.size());
        return codeLists.stream()
                .map(codeListMapper::toDTO)
                .collect(Collectors.toList());
    }
}
