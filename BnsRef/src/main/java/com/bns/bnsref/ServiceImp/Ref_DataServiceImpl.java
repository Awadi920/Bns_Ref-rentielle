package com.bns.bnsref.ServiceImp;


import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import com.bns.bnsref.Filter.Specification.Ref_DataSpecification;
import com.bns.bnsref.Views.Views;
import com.bns.bnsref.dao.CodeListDAO;
import com.bns.bnsref.dao.FilterRepository;
import com.bns.bnsref.dao.Ref_DataDAO;
import com.bns.bnsref.dao.SortCriteriaRepository;
import com.bns.bnsref.dto.Ref_DataDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Ref_Data;
import com.bns.bnsref.Mappers.Ref_DataMapper;
import com.bns.bnsref.Service.Ref_DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Ref_DataServiceImpl implements Ref_DataService {

    private static final Logger logger = LoggerFactory.getLogger(Ref_DataServiceImpl.class);


    private final Ref_DataDAO refDataDAO;
    private final Ref_DataMapper refDataMapper;
    private final CodeListDAO codeListDAO;
    private final ObjectMapper objectMapper; // Add ObjectMapper

    private final FilterRepository filterRepository;
    private final SortCriteriaRepository sortCriteriaRepository;


//    @Override
//    public Ref_DataDTO addRefData(Ref_DataDTO refDataDTO) {
//        // Vérifier si le CodeList existe
//        CodeList codeList = codeListDAO.findById(refDataDTO.getCodeListCode())
//                .orElseThrow(() -> new RuntimeException("CodeList not found with code: " + refDataDTO.getCodeListCode()));
//
//        // Générer automatiquement le codeRefData
//        String lastCodeRefData = refDataDAO.findLastRefDataCode().orElse("REFD000");
//        int nextId = Integer.parseInt(lastCodeRefData.replace("REFD", "")) + 1;
//        String newCodeRefData = String.format("REFD%03d", nextId); // Format REFD001, REFD002...
//
//        // Convertir le DTO en entité
//        Ref_Data refData = refDataMapper.toEntity(refDataDTO);
//        refData.setCodeRefData(newCodeRefData);
//        refData.setCodeList(codeList); // Associer le CodeList
//
//        // Sauvegarder l'entité
//        Ref_Data savedRefData = refDataDAO.save(refData);
//
//        // Retourner le DTO
//        return refDataMapper.toDTO(savedRefData);
//    }

    @Override
    public Ref_DataDTO addRefData(Ref_DataDTO refDataDTO) {
        // Vérifier si le CodeList existe
        CodeList codeList = codeListDAO.findById(refDataDTO.getCodeListCode())
                .orElseThrow(() -> new RuntimeException("CodeList not found with code: " + refDataDTO.getCodeListCode()));

        // Générer automatiquement le codeRefData
        String lastCodeRefData = refDataDAO.findLastRefDataCode().orElse("REFD000");
        int nextId = Integer.parseInt(lastCodeRefData.replace("REFD", "")) + 1;
        String newCodeRefData = String.format("REFD%03d", nextId); // Format REFD001, REFD002...

        // Convertir le DTO en entité
        Ref_Data refData = refDataMapper.toEntity(refDataDTO);
        refData.setCodeRefData(newCodeRefData);
        refData.setCodeList(codeList); // Associer le CodeList

        // Sauvegarder l'entité
        Ref_Data savedRefData = refDataDAO.save(refData);

        // Retourner le DTO sans charger les collections lazy
        return refDataMapper.toBasicDTO(savedRefData);
    }


    @Override
    public Ref_DataDTO updateRefData(String codeRefData, Ref_DataDTO refDataDTO) {
        Ref_Data refData = refDataDAO.findById(codeRefData)
                .orElseThrow(() -> new RuntimeException("Ref_Data not found"));

        refData.setDesignation(refDataDTO.getDesignation());
        refData.setDescription(refDataDTO.getDescription());

        // Mise à jour du CodeList
        var codeList = codeListDAO.findById(refDataDTO.getCodeListCode())
                .orElseThrow(() -> new RuntimeException("CodeList not found"));
        refData.setCodeList(codeList);

        return refDataMapper.toDTO(refDataDAO.save(refData));
    }

    @Override
    public void deleteRefData(String codeRefData) {
        refDataDAO.deleteById(codeRefData);
    }

    @Override
    public Ref_DataDTO getRefDataById(String codeRefData) {
        return refDataMapper.toDTO(refDataDAO.findById(codeRefData)
                .orElseThrow(() -> new RuntimeException("Ref_Data not found")));
    }

//    @Override
//    public List<Ref_DataDTO> getAllRefData() {
//        return refDataDAO.findAll().stream()
//                .map(refDataMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ref_DataDTO> getAllRefData(Pageable pageable) {
        Page<Ref_Data> refDataPage = refDataDAO.findAll(pageable);
        Page<Ref_DataDTO> dtoPage = refDataPage.map(refData -> {
            Ref_DataDTO dto = refDataMapper.toBasicDTO(refData); // Use toBasicDTO
            return dto;
        });
        return dtoPage;
    }


    @Override
    public List<Ref_DataDTO> getFilteredAndSortedRefData() {
        List<Filter> filters = filterRepository.findByEntityName("Ref_Data");
        List<SortCriteria> sortCriteria = sortCriteriaRepository.findByEntityName("Ref_Data");
        logger.info("Retrieved {} filters for Ref_Data: {}", filters.size(), filters);
        logger.info("Retrieved {} sort criteria for Ref_Data: {}", sortCriteria.size(), sortCriteria);

        List<Ref_Data> refDataList;
        if (filters.isEmpty() && sortCriteria.isEmpty()) {
            logger.info("No filters or sort criteria, returning all Ref_Data");
            refDataList = refDataDAO.findAll();
        } else {
            logger.info("Applying filters and/or sort criteria");
            refDataList = refDataDAO.findAll(Ref_DataSpecification.applyFiltersAndSort(filters, sortCriteria));
        }

        logger.info("Found {} Ref_Data after applying criteria", refDataList.size());
        return refDataList.stream()
                .map(refDataMapper::toDTO)
                .collect(Collectors.toList());
    }
}
