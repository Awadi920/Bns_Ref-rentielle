package com.bns.bnsref.Service;

import com.bns.bnsref.dto.MetaDataDTO;

import java.util.*;


public interface MetaDataService {
    MetaDataDTO addMetaData(MetaDataDTO metaDataDTO);
    MetaDataDTO updateMetaData(String codeMetaData, MetaDataDTO metaDataDTO);
    void deleteMetaData(String codeMetaData);
    MetaDataDTO getMetaDataById(String codeMetaData);
    List<MetaDataDTO> getAllMetaData();
}
