package com.bns.bnsref.Service;

import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.dto.ColumnOrderDTO;
import com.bns.bnsref.dto.ReferenceDTO;

import java.util.*;

public interface ReferenceService {

   List<CodeList> getAllReferences();

   List<ReferenceDTO> getAllReferencesAsDTO();
   List<ReferenceDTO> getAllReferencesWithLang(String lang);
   List<ReferenceDTO> getAllReferencesWithAllTranslations();

   ReferenceDTO getReferenceWithLang(String codeList, String lang);

   ReferenceDTO getReferenceDetailsWithAllTranslations(String codeListCode);

   void updateColumnOrder(String codeListCode, List<ColumnOrderDTO> columnOrders);

}

