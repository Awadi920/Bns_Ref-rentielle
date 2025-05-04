package com.bns.bnsref.Service;

import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.dto.ReferenceDTO;

import java.util.*;

public interface ReferenceService {

   // List<CodeList> getAllReferences();
   // List<ReferenceDTO> getAllReferencesDTO(boolean includeTranslations, String lang);
   // List<ReferenceDTO> getAllReferencesWithTranslations();

   List<CodeList> getAllReferences();

   List<ReferenceDTO> getAllReferencesAsDTO();
   List<ReferenceDTO> getAllReferencesWithLang(String lang);
   List<ReferenceDTO> getAllReferencesWithAllTranslations();

   // Nouvelles méthodes pour les transformations individuelles
  // ReferenceDTO getReferenceWithAllTranslations(String codeList);
   ReferenceDTO getReferenceWithLang(String codeList, String lang);


   ReferenceDTO getReferenceDetailsWithAllTranslations(String codeListCode);

}

