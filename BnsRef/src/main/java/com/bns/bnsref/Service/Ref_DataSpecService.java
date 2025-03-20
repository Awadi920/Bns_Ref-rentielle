package com.bns.bnsref.Service;

import com.bns.bnsref.DTO.Ref_DataSpecDTO;

import java.util.*;

public interface Ref_DataSpecService {
    Ref_DataSpecDTO addRefDataSpec(Ref_DataSpecDTO refDataSpecDTO);
    Ref_DataSpecDTO updateRefDataSpec(String codeRefDataSpec, Ref_DataSpecDTO refDataSpecDTO);
    void deleteRefDataSpec(String codeRefDataSpec);
    Ref_DataSpecDTO getRefDataSpecById(String codeRefDataSpec);
    List<Ref_DataSpecDTO> getAllRefDataSpec();
}
