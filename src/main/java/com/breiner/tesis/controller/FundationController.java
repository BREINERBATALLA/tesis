package com.breiner.tesis.controller;

import com.breiner.tesis.dto.request.FundationInfoDto;
import com.breiner.tesis.service.lFundationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/fundations")
public class FundationController {
    private final lFundationInfoService fundationInfoService;

    @GetMapping()
    public ResponseEntity<FundationInfoDto> getFundationInfo(){
        return ResponseEntity.ok(fundationInfoService.getFundationInfo());
    }
}
