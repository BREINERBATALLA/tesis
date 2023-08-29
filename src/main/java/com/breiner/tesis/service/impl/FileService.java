package com.breiner.tesis.service.impl;

import com.breiner.tesis.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService implements IFileService {

    private final S3Client s3Client;
    @Value("${aws.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        String nameFile = LocalDate.now().toString()+file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(nameFile)
                .build();
        try {
            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nameFile;
    }


    public void deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);

        /** Verificar si la eliminación fue exitosa
         if (deleteObjectResponse.sdkHttpResponse().isSuccessful()) {
         return "El archivo de audio se eliminó correctamente";
         } else {
         return "No se pudo eliminar el archivo de audio";
         }
         */
    }
}
