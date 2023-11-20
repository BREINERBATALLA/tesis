package com.breiner.tesis.dto.request;

import jakarta.persistence.Id;

public record AdoptionFormDto(
                 String activitiesPlanToDoWithThePet,
                 boolean includesPetInCaseOfMove,
                 String reasonOfAdoption,
                 Integer weeklyHoursAvailableForThePet
) {
}

