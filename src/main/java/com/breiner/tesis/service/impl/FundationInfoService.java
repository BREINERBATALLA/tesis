package com.breiner.tesis.service.impl;

import com.breiner.tesis.dto.request.FundationInfoDto;
import com.breiner.tesis.service.IFileService;
import com.breiner.tesis.service.lFundationInfoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@RequiredArgsConstructor
@Service
public class FundationInfoService implements lFundationInfoService {

    private final IFileService fileService;
    @Value("${path.properties-fundation}")
    private String pathToProperties;

    @Override
    public FundationInfoDto getFundationInfo() {
        Properties fundationProperties = fileService.setProperties();
        return FundationInfoDto.builder()
                .name(fundationProperties.getProperty("name"))
                .mission(fundationProperties.getProperty("mission"))
                .vision(fundationProperties.getProperty("vision"))
                .address(fundationProperties.getProperty("address"))
                .telephoneNumber(fundationProperties.getProperty("telephone"))
                .build();
    }

    @Override
    public String updateFundationInfo(@NotNull(message = "Key can't be null") String key,
                                    @NotNull(message = "Key can't be null") String newValue) {
        Properties fundationProperties = fileService.setProperties();
        fundationProperties.setProperty(key,newValue);
        try (FileOutputStream salida = new FileOutputStream(pathToProperties)) {
            fundationProperties.store(salida, "");
            return "Updated sucessfully";
        } catch (FileNotFoundException e) {
            return "Updated unsuccessfully, file not found";
        } catch (IOException e) {
            return "Updated unsuccessfully, file not found";
        }
    }
}
