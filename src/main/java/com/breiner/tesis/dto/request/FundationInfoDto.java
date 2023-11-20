package com.breiner.tesis.dto.request;

import lombok.Builder;

@Builder
public record FundationInfoDto(String name, String mission, String vision,
                               String address, String telephoneNumber) {
}
