package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.DAO.LanguageDAO;
import com.bns.bnsref.DAO.Ref_DataDAO;
import com.bns.bnsref.DAO.Ref_DataValueDAO;
import com.bns.bnsref.DTO.Ref_DataValueDTO;
import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.Entity.Ref_Data;
import com.bns.bnsref.Entity.Ref_DataValue;
import com.bns.bnsref.Mappers.Ref_DataValueMapper;
import com.bns.bnsref.Service.Ref_DataValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Ref_DataValueServiceImpl implements Ref_DataValueService {

    private final Ref_DataValueDAO refDataValueDAO;
    private final Ref_DataValueMapper refDataValueMapper;
    private final Ref_DataDAO refDataDAO;
    private final LanguageDAO languageDAO;


    @Override
    public Ref_DataValueDTO addRefDataValue(Ref_DataValueDTO refDataValueDTO) {
        // Vérifier si le Ref_Data existe
        Ref_Data refData = refDataDAO.findById(refDataValueDTO.getCodeRefData())
                .orElseThrow(() -> new RuntimeException("Ref_Data not found with code: " + refDataValueDTO.getCodeRefData()));

        // Vérifier si le Language existe
        Language language = languageDAO.findById(refDataValueDTO.getCodeLanguage())
                .orElseThrow(() -> new RuntimeException("Language not found with code: " + refDataValueDTO.getCodeLanguage()));

        // Générer automatiquement le codeRefDataValue
        String lastCodeRefDataValue = refDataValueDAO.findLastRefDataValueCode().orElse("RDV000");
        int nextId = Integer.parseInt(lastCodeRefDataValue.replace("RDV", "")) + 1;
        String newCodeRefDataValue = String.format("RDV%03d", nextId); // Format RDV001, RDV002...

        // Convertir le DTO en entité
        Ref_DataValue refDataValue = refDataValueMapper.toEntity(refDataValueDTO);
        refDataValue.setCodeRefDataValue(newCodeRefDataValue);
        refDataValue.setRefData(refData); // Associer le Ref_Data
        refDataValue.setLanguage(language); // Associer le Language

        // Sauvegarder l'entité
        Ref_DataValue savedRefDataValue = refDataValueDAO.save(refDataValue);

        // Retourner le DTO
        return refDataValueMapper.toDTO(savedRefDataValue);
    }


    @Override
    public Ref_DataValueDTO updateRefDataValue(String codeRefDataValue, Ref_DataValueDTO refDataValueDTO) {
        Ref_DataValue refDataValue = refDataValueDAO.findById(codeRefDataValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found"));

        refDataValue.setValue(refDataValueDTO.getValue());

        var refData = refDataDAO.findById(refDataValueDTO.getCodeRefData())
                .orElseThrow(() -> new RuntimeException("Ref_Data not found"));
        refDataValue.setRefData(refData);

        var language = languageDAO.findById(refDataValueDTO.getCodeLanguage())
                .orElseThrow(() -> new RuntimeException("Language not found"));
        refDataValue.setLanguage(language);

        return refDataValueMapper.toDTO(refDataValueDAO.save(refDataValue));
    }

    @Override
    public void deleteRefDataValue(String codeRefDataValue) {
        refDataValueDAO.deleteById(codeRefDataValue);
    }

    @Override
    public Ref_DataValueDTO getRefDataValueById(String codeRefDataValue) {
        return refDataValueMapper.toDTO(refDataValueDAO.findById(codeRefDataValue)
                .orElseThrow(() -> new RuntimeException("Ref_DataValue not found")));
    }

    @Override
    public List<Ref_DataValueDTO> getAllRefDataValues() {
        return refDataValueDAO.findAll().stream()
                .map(refDataValueMapper::toDTO)
                .collect(Collectors.toList());
    }
}
