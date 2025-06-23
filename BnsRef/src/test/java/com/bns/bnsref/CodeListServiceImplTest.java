package com.bns.bnsref;

import com.bns.bnsref.Entity.*;
import com.bns.bnsref.Mappers.CodeListMapper;
import com.bns.bnsref.Mappers.Ref_DataSpecValueMapper;
import com.bns.bnsref.Mappers.Ref_DataValueMapper;
import com.bns.bnsref.ServiceImp.CodeListServiceImpl;
import com.bns.bnsref.dao.*;
import com.bns.bnsref.dto.CodeListDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CodeListServiceImplTest {

    @Mock
    private CodeListDAO codeListDAO;

    @Mock
    private DomainDAO domainDAO;

    @Mock
    private CategoryDAO categoryDAO;

    @Mock
    private ProducerDAO producerDAO;

    @Mock
    private Ref_DataDAO refDataDAO;

    @Mock
    private Ref_DataSpecDAO refDataSpecDAO;


    @Mock
    private CodeListMapper codeListMapper;


    @InjectMocks
    private CodeListServiceImpl codeListService;

    private CodeList codeList;
    private CodeListDTO codeListDTO;
    private Domain domain;
    private Category category;
    private Producer producer;
    private List<Ref_Data> refDataList;

    @BeforeEach
    void setUp() {
        // Initialisation des objets mockés
        codeList = new CodeList();
        codeList.setCodeList("CL001");
        codeList.setLabelList("Test List");
        codeList.setDescription("Description");
        codeList.setCreationDate(LocalDateTime.now());

        codeListDTO = new CodeListDTO();
        codeListDTO.setCodeList("CL001");
        codeListDTO.setLabelList("Test List");
        codeListDTO.setDescription("Description");
        codeListDTO.setDomainCode("DOM001");
        codeListDTO.setCodeCategory("CAT001");
        codeListDTO.setProducerCode("PROD001");

        domain = new Domain();
        domain.setCodeDomain("DOM001");

        category = new Category();
        category.setCodeCategory("CAT001");

        producer = new Producer();
        producer.setCodeProd("PROD001");

        refDataList = new ArrayList<>();
        Ref_Data refData = Ref_Data.builder()
                .codeRefData("REFD001")
                .designation("code")
                .description("Colonne par défaut: code")
                .codeList(codeList)
                .orderPosition(0)
                .build();
        refDataList.add(refData);
    }

    @Test
    void addCodeList() {
        // Configurer les mocks
        when(domainDAO.findById("DOM002")).thenReturn(Optional.of(domain));
        when(categoryDAO.findById("CAT001")).thenReturn(Optional.of(category));
        when(producerDAO.findById("PROD001")).thenReturn(Optional.of(producer));
        when(codeListDAO.findLastCodeList()).thenReturn(Optional.of("CL000"));
        when(codeListDAO.save(any(CodeList.class))).thenReturn(codeList);
        when(refDataDAO.findLastRefDataCode()).thenReturn(Optional.of("REFD000"));
        when(refDataDAO.saveAll(anySet())).thenReturn(refDataList);
        when(codeListMapper.toEntity(any(CodeListDTO.class), any(Domain.class), any(Category.class), any(Producer.class)))
                .thenReturn(codeList);
        when(codeListMapper.toDTO(any(CodeList.class))).thenReturn(codeListDTO);

        // Exécuter le test
        CodeListDTO result = codeListService.addCodeList(codeListDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("CL001", result.getCodeList());
        assertEquals("Test List", result.getLabelList());
        verify(codeListDAO, times(2)).save(any(CodeList.class)); // Deux appels à save
        verify(refDataDAO, times(1)).saveAll(anySet());
        verify(codeListMapper, times(1)).toDTO(any(CodeList.class));
    }

    @Test
    void updateCodeList() {
        // Configurer les mocks
        when(codeListDAO.findById("CL001")).thenReturn(Optional.of(codeList));
        when(domainDAO.findById("DOM001")).thenReturn(Optional.of(domain));
        when(categoryDAO.findById("CAT001")).thenReturn(Optional.of(category));
        when(producerDAO.findById("PROD001")).thenReturn(Optional.of(producer));
        when(codeListDAO.save(any(CodeList.class))).thenReturn(codeList);
        when(codeListMapper.toDTO(any(CodeList.class))).thenReturn(codeListDTO);

        // Mise à jour du DTO
        codeListDTO.setLabelList("Updated List");

        // Exécuter le test
        CodeListDTO result = codeListService.updateCodeList("CL001", codeListDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Updated List", result.getLabelList());
        verify(codeListDAO, times(1)).findById("CL001");
        verify(codeListDAO, times(1)).save(any(CodeList.class));
        verify(codeListMapper, times(1)).toDTO(any(CodeList.class));
    }

    @Test
    void deleteCodeList() {
        // Configurer les mocks
        when(codeListDAO.findById("CL001")).thenReturn(Optional.of(codeList));
        when(refDataSpecDAO.findByCodeListCodeList("CL001")).thenReturn(new ArrayList<>());

        // Exécuter le test
        codeListService.deleteCodeList("CL001");

        // Vérifications
        verify(refDataSpecDAO, times(1)).findByCodeListCodeList("CL001");
        verify(refDataSpecDAO, times(1)).deleteAll(anyList());
        verify(codeListDAO, times(1)).delete(codeList);
    }

    @Test
    void getCodeListById() {
        // Configurer les mocks
        when(codeListDAO.findById("CL001")).thenReturn(Optional.of(codeList));
        when(codeListMapper.toDTO(any(CodeList.class))).thenReturn(codeListDTO);

        // Exécuter le test
        CodeListDTO result = codeListService.getCodeListById("CL001");

        // Vérifications
        assertNotNull(result);
        assertEquals("CL001", result.getCodeList());
        verify(codeListDAO, times(1)).findById("CL001");
        verify(codeListMapper, times(1)).toDTO(any(CodeList.class));
    }

    @Test
    void getAllCodeLists() {
        // Préparation des données
        List<CodeList> codeLists = Collections.singletonList(codeList);
        Page<CodeList> page = new PageImpl<>(codeLists);
        Pageable pageable = PageRequest.of(0, 10);

        // Configurer les mocks
        when(codeListDAO.findAll(pageable)).thenReturn(page);
        when(codeListMapper.toDTO(any(CodeList.class))).thenReturn(codeListDTO);

        // Exécuter le test
        Page<CodeListDTO> result = codeListService.getAllCodeLists(pageable);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("CL001", result.getContent().get(0).getCodeList());
        verify(codeListDAO, times(1)).findAll(pageable);
        verify(codeListMapper, times(1)).toDTO(any(CodeList.class));
    }
}