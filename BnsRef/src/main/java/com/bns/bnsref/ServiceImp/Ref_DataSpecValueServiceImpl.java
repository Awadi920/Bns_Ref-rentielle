package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.DAO.Ref_DataSpecDAO;
import com.bns.bnsref.DAO.Ref_DataSpecValueDAO;
import com.bns.bnsref.DTO.Ref_DataSpecValueDTO;
import com.bns.bnsref.Entity.Ref_DataSpec;
import com.bns.bnsref.Entity.Ref_DataSpecValue;
import com.bns.bnsref.Mappers.Ref_DataSpecValueMapper;
import com.bns.bnsref.Service.Ref_DataSpecValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Ref_DataSpecValueServiceImpl implements Ref_DataSpecValueService {

    private final Ref_DataSpecValueDAO refDataSpecValueDAO;
    private final Ref_DataSpecValueMapper refDataSpecValueMapper;
    private final Ref_DataSpecDAO refDataSpecDAO;

    @Override
    public Ref_DataSpecValueDTO addRefDataSpecValue(Ref_DataSpecValueDTO refDataSpecValueDTO) {
        // Vérifier si le Ref_DataSpec existe
        Ref_DataSpec refDataSpec = refDataSpecDAO.findById(refDataSpecValueDTO.getCodeRefDataSpec())
                .orElseThrow(() -> new RuntimeException("Ref_DataSpec not found with code: " + refDataSpecValueDTO.getCodeRefDataSpec()));

        // Générer automatiquement le codeRefDataSpecValue
        String lastCodeRefDataSpecValue = refDataSpecValueDAO.findLastRefDataSpecValueCode().orElse("RDSV000");
        int nextId = Integer.parseInt(lastCodeRefDataSpecValue.replace("RDSV", "")) + 1;
        String newCodeRefDataSpecValue = String.format("RDSV%03d", nextId); // Format RDSV001, RDSV002...

        // Convertir le DTO en entité
        Ref_DataSpecValue refDataSpecValue = refDataSpecValueMapper.toEntity(refDataSpecValueDTO);
        refDataSpecValue.setCodeRefDataSpecValue(newCodeRefDataSpecValue);
        refDataSpecValue.setRefDataSpec(refDataSpec); // Associer le Ref_DataSpec

        // Sauvegarder l'entité
        Ref_DataSpecValue savedRefDataSpecValue = refDataSpecValueDAO.save(refDataSpecValue);

        // Retourner le DTO
        return refDataSpecValueMapper.toDTO(savedRefDataSpecValue);
    }


    @Override
    public Ref_DataSpecValueDTO updateRefDataSpecValue(String codeRefDataSpecValue, Ref_DataSpecValueDTO refDataSpecValueDTO) {
        Ref_DataSpecValue refDataSpecValue = refDataSpecValueDAO.findById(codeRefDataSpecValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataSpecValue not found"));

        refDataSpecValue.setValue(refDataSpecValueDTO.getValue());

        var refDataSpec = refDataSpecDAO.findById(refDataSpecValueDTO.getCodeRefDataSpec())
                .orElseThrow(() -> new RuntimeException("Ref_DataSpec not found"));
        refDataSpecValue.setRefDataSpec(refDataSpec);

        return refDataSpecValueMapper.toDTO(refDataSpecValueDAO.save(refDataSpecValue));
    }

    @Override
    public void deleteRefDataSpecValue(String codeRefDataSpecValue) {
        refDataSpecValueDAO.deleteById(codeRefDataSpecValue);
    }

    @Override
    public Ref_DataSpecValueDTO getRefDataSpecValueById(String codeRefDataSpecValue) {
        return refDataSpecValueMapper.toDTO(refDataSpecValueDAO.findById(codeRefDataSpecValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataSpecValue not found")));
    }

    @Override
    public List<Ref_DataSpecValueDTO> getAllRefDataSpecValues() {
        return refDataSpecValueDAO.findAll().stream()
                .map(refDataSpecValueMapper::toDTO)
                .collect(Collectors.toList());
    }
}
