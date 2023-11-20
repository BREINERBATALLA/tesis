package com.breiner.tesis.service;

import com.breiner.tesis.dto.request.FundationInfoDto;

public interface lFundationInfoService {
    FundationInfoDto getFundationInfo();
    String updateFundationInfo(String key, String content);
}
