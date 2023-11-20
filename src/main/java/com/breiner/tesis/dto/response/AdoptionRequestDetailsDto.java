package com.breiner.tesis.dto.response;

import com.breiner.tesis.enumeration.Status;
import lombok.Builder;

@Builder
public record AdoptionRequestDetailsDto(Long idAdoptionRequest, String name, boolean isAdopted, Status status, String photo){
}
