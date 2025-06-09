package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dao.ListCodeRelationDAO;
import com.bns.bnsref.dao.Ref_DataDAO;
import com.bns.bnsref.dao.Ref_DataValueDAO;
import com.bns.bnsref.dto.Ref_DataValueDTO;
import com.bns.bnsref.Entity.Ref_Data;
import com.bns.bnsref.Entity.Ref_DataValue;
import com.bns.bnsref.Mappers.Ref_DataValueMapper;
import com.bns.bnsref.Service.Ref_DataValueService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class Ref_DataValueServiceImpl implements Ref_DataValueService {

    private final Ref_DataValueDAO refDataValueDAO;
    private final ListCodeRelationDAO listCodeRelationDAO;
    private final Ref_DataValueMapper refDataValueMapper;
    private final Ref_DataDAO refDataDAO;


//    @Override
//    @Transactional
//    public Ref_DataValueDTO addRefDataValue(Ref_DataValueDTO refDataValueDTO) {
//        // Générer un nouveau code pour Ref_DataValue
//        String lastCode = refDataValueDAO.findLastRefDataValueCode().orElse("RDV000");
//        int nextId = Integer.parseInt(lastCode.replace("RDV", "")) + 1;
//        String newCode = String.format("RDV%03d", nextId);
//
//        // Vérifier et récupérer le Ref_Data associé
//        Ref_Data refData = refDataDAO.findById(refDataValueDTO.getCodeRefData())
//                .orElseThrow(() -> new RuntimeException("Ref_Data not found: " + refDataValueDTO.getCodeRefData()));
//
//        // Convertir le DTO en entité
//        Ref_DataValue refDataValue = refDataValueMapper.toEntity(refDataValueDTO);
//        refDataValue.setCodeRefDataValue(newCode);
//        refDataValue.setRefData(refData);
//
//        // Gestion de la jointure avec parentValue
//        if (refDataValueDTO.getParentValueCode() != null) {
//            Ref_DataValue parent = refDataValueDAO.findById(refDataValueDTO.getParentValueCode())
//                    .orElseThrow(() -> new RuntimeException("Parent Ref_DataValue not found: " + refDataValueDTO.getParentValueCode()));
//            refDataValue.setParentValue(parent);
//        }
//
//        // Sauvegarder l'entité
//        Ref_DataValue savedValue = refDataValueDAO.save(refDataValue);
//        return refDataValueMapper.toDTO(savedValue);
//    }

//    // nouveau
//    @Override
//    @Transactional
//    public Ref_DataValueDTO addRefDataValue(Ref_DataValueDTO refDataValueDTO) {
//        String lastCode = refDataValueDAO.findLastRefDataValueCode().orElse("RDV000");
//        int nextId = Integer.parseInt(lastCode.replace("RDV", "")) + 1;
//        String newCode = String.format("RDV%03d", nextId);
//
//        Ref_Data refData = refDataDAO.findById(refDataValueDTO.getCodeRefData())
//                .orElseThrow(() -> new RuntimeException("Ref_Data not found: " + refDataValueDTO.getCodeRefData()));
//
//        Ref_DataValue refDataValue = refDataValueMapper.toEntity(refDataValueDTO);
//        refDataValue.setCodeRefDataValue(newCode);
//        refDataValue.setRefData(refData);
//
//        // Vérification et assignation du parent
//        if (refDataValueDTO.getParentValueCode() != null) {
//            assignParent(refDataValue, refDataValueDTO.getParentValueCode());
//        }
//
//        // Vérification et assignation des enfants
//        if (refDataValueDTO.getChildValueCodes() != null && !refDataValueDTO.getChildValueCodes().isEmpty()) {
//            assignChildren(refDataValue, refDataValueDTO.getChildValueCodes());
//        }
//
//        Ref_DataValue savedValue = refDataValueDAO.save(refDataValue);
//        return refDataValueMapper.toDTO(savedValue);
//    }

    @Override
    @Transactional
    public Ref_DataValueDTO addRefDataValue(Ref_DataValueDTO refDataValueDTO) {
        String lastCode = refDataValueDAO.findLastRefDataValueCode().orElse("RDV000");
        int nextId = Integer.parseInt(lastCode.replace("RDV", "")) + 1;
        String newCode = String.format("RDV%03d", nextId);

        Ref_Data refData = refDataDAO.findById(refDataValueDTO.getCodeRefData())
                .orElseThrow(() -> new RuntimeException("Ref_Data not found: " + refDataValueDTO.getCodeRefData()));

        Ref_DataValue refDataValue = refDataValueMapper.toEntity(refDataValueDTO);
        refDataValue.setCodeRefDataValue(newCode);
        refDataValue.setRefData(refData);

        // Save the new parent entity first
        refDataValue = refDataValueDAO.save(refDataValue);

        if (refDataValueDTO.getParentValueCodes() != null && !refDataValueDTO.getParentValueCodes().isEmpty()) {
            assignParents(refDataValue, refDataValueDTO.getParentValueCodes());
        }

        if (refDataValueDTO.getChildValueCodes() != null && !refDataValueDTO.getChildValueCodes().isEmpty()) {
            assignChildren(refDataValue, refDataValueDTO.getChildValueCodes());
        }

        // Save again to persist relationships
        Ref_DataValue savedValue = refDataValueDAO.save(refDataValue);
        return refDataValueMapper.toDTO(savedValue);
    }

    @Override
    @Transactional
    public Ref_DataValueDTO assignRelation(Ref_DataValueDTO refDataValueDTO) {
        if (refDataValueDTO.getCodeRefDataValue() == null) {
            throw new IllegalArgumentException("codeRefDataValue must not be null");
        }

        Ref_DataValue refDataValue = refDataValueDAO.findById(refDataValueDTO.getCodeRefDataValue())
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found: " + refDataValueDTO.getCodeRefDataValue()));

        // Assign new parents, if provided
        if (refDataValueDTO.getParentValueCodes() != null && !refDataValueDTO.getParentValueCodes().isEmpty()) {
            assignParents(refDataValue, refDataValueDTO.getParentValueCodes());
        }

        // Assign new children, if provided (append, don’t clear)
        if (refDataValueDTO.getChildValueCodes() != null && !refDataValueDTO.getChildValueCodes().isEmpty()) {
            assignChildren(refDataValue, refDataValueDTO.getChildValueCodes());
        }

        Ref_DataValue updatedValue = refDataValueDAO.save(refDataValue);
        return refDataValueMapper.toDTO(updatedValue);
    }

    private void assignParents(Ref_DataValue refDataValue, List<String> parentValueCodes) {
        CodeList childCodeList = refDataValue.getRefData().getCodeList();
        for (String parentCode : parentValueCodes) {
            Ref_DataValue parent = refDataValueDAO.findById(parentCode)
                    .orElseThrow(() -> new RuntimeException("Parent Ref_DataValue not found: " + parentCode));

            // Validate relationship
            CodeList parentCodeList = parent.getRefData().getCodeList();
            boolean validParentRelation = listCodeRelationDAO.findByCodeListSourceCodeList(parentCodeList.getCodeList())
                    .stream()
                    .anyMatch(relation -> relation.getCodeListCible().getCodeList().equals(childCodeList.getCodeList()));
            if (!validParentRelation) {
                throw new IllegalStateException("No valid relation exists between " + parentCodeList.getCodeList() + " and " + childCodeList.getCodeList() + " to set parent");
            }

            // Append parent if not already present
            if (!refDataValue.getParents().contains(parent)) {
                refDataValue.getParents().add(parent);
                parent.getChildren().add(refDataValue);
                refDataValueDAO.save(parent);
            }
        }
    }

    private void assignChildren(Ref_DataValue refDataValue, List<String> childValueCodes) {
        CodeList parentCodeList = refDataValue.getRefData().getCodeList();

        // Validate all relationships first
        for (String childCode : childValueCodes) {
            Ref_DataValue child = refDataValueDAO.findById(childCode)
                    .orElseThrow(() -> new RuntimeException("Child Ref_DataValue not found: " + childCode));
            CodeList childCodeList = child.getRefData().getCodeList();
            boolean validChildRelation = listCodeRelationDAO.findByCodeListSourceCodeList(parentCodeList.getCodeList())
                    .stream()
                    .anyMatch(relation -> relation.getCodeListCible().getCodeList().equals(childCodeList.getCodeList()));
            if (!validChildRelation) {
                throw new IllegalStateException("No valid relation exists between " + parentCodeList.getCodeList() + " and " + childCodeList.getCodeList() + " to set child");
            }
        }

        // Assign children
        for (String childCode : childValueCodes) {
            Ref_DataValue child = refDataValueDAO.findById(childCode)
                    .orElseThrow(() -> new RuntimeException("Child Ref_DataValue not found: " + childCode));
            log.info("Assigning child {} to parent {}", childCode, refDataValue.getCodeRefDataValue());

            // Append child if not already present
            if (!refDataValue.getChildren().contains(child)) {
                refDataValue.getChildren().add(child);
                child.getParents().add(refDataValue);
                refDataValueDAO.save(child);
            }
        }
    }



    @Override
    public Ref_DataValueDTO updateRefDataValue(String codeRefDataValue, Ref_DataValueDTO refDataValueDTO) {
        Ref_DataValue refDataValue = refDataValueDAO.findById(codeRefDataValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found"));

        refDataValue.setValue(refDataValueDTO.getValue());

        // Use codeRefData from DTO to fetch Ref_Data
        var refData = refDataDAO.findById(refDataValueDTO.getCodeRefData())
                .orElseThrow(() -> new RuntimeException("Ref_Data not found"));
        refDataValue.setRefData(refData);

        return refDataValueMapper.toDTO(refDataValueDAO.save(refDataValue));
    }
    @Override
    @Transactional
    public void deleteRefDataValue(String codeRefDataValue) {
        Ref_DataValue refDataValue = refDataValueDAO.findById(codeRefDataValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found: " + codeRefDataValue));

        // Detach from parents
        for (Ref_DataValue parent : new HashSet<>(refDataValue.getParents())) {
            parent.getChildren().remove(refDataValue);
        }
        refDataValue.getParents().clear();

        // Detach from children
        for (Ref_DataValue child : new HashSet<>(refDataValue.getChildren())) {
            child.getParents().remove(refDataValue);
        }
        refDataValue.getChildren().clear();

        // Optional: detach translations if needed (already CascadeType.REMOVE, so not required)
        // refDataValue.getTranslations().clear();

        // Delete entity
        refDataValueDAO.delete(refDataValue);
    }


    @Override
    public Ref_DataValueDTO getRefDataValueById(String codeRefDataValue) {
        return refDataValueMapper.toDTO(refDataValueDAO.findById(codeRefDataValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found: " + codeRefDataValue)));
    }

    @Override
    public List<Ref_DataValueDTO> getAllRefDataValues() {
        return refDataValueDAO.findAll().stream()
                .map(refDataValueMapper::toDTO)
                .collect(Collectors.toList());
    }




}