package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.DAO.CategoryDAO;
import com.bns.bnsref.DAO.CodeListDAO;
import com.bns.bnsref.DAO.DomainDAO;
import com.bns.bnsref.DAO.ProducerDAO;
import com.bns.bnsref.DTO.CodeListDTO;
import com.bns.bnsref.Entity.Category;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Domain;
import com.bns.bnsref.Entity.Producer;
import com.bns.bnsref.Mappers.CodeListMapper;
import com.bns.bnsref.Service.CodeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeListServiceImpl implements CodeListService {

    private final CodeListDAO codeListDAO;
    private final DomainDAO domainDAO;
    private final CategoryDAO categoryDAO;
    private final ProducerDAO producerDAO;

    private final CodeListMapper codeListMapper;

    @Override
    public CodeListDTO addCodeList(CodeListDTO codeListDTO) {
        Domain domain = domainDAO.findById(codeListDTO.getDomainCode())
                .orElseThrow(() -> new RuntimeException("Domain non trouvé avec le code: " + codeListDTO.getDomainCode()));

        Category category = categoryDAO.findById(codeListDTO.getCodeCategory())
                .orElseThrow(() -> new RuntimeException("Category non trouvée avec le code: " + codeListDTO.getCodeCategory()));

        Producer producer = producerDAO.findById(codeListDTO.getProducerCode())
                .orElseThrow(() -> new RuntimeException("Producer non trouvé avec le code: " + codeListDTO.getProducerCode()));

        // Générer automatiquement le codeList
        String lastCodeList = codeListDAO.findLastCodeList().orElse("CL000");
        int nextId = Integer.parseInt(lastCodeList.replace("CL", "")) + 1;
        String newCodeList = String.format("CL%03d", nextId); // Format CL001, CL002...

        CodeList codeList = codeListMapper.toEntity(codeListDTO, domain, category, producer);
        codeList.setCodeList(newCodeList);
        codeList.setCreationDate(LocalDateTime.now());

        codeListDAO.save(codeList);
        return codeListMapper.toDTO(codeList);
    }


//    @Override
//    public CodeListDTO updateCodeList(String codeListId, CodeListDTO codeListDTO) {
//        Optional<CodeList> existingCodeList = codeListDAO.findById(codeListId);
//
//        if (existingCodeList.isEmpty()) {
//            throw new RuntimeException("CodeList non trouvé !");
//        }
//
//        Optional<Domain> domain = domainDAO.findById(codeListDTO.getDomainCode());
//        Optional<Category> category = categoryDAO.findById(codeListDTO.getCodeCategory());
//        Optional<Producer> producer = producerDAO.findById(codeListDTO.getProducerCode());
//
//        if (domain.isEmpty() || category.isEmpty() || producer.isEmpty()) {
//            throw new RuntimeException("Domain, Category ou Producer non trouvés !");
//        }
//
//        CodeList codeList = existingCodeList.get();
//        codeList.setLabelList(codeListDTO.getLabelList());
//        codeList.setDescription(codeListDTO.getDescription());
//        codeList.setCreationDate(codeListDTO.getCreationDate());
//        codeList.setDomain(domain.get());
//        codeList.setCategory(category.get());
//        codeList.setProducer(producer.get());
//
//        codeListDAO.save(codeList);
//        return codeListMapper.toDTO(codeList);
//    }

    public CodeListDTO updateCodeList(String codeListId, CodeListDTO codeListDTO) {
        CodeList existingCodeList = codeListDAO.findById(codeListId)
                .orElseThrow(() -> new RuntimeException("CodeList not found"));

        // Mettre à jour les champs sans écraser la creationDate
        existingCodeList.setLabelList(codeListDTO.getLabelList());
        existingCodeList.setDescription(codeListDTO.getDescription());

        // Vérifier si les nouvelles valeurs existent avant de les mettre à jour
        if (codeListDTO.getDomainCode() != null) {
            Domain domain = domainDAO.findById(codeListDTO.getDomainCode())
                    .orElseThrow(() -> new RuntimeException("Domain not found"));
            existingCodeList.setDomain(domain);
        }

        if (codeListDTO.getCodeCategory() != null) {
            Category category = categoryDAO.findById(codeListDTO.getCodeCategory())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingCodeList.setCategory(category);
        }

        if (codeListDTO.getProducerCode() != null) {
            Producer producer = producerDAO.findById(codeListDTO.getProducerCode())
                    .orElseThrow(() -> new RuntimeException("Producer not found"));
            existingCodeList.setProducer(producer);
        }

        // Sauvegarder sans modifier creationDate
        CodeList updatedCodeList = codeListDAO.save(existingCodeList);

        return codeListMapper.toDTO(updatedCodeList);
    }


    @Override
    public void deleteCodeList(String codeListId) {
        if (!codeListDAO.existsById(codeListId)) {
            throw new RuntimeException("CodeList non trouvé !");
        }
        codeListDAO.deleteById(codeListId);
    }

    @Override
    public CodeListDTO getCodeListById(String codeListId) {
        CodeList codeList = codeListDAO.findById(codeListId)
                .orElseThrow(() -> new RuntimeException("CodeList non trouvé !"));
        return codeListMapper.toDTO(codeList);
    }

    @Override
    public List<CodeListDTO> getAllCodeLists() {
        List<CodeList> codeLists = codeListDAO.findAll();
        return codeLists.stream()
                .map(codeListMapper::toDTO)
                .collect(Collectors.toList());
    }

}
