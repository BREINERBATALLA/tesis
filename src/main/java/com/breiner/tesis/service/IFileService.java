package com.breiner.tesis.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Properties;

public interface IFileService {

    String uploadFile(MultipartFile file);

    void deleteFile(String fileName);
    void uploadDataFromCSV(MultipartFile file);

    String exportReport(String reportFomrat);

    Properties setProperties();

}
