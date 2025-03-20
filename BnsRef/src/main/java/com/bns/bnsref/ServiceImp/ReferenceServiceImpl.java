package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.DAO.CodeListDAO;
import com.bns.bnsref.DTO.ReferenceDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Mappers.ReferenceMapper;
import com.bns.bnsref.Service.ReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional; // <-- Correct
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferenceServiceImpl implements ReferenceService {

//    private final CodeListDAO codeListDAO;
//    private final ReferenceMapper referenceMapper;
//
//    public ReferenceServiceImpl(CodeListDAO codeListDAO, ReferenceMapper referenceMapper) {
//        this.codeListDAO = codeListDAO;
//        this.referenceMapper = referenceMapper;
//    }
//
//    @Override
//    @Transactional(readOnly = true) // <-- Garde la session ouverte
//    public List<ReferenceDTO> getAllReferences() {
//        return codeListDAO.findAllWithDetails().stream()
//                .map(referenceMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true) // <-- Ajoutez cette méthode
//    public Optional<ReferenceDTO> getReferenceByIdOrLabel(String idOrLabel) {
//        return codeListDAO.findByCodeListOrLabelListWithDetails(idOrLabel)
//                .map(referenceMapper::toDTO);
//    }

    private final CodeListDAO codeListDAO;
    private final ReferenceMapper referenceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReferenceDTO> getAllReferences() {
        List<CodeList> codeLists = codeListDAO.findAllWithDetails();
        return codeLists.stream()
                .map(referenceMapper::toDTO)
                .peek(dto -> {
                    // Déduplication des specValues dans chaque Ref_DataSpecDTO
                    dto.getRefDataSpec().forEach(refDataSpecDTO ->
                            refDataSpecDTO.setSpecValues(
                                    refDataSpecDTO.getSpecValues().stream().distinct().collect(Collectors.toList())
                            ));
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReferenceDTO> getReferenceByIdOrLabel(String idOrLabel) {
        return codeListDAO.findByCodeListOrLabelListWithDetails(idOrLabel)
                .map(referenceMapper::toDTO)
                .map(dto -> {
                    // Déduplication des specValues
                    dto.getRefDataSpec().forEach(refDataSpecDTO ->
                            refDataSpecDTO.setSpecValues(
                                    refDataSpecDTO.getSpecValues().stream().distinct().collect(Collectors.toList())
                            ));
                    return dto;
                });
    }
}