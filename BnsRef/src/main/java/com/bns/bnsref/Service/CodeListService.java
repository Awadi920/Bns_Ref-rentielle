package com.bns.bnsref.Service;

import com.bns.bnsref.dto.CodeListDTO;

import java.util.*;

import com.bns.bnsref.dto.CodeListRowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeListService {
    CodeListDTO addCodeList(CodeListDTO codeListDTO);
    CodeListDTO updateCodeList(String codeList, CodeListDTO codeListDTO);
    void deleteCodeList(String codeList);
    CodeListDTO getCodeListById(String codeList);
//    List<CodeListDTO> getAllCodeLists();
    Page<CodeListDTO> getAllCodeLists(Pageable pageable); // Updated method

    List<CodeListDTO> getFilteredAndSortedCodeLists();

    void addCodeListRow(CodeListRowDTO rowDTO); // Nouvelle méthode
    List<CodeListRowDTO> getCodeListRows(String codeListId); // Nouvelle méthode
}
