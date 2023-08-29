package com.breiner.tesis.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    public String uploadFile(MultipartFile file);

    public void deleteFile(String fileName);

}
