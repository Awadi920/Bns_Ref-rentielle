package com.bns.bnsref.Service;

import com.bns.bnsref.DTO.CodeListDTO;

import java.util.*;

public interface CodeListService {

    CodeListDTO addCodeList(CodeListDTO codeListDTO);
    CodeListDTO updateCodeList(String codeList, CodeListDTO codeListDTO);
    void deleteCodeList(String codeList);
    CodeListDTO getCodeListById(String codeList);
    List<CodeListDTO> getAllCodeLists();

}
