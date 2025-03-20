package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.DAO.CodeListDAO;
import com.bns.bnsref.DAO.Ref_DataSpecDAO;
import com.bns.bnsref.DTO.Ref_DataSpecDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Ref_DataSpec;
import com.bns.bnsref.Mappers.Ref_DataSpecMapper;
import com.bns.bnsref.Service.Ref_DataSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Ref_DataSpecServiceImpl implements Ref_DataSpecService {

    private final Ref_DataSpecDAO refDataSpecDAO;
    private final Ref_DataSpecMapper refDataSpecMapper;
    private final CodeListDAO codeListDAO;

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

    @Override
    public List<Ref_DataSpecDTO> getAllRefDataSpec() {
        return refDataSpecDAO.findAll().stream()
                .map(refDataSpecMapper::toDTO)
                .collect(Collectors.toList());
    }
}
