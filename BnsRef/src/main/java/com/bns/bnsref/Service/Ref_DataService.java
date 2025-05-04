package com.bns.bnsref.Service;

import com.bns.bnsref.dto.Ref_DataDTO;

import java.util.*;

public interface Ref_DataService {
    Ref_DataDTO addRefData(Ref_DataDTO refDataDTO);
    Ref_DataDTO updateRefData(String codeRefData, Ref_DataDTO refDataDTO);
    void deleteRefData(String codeRefData);
    Ref_DataDTO getRefDataById(String codeRefData);
    List<Ref_DataDTO> getAllRefData();
    List<Ref_DataDTO> getFilteredAndSortedRefData();

}

