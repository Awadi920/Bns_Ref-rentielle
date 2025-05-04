package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.dao.CodeListDAO;
import com.bns.bnsref.dao.ListCodeRelationDAO;
import com.bns.bnsref.dao.TypeRelationDAO;
import com.bns.bnsref.dto.ListCodeRelationDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.ListCodeRelation;
import com.bns.bnsref.Mappers.ListCodeRelationMapper;
import com.bns.bnsref.Service.ListCodeRelationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bns.bnsref.Entity.TypeRelation;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListCodeRelationServiceImpl implements ListCodeRelationService {

    private final ListCodeRelationDAO listCodeRelationDAO;
    private final CodeListDAO codeListDAO;
    private final TypeRelationDAO typeRelationDAO;

    private final ListCodeRelationMapper listCodeRelationMapper;

//    @Override
//    @Transactional
//    public ListCodeRelationDTO createListCodeRelation(ListCodeRelationDTO dto) {
//        if (dto == null || dto.getCodeListSourceCode() == null || dto.getCodeListCibleCode() == null) {
//            throw new IllegalArgumentException("CodeListSourceCode and CodeListCibleCode must not be null");
//        }
//
//        CodeList codeListSource = codeListDAO.findById(dto.getCodeListSourceCode())
//                .orElseThrow(() -> new RuntimeException("CodeList source not found: " + dto.getCodeListSourceCode()));
//        CodeList codeListCible = codeListDAO.findById(dto.getCodeListCibleCode())
//                .orElseThrow(() -> new RuntimeException("CodeList cible not found: " + dto.getCodeListCibleCode()));
//
//        ListCodeRelation relation = listCodeRelationMapper.toEntity(dto);
//        relation.setCodeListSource(codeListSource);
//        relation.setCodeListCible(codeListCible);
//
//        // Synchronisation bidirectionnelle
//        codeListSource.getSourceRelations().add(relation);
//        codeListCible.getTargetRelations().add(relation);
//
//        ListCodeRelation savedRelation = listCodeRelationDAO.save(relation);
//
//        if (savedRelation.getCodeListSource() == null) {
//            throw new IllegalStateException("CodeListSource association is null after saving ListCodeRelation");
//        }
//        if (savedRelation.getCodeListCible() == null) {
//            throw new IllegalStateException("CodeListCible association is null after saving ListCodeRelation");
//        }
//
//        return listCodeRelationMapper.toDTO(savedRelation);
//    }
//
//    @Override
//    @Transactional
//    public ListCodeRelationDTO updateListCodeRelation(Long id, ListCodeRelationDTO dto) {
//        Optional<ListCodeRelation> existing = listCodeRelationDAO.findById(id); // Plus d'erreur ici
//        if (existing.isEmpty()) {
//            throw new RuntimeException("ListCodeRelation not found");
//        }
//        ListCodeRelation toUpdate = existing.get();
//
//        toUpdate.setTypeRelation(dto.getTypeRelation());
//
//        ListCodeRelation updatedRelation = listCodeRelationDAO.save(toUpdate);
//        return listCodeRelationMapper.toDTO(updatedRelation);
//    }
//
//    @Override
//    public ListCodeRelationDTO getListCodeRelationById(Long id) {
//        ListCodeRelation relation = listCodeRelationDAO.findById(id)
//                .orElseThrow(() -> new RuntimeException("ListCodeRelation not found"));
//        return listCodeRelationMapper.toDTO(relation);
//    }
//
//    @Override
//    public List<ListCodeRelationDTO> getAllListCodeRelations() {
//        return listCodeRelationDAO.findAll().stream()
//                .map(listCodeRelationMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteListCodeRelation(Long id) {
//        if (!listCodeRelationDAO.existsById(id)) {
//            throw new RuntimeException("ListCodeRelation not found");
//        }
//        listCodeRelationDAO.deleteById(id);
//    }

    @Override
    @Transactional
    public ListCodeRelationDTO createListCodeRelation(ListCodeRelationDTO dto) {
        if (dto == null || dto.getCodeListSourceCode() == null || dto.getCodeListCibleCode() == null || dto.getTypeRelationCode() == null) {
            throw new IllegalArgumentException("CodeListSourceCode, CodeListCibleCode, and TypeRelationCode must not be null");
        }

        // Vérifier et récupérer CodeList source
        CodeList codeListSource = codeListDAO.findById(dto.getCodeListSourceCode())
                .orElseThrow(() -> new RuntimeException("CodeList source not found: " + dto.getCodeListSourceCode()));

        // Vérifier et récupérer CodeList cible
        CodeList codeListCible = codeListDAO.findById(dto.getCodeListCibleCode())
                .orElseThrow(() -> new RuntimeException("CodeList cible not found: " + dto.getCodeListCibleCode()));

        // Vérifier et récupérer TypeRelation
        TypeRelation typeRelation = typeRelationDAO.findByCode(dto.getTypeRelationCode())
                .orElseThrow(() -> new RuntimeException("TypeRelation not found: " + dto.getTypeRelationCode()));

        ListCodeRelation relation = listCodeRelationMapper.toEntity(dto);
        relation.setCodeListSource(codeListSource);
        relation.setCodeListCible(codeListCible);
        relation.setTypeRelation(typeRelation);

        // Synchronisation bidirectionnelle
        codeListSource.getSourceRelations().add(relation);
        codeListCible.getTargetRelations().add(relation);

        ListCodeRelation savedRelation = listCodeRelationDAO.save(relation);

        if (savedRelation.getCodeListSource() == null) {
            throw new IllegalStateException("CodeListSource association is null after saving ListCodeRelation");
        }
        if (savedRelation.getCodeListCible() == null) {
            throw new IllegalStateException("CodeListCible association is null after saving ListCodeRelation");
        }
        if (savedRelation.getTypeRelation() == null) {
            throw new IllegalStateException("TypeRelation association is null after saving ListCodeRelation");
        }

        return listCodeRelationMapper.toDTO(savedRelation);
    }

    @Override
    @Transactional
    public ListCodeRelationDTO updateListCodeRelation(Long id, ListCodeRelationDTO dto) {
        Optional<ListCodeRelation> existing = listCodeRelationDAO.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("ListCodeRelation not found");
        }
        ListCodeRelation toUpdate = existing.get();

        // Mise à jour du TypeRelation si fourni
        if (dto.getTypeRelationCode() != null) {
            TypeRelation typeRelation = typeRelationDAO.findByCode(dto.getTypeRelationCode())
                    .orElseThrow(() -> new RuntimeException("TypeRelation not found: " + dto.getTypeRelationCode()));
            toUpdate.setTypeRelation(typeRelation);
        }

        toUpdate.setDescription(dto.getDescription());

        ListCodeRelation updatedRelation = listCodeRelationDAO.save(toUpdate);
        return listCodeRelationMapper.toDTO(updatedRelation);
    }

    @Override
    public ListCodeRelationDTO getListCodeRelationById(Long id) {
        ListCodeRelation relation = listCodeRelationDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("ListCodeRelation not found"));
        return listCodeRelationMapper.toDTO(relation);
    }

    @Override
    public List<ListCodeRelationDTO> getAllListCodeRelations() {
        return listCodeRelationDAO.findAll().stream()
                .map(listCodeRelationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteListCodeRelation(Long id) {
        if (!listCodeRelationDAO.existsById(id)) {
            throw new RuntimeException("ListCodeRelation not found");
        }
        listCodeRelationDAO.deleteById(id);
    }
}