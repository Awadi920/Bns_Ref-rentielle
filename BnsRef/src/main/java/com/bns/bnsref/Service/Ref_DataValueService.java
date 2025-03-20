package com.bns.bnsref.Service;

import com.bns.bnsref.DTO.Ref_DataValueDTO;

import java.util.*;

public interface Ref_DataValueService {
    Ref_DataValueDTO addRefDataValue(Ref_DataValueDTO refDataValueDTO);
    Ref_DataValueDTO updateRefDataValue(String codeRefDataValue, Ref_DataValueDTO refDataValueDTO);
    void deleteRefDataValue(String codeRefDataValue);
    Ref_DataValueDTO getRefDataValueById(String codeRefDataValue);
    List<Ref_DataValueDTO> getAllRefDataValues();
}
