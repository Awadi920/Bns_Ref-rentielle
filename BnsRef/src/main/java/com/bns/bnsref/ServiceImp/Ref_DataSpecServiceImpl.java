package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import com.bns.bnsref.Filter.Specification.Ref_DataSpecSpecification;
import com.bns.bnsref.dao.CodeListDAO;
import com.bns.bnsref.dao.FilterRepository;
import com.bns.bnsref.dao.Ref_DataSpecDAO;
import com.bns.bnsref.dao.SortCriteriaRepository;
import com.bns.bnsref.dto.Ref_DataSpecDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Ref_DataSpec;
import com.bns.bnsref.Mappers.Ref_DataSpecMapper;
import com.bns.bnsref.Service.Ref_DataSpecService;
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
public class Ref_DataSpecServiceImpl implements Ref_DataSpecService {

    private static final Logger logger = LoggerFactory.getLogger(Ref_DataSpecServiceImpl.class);


    private final Ref_DataSpecDAO refDataSpecDAO;
    private final Ref_DataSpecMapper refDataSpecMapper;
    private final CodeListDAO codeListDAO;

    private final FilterRepository filterRepository;
    private final SortCriteriaRepository sortCriteriaRepository;


    @Override
    public Ref_DataSpecDTO addRefDataSpec(Ref_DataSpecDTO refDataSpecDTO) {
        // Vérifier si le CodeList existe
        CodeList codeList = codeListDAO.findById(refDataSpecDTO.getCodeListCode())
                .orElseThrow(() -> new RuntimeException("CodeList not found with code: " + refDataSpecDTO.getCodeListCode()));

        // Générer automatiquement le codeRefDataSpec
        String lastCodeRefDataSpec = refDataSpecDAO.findLastRefDataSpecCode().orElse("REFDS000");
        int nextId = Integer.parseInt(lastCodeRefDataSpec.replace("REFDS", "")) + 1;
        String newCodeRefDataSpec = String.format("REFDS%03d", nextId); // Format REFDS001, REFDS002...

        // Convertir le DTO en entité
        Ref_DataSpec refDataSpec = refDataSpecMapper.toEntity(refDataSpecDTO);
        refDataSpec.setCodeRefDataSpec(newCodeRefDataSpec);
        refDataSpec.setCodeList(codeList); // Associer le CodeList

        // Sauvegarder l'entité
        Ref_DataSpec savedRefDataSpec = refDataSpecDAO.save(refDataSpec);

        // Retourner le DTO
        return refDataSpecMapper.toDTO(savedRefDataSpec);
    }

    @Override
    public Ref_DataSpecDTO updateRefDataSpec(String codeRefDataSpec, Ref_DataSpecDTO refDataSpecDTO) {
        Ref_DataSpec refDataSpec = refDataSpecDAO.findById(codeRefDataSpec)
                .orElseThrow(() -> new RuntimeException("Ref_DataSpec not found"));

        refDataSpec.setDesignation(refDataSpecDTO.getDesignation());
        refDataSpec.setDescription(refDataSpecDTO.getDescription());

        var codeList = codeListDAO.findById(refDataSpecDTO.getCodeListCode())
                .orElseThrow(() -> new RuntimeException("CodeList not found"));
        refDataSpec.setCodeList(codeList);

        return refDataSpecMapper.toDTO(refDataSpecDAO.save(refDataSpec));
    }

    @Override
    public void deleteRefDataSpec(String codeRefDataSpec) {
        refDataSpecDAO.deleteById(codeRefDataSpec);
    }

    @Override
    public Ref_DataSpecDTO getRefDataSpecById(String codeRefDataSpec) {
        return refDataSpecMapper.toDTO(refDataSpecDAO.findById(codeRefDataSpec)
                .orElseThrow(() -> new RuntimeException("Ref_DataSpec not found")));
    }

//    @Override
//    public List<Ref_DataSpecDTO> getAllRefDataSpec() {
//        return refDataSpecDAO.findAll().stream()
//                .map(refDataSpecMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ref_DataSpecDTO> getAllRefDataSpec(Pageable pageable) {
        Page<Ref_DataSpec> refDataSpecPage = refDataSpecDAO.findAll(pageable);
        return refDataSpecPage.map(refDataSpecMapper::toBasicDTO); // Use toBasicDTO
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ref_DataSpecDTO> getFilteredAndSortedRefDataSpec() {
        List<Filter> filters = filterRepository.findByEntityName("Ref_DataSpec");
        List<SortCriteria> sortCriteria = sortCriteriaRepository.findByEntityName("Ref_DataSpec");
        logger.info("Retrieved {} filters for Ref_DataSpec: {}", filters.size(), filters);
        logger.info("Retrieved {} sort criteria for Ref_DataSpec: {}", sortCriteria.size(), sortCriteria);

        List<Ref_DataSpec> refDataSpecs;
        if (filters.isEmpty() && sortCriteria.isEmpty()) {
            logger.info("No filters or sort criteria, returning all Ref_DataSpec");
            refDataSpecs = refDataSpecDAO.findAll();
        } else {
            logger.info("Applying filters and/or sort criteria");
            refDataSpecs = refDataSpecDAO.findAll(Ref_DataSpecSpecification.applyFiltersAndSort(filters, sortCriteria));
        }

        logger.info("Found {} Ref_DataSpec after applying criteria", refDataSpecs.size());
        return refDataSpecs.stream()
                .map(refDataSpecMapper::toDTO)
                .collect(Collectors.toList());
    }
}
