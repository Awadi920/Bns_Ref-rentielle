package com.bns.bnsref.Service;

import com.bns.bnsref.DTO.ReferenceDTO;

import java.util.*;

public interface ReferenceService {
    List<ReferenceDTO> getAllReferences();
    Optional<ReferenceDTO> getReferenceByIdOrLabel(String idOrLabel);
}
