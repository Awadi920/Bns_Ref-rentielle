package com.bns.bnsref.Service;

import com.bns.bnsref.DTO.Ref_DataSpecValueDTO;

import java.util.*;

public interface Ref_DataSpecValueService {
    Ref_DataSpecValueDTO addRefDataSpecValue(Ref_DataSpecValueDTO refDataSpecValueDTO);
    Ref_DataSpecValueDTO updateRefDataSpecValue(String codeRefDataSpecValue, Ref_DataSpecValueDTO refDataSpecValueDTO);
    void deleteRefDataSpecValue(String codeRefDataSpecValue);
    Ref_DataSpecValueDTO getRefDataSpecValueById(String codeRefDataSpecValue);
    List<Ref_DataSpecValueDTO> getAllRefDataSpecValues();
}
