package com.bns.bnsref.Service;

import com.bns.bnsref.dto.Ref_DataSpecDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface Ref_DataSpecService {
    Ref_DataSpecDTO addRefDataSpec(Ref_DataSpecDTO refDataSpecDTO);
    Ref_DataSpecDTO updateRefDataSpec(String codeRefDataSpec, Ref_DataSpecDTO refDataSpecDTO);
    void deleteRefDataSpec(String codeRefDataSpec);
    Ref_DataSpecDTO getRefDataSpecById(String codeRefDataSpec);
//    List<Ref_DataSpecDTO> getAllRefDataSpec();
    Page<Ref_DataSpecDTO> getAllRefDataSpec(Pageable pageable); // Updated method


    List<Ref_DataSpecDTO> getFilteredAndSortedRefDataSpec(); // Nouvelle méthode

}
