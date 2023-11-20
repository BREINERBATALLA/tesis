package com.breiner.tesis.dto.request;

import com.breiner.tesis.enumeration.Status;

public record UpdateStatusRequestDto(Status status, String message) {
}
