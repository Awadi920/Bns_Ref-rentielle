package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.dao.CodeListDAO;
import com.bns.bnsref.dao.MetaDataDAO;
import com.bns.bnsref.dto.MetaDataDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.MetaData;
import com.bns.bnsref.Mappers.MetaDataMapper;
import com.bns.bnsref.Service.MetaDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaDataServiceImpl implements MetaDataService {

    private final MetaDataDAO metaDataDAO;
    private final CodeListDAO codeListDAO;
    private final MetaDataMapper metaDataMapper;

    @Override
    public MetaDataDTO addMetaData(MetaDataDTO metaDataDTO) {
        // Vérifier si le CodeList existe
        CodeList codeList = codeListDAO.findById(metaDataDTO.getCodeList())
                .orElseThrow(() -> new RuntimeException("CodeList not found with code: " + metaDataDTO.getCodeList()));

        // Générer automatiquement le codeMetaData
        String lastCodeMetaData = metaDataDAO.findLastMetaDataCode().orElse("META000");
        int nextId = Integer.parseInt(lastCodeMetaData.replace("META", "")) + 1;
        String newCodeMetaData = String.format("META%03d", nextId); // Format META001, META002...

        // Convertir le DTO en entité
        MetaData metaData = metaDataMapper.toEntity(metaDataDTO, codeList);
        metaData.setCodeMetaData(newCodeMetaData);

        // Sauvegarder l'entité
        MetaData savedMetaData = metaDataDAO.save(metaData);

        // Retourner le DTO
        return metaDataMapper.toDTO(savedMetaData);
    }


    @Override
    public MetaDataDTO updateMetaData(String codeMetaData, MetaDataDTO metaDataDTO) {
        MetaData metaData = metaDataDAO.findById(codeMetaData)
                .orElseThrow(() -> new RuntimeException("MetaData not found"));

        // Mise à jour de la MetaData
        metaData.setNameMetaData(metaDataDTO.getNameMetaData());
        metaData.setValueMetaData(metaDataDTO.getValueMetaData());
        metaData.setLastUpdate(metaDataDTO.getLastUpdate());
        metaData.setStandard(metaDataDTO.getStandard());

        // Vérifier si le CodeList existe
        CodeList codeList = codeListDAO.findById(metaDataDTO.getCodeList())
                .orElseThrow(() -> new RuntimeException("CodeList not found"));

        metaData.setCodeList(codeList);
        return metaDataMapper.toDTO(metaDataDAO.save(metaData));
    }

    @Override
    public void deleteMetaData(String codeMetaData) {
        metaDataDAO.deleteById(codeMetaData);
    }

    @Override
    public MetaDataDTO getMetaDataById(String codeMetaData) {
        return metaDataMapper.toDTO(metaDataDAO.findById(codeMetaData)
                .orElseThrow(() -> new RuntimeException("MetaData not found")));
    }

    @Override
    public List<MetaDataDTO> getAllMetaData() {
        return metaDataDAO.findAll().stream()
                .map(metaDataMapper::toDTO)
                .collect(Collectors.toList());
    }
}