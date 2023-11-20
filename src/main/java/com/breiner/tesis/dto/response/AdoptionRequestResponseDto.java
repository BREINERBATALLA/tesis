package com.breiner.tesis.dto.response;

import com.breiner.tesis.enumeration.Status;

public record AdoptionRequestResponseDto(Status status, Long idAdoptionRequest) {
}
