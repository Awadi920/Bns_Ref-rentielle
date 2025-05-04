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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



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

        if (refDataValueDTO.getParentValueCode() != null) {
            assignParent(refDataValue, refDataValueDTO.getParentValueCode());
        }

        if (refDataValueDTO.getChildValueCodes() != null && !refDataValueDTO.getChildValueCodes().isEmpty()) {
            assignChildren(refDataValue, refDataValueDTO.getChildValueCodes());
        }

        Ref_DataValue savedValue = refDataValueDAO.save(refDataValue);
        return refDataValueMapper.toDTO(savedValue);
    }

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
//        // Sauvegarder refDataValue avant d'assigner des enfants
//        refDataValue = refDataValueDAO.save(refDataValue);
//
//        // Vérification et assignation des enfants
//        if (refDataValueDTO.getChildValueCodes() != null && !refDataValueDTO.getChildValueCodes().isEmpty()) {
//            assignChildren(refDataValue, refDataValueDTO.getChildValueCodes());
//        }
//
//        // Sauvegarder à nouveau après avoir assigné les enfants
//        refDataValue = refDataValueDAO.save(refDataValue);
//        return refDataValueMapper.toDTO(refDataValue);
//    }


    @Override
    public Ref_DataValueDTO updateRefDataValue(String codeRefDataValue, Ref_DataValueDTO refDataValueDTO) {
        Ref_DataValue refDataValue = refDataValueDAO.findById(codeRefDataValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found"));

        refDataValue.setValue(refDataValueDTO.getValue());

        var refData = refDataDAO.findById(refDataValueDTO.getCodeRefDataValue())
                .orElseThrow(() -> new RuntimeException("Ref_Data not found"));
        refDataValue.setRefData(refData);

//        var language = languageDAO.findById(refDataValueDTO.getCodeLanguage())
//                .orElseThrow(() -> new RuntimeException("Language not found"));
//        refDataValue.setLanguage(language);

        return refDataValueMapper.toDTO(refDataValueDAO.save(refDataValue));
    }

//    @Override
//    @Transactional
//    public void deleteRefDataValue(String codeRefDataValue) {
//        if (!refDataValueDAO.existsById(codeRefDataValue)) {
//            throw new RuntimeException("Ref_DataValue not found: " + codeRefDataValue);
//        }
//        refDataValueDAO.deleteById(codeRefDataValue); // Cascade supprime les enfants
//    }

    @Transactional
    public void deleteRefDataValue(String codeRefDataValue) {
        Ref_DataValue value = refDataValueDAO.findById(codeRefDataValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found: " + codeRefDataValue));

        // Dissocier les enfants
        value.getChildValues().forEach(child -> child.setParentValue(null));
        refDataValueDAO.saveAll(value.getChildValues());

        // Supprimer la valeur
        refDataValueDAO.delete(value);
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
    @Override
    @Transactional
    public Ref_DataValueDTO assignRelation(Ref_DataValueDTO refDataValueDTO) {
        if (refDataValueDTO.getCodeRefDataValue() == null) {
            throw new IllegalArgumentException("codeRefDataValue must not be null");
        }

        Ref_DataValue refDataValue = refDataValueDAO.findById(refDataValueDTO.getCodeRefDataValue())
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found: " + refDataValueDTO.getCodeRefDataValue()));

        if (refDataValueDTO.getParentValueCode() != null) {
            assignParent(refDataValue, refDataValueDTO.getParentValueCode());
        } else if (refDataValueDTO.getParentValueCode() == null && refDataValue.getParentValue() != null) {
            refDataValue.setParentValue(null);
        }

        if (refDataValueDTO.getChildValueCodes() != null) {
            refDataValue.getChildValues().forEach(child -> child.setParentValue(null));
            refDataValue.getChildValues().clear();
            if (!refDataValueDTO.getChildValueCodes().isEmpty()) {
                assignChildren(refDataValue, refDataValueDTO.getChildValueCodes());
            }
        }

        Ref_DataValue updatedValue = refDataValueDAO.save(refDataValue);
        return refDataValueMapper.toDTO(updatedValue);
    }

    // Nouvelle méthodes
    private void assignParent(Ref_DataValue refDataValue, String parentValueCode) {
        Ref_DataValue parent = refDataValueDAO.findById(parentValueCode)
                .orElseThrow(() -> new RuntimeException("Parent Ref_DataValue not found: " + parentValueCode));
        CodeList childCodeList = refDataValue.getRefData().getCodeList();
        CodeList parentCodeList = parent.getRefData().getCodeList();

        boolean validRelation = listCodeRelationDAO.findByCodeListSourceCodeList(parentCodeList.getCodeList())
                .stream()
                .anyMatch(relation -> relation.getCodeListCible().getCodeList().equals(childCodeList.getCodeList()));
        if (!validRelation) {
            throw new IllegalStateException("No valid relation exists between " + childCodeList.getCodeList() + " and " + parentCodeList.getCodeList() + " to set parent");
        }

        refDataValue.setParentValue(parent);
    }

    private void assignChildren(Ref_DataValue refDataValue, List<String> childValueCodes) {
        CodeList parentCodeList = refDataValue.getRefData().getCodeList();
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

            child.setParentValue(refDataValue);
            refDataValue.getChildValues().add(child);
        }
    }

//    private void assignParent(Ref_DataValue refDataValue, String parentValueCode) {
//        Ref_DataValue parent = refDataValueDAO.findById(parentValueCode)
//                .orElseThrow(() -> new RuntimeException("Parent Ref_DataValue not found: " + parentValueCode));
//        CodeList childCodeList = refDataValue.getRefData().getCodeList();
//        CodeList parentCodeList = parent.getRefData().getCodeList();
//
//        boolean validRelation = listCodeRelationDAO.findByCodeListSourceCodeList(parentCodeList.getCodeList())
//                .stream()
//                .anyMatch(relation -> relation.getCodeListCible().getCodeList().equals(childCodeList.getCodeList()));
//        if (!validRelation) {
//            throw new IllegalStateException("No valid relation exists between " + childCodeList.getCodeList() + " and " + parentCodeList.getCodeList() + " to set parent");
//        }
//
//        refDataValue.setParentValue(parent);
//    }
//
//    private void assignChildren(Ref_DataValue refDataValue, List<String> childValueCodes) {
//        CodeList parentCodeList = refDataValue.getRefData().getCodeList();
//        for (String childCode : childValueCodes) {
//            Ref_DataValue child = refDataValueDAO.findById(childCode)
//                    .orElseThrow(() -> new RuntimeException("Child Ref_DataValue not found: " + childCode));
//            CodeList childCodeList = child.getRefData().getCodeList();
//
//            boolean validChildRelation = listCodeRelationDAO.findByCodeListSourceCodeList(parentCodeList.getCodeList())
//                    .stream()
//                    .anyMatch(relation -> relation.getCodeListCible().getCodeList().equals(childCodeList.getCodeList()));
//            if (!validChildRelation) {
//                throw new IllegalStateException("No valid relation exists between " + parentCodeList.getCodeList() + " and " + childCodeList.getCodeList() + " to set child");
//            }
//
//            // Mettre à jour la relation bidirectionnelle
//            child.setParentValue(refDataValue);
//            refDataValue.getChildValues().add(child);
//
//            // Sauvegarder l'enfant pour persister les changements
//            refDataValueDAO.save(child);
//        }
//    }


}
