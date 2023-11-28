package com.breiner.tesis.dto.request;

import jakarta.validation.constraints.NotNull;

public record QualityDto(@NotNull(message = "No puede ir nula") String description) {
}
