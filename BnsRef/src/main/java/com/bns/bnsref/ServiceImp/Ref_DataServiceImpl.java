package com.bns.bnsref.ServiceImp;


import com.bns.bnsref.DAO.CodeListDAO;
import com.bns.bnsref.DAO.Ref_DataDAO;
import com.bns.bnsref.DTO.Ref_DataDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Ref_Data;
import com.bns.bnsref.Mappers.Ref_DataMapper;
import com.bns.bnsref.Service.Ref_DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Ref_DataServiceImpl implements Ref_DataService {

    private final Ref_DataDAO refDataDAO;
    private final Ref_DataMapper refDataMapper;
    private final CodeListDAO codeListDAO;

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

        // Retourner le DTO
        return refDataMapper.toDTO(savedRefData);
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

    @Override
    public List<Ref_DataDTO> getAllRefData() {
        return refDataDAO.findAll().stream()
                .map(refDataMapper::toDTO)
                .collect(Collectors.toList());
    }
}
