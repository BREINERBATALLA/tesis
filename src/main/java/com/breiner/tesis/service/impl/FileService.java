package com.breiner.tesis.service.impl;

import com.breiner.tesis.dto.request.AdoptionPetDto;
import com.breiner.tesis.dto.response.ReportDto;
import com.breiner.tesis.entity.AdoptionPet;
import com.breiner.tesis.entity.Quality;
import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import com.breiner.tesis.repository.AdoptionPetRepository;
import com.breiner.tesis.repository.AdoptionRequestRepository;
import com.breiner.tesis.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService implements IFileService {

    private final S3Client s3Client;
    @Value("${aws.bucket-name}")
    private String bucketName;
    @Value("${path.report}")
    private String pathToReports;
    private final AdoptionPetRepository adoptionPetRepository;
    private final AdoptionRequestRepository adoptionRequestRepository;
    @Value("${path.properties-fundation}")
    private String pathToProperties;

    public String uploadFile(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String nameFile = LocalDate.now().toString()+uuid+file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(nameFile)
                .build();
        try {
            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            if(false){
                throw new Exception("something went wrong saving image");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
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

    public void uploadDataFromCSV(MultipartFile file){
        saveDataFromCsv(file);
    }

    @Override
    public String exportReport(String reportFormat) {
        List<ReportDto> reportDtos = adoptionRequestRepository.listAdoptionRequestReport();
        try {
            File file = ResourceUtils.getFile("classpath:adoptionrequest.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            //mapear datasource al reporte

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Breiner Batalla");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportDtos);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            UUID uuid = UUID.randomUUID();
            if(reportFormat.equalsIgnoreCase("html")) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, pathToReports+uuid.toString()+".html");
            }
            if(reportFormat.equalsIgnoreCase("pdf")){
                JasperExportManager.exportReportToPdfFile(jasperPrint, pathToReports+uuid.toString()+".pdf");
            }
            return "Report has been generated in path: "+pathToReports+uuid.toString();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Properties setProperties() {
        Properties fundationProperties = new Properties();
        try (FileInputStream entry = new FileInputStream(pathToProperties)) {
            // Cargar las propiedades desde el archivo
             fundationProperties.load(entry);
             return fundationProperties;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasCsvFormat(MultipartFile file) {
        String type = "text/CSV";
        return type.equals(file.getContentType())? true: false;
    }

    private void saveDataFromCsv(MultipartFile file) {
        try{
            List<AdoptionPet> adoptionPets = csvToAdoptionPets(file.getInputStream());
            adoptionPetRepository.saveAll(adoptionPets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<AdoptionPet> csvToAdoptionPets(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){

            List<AdoptionPet> adoptionPets = new ArrayList<AdoptionPet>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                AdoptionPet adoptionPet = transformData(csvRecord);
                adoptionPets.add(adoptionPet);
            }

            return adoptionPets;
        }catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private AdoptionPet transformData(CSVRecord csvRecord){
        AdoptionPet adoptionPet = new AdoptionPet();

        String adoptado = csvRecord.get("Adoptado");
        if(adoptado.equalsIgnoreCase("Si")){
            adoptionPet.setAdopted(true);
        }
        if(adoptado.equalsIgnoreCase("No")){
            adoptionPet.setAdopted(false);
        }

        String vacunado = csvRecord.get("Vacunado");
        if(vacunado.equalsIgnoreCase("Si")){
            adoptionPet.setVaccinated(true);
        }
        if(vacunado.equalsIgnoreCase("No")){
            adoptionPet.setVaccinated(false);
        }

        String desparasitado = csvRecord.get("Desparasitado");
        if(desparasitado.equalsIgnoreCase("Si")){
            adoptionPet.setDewormed(true);
        }
        if(desparasitado.equalsIgnoreCase("No")){
            adoptionPet.setDewormed(false);
        }

        adoptionPet.setPhoto(csvRecord.get("Foto"));
        List<String> qualities = Arrays.stream(csvRecord.get("Cualidades").split(",")).toList();
        List<Quality> qualityList = new ArrayList<>();
        for(String a: qualities) {
            Quality quality = new Quality();
            quality.setDescription(a);
            qualityList.add(quality);
        }
        adoptionPet.setQualityList(qualityList);
        adoptionPet.setDescription(csvRecord.get("Descripcion"));


        adoptionPet.setAgeInMonths(AdoptionPet.calculateAgeInMonths(
                LocalDate.parse(csvRecord.get("Nacimiento"))));

        adoptionPet.setBirthDate(LocalDate.parse(csvRecord.get("Nacimiento")));

        if(csvRecord.get("Sexo").equals("Macho")) {
               adoptionPet.setSex(Sex.MALE);
        }
        if(csvRecord.get("Sexo").equals("Hembra")) {
                adoptionPet.setSex(Sex.FEMALE);
        }
        if(csvRecord.get("TipoMascota").equals("Perro")) {
            adoptionPet.setTypePet(TypePet.DOG);
        }
        if(csvRecord.get("TipoMascota").equals("Gato")) {
            adoptionPet.setTypePet(TypePet.CAT);
        }
        adoptionPet.setSize((csvRecord.get("Tamaño")));
        adoptionPet.setAdoptionCondition(csvRecord.get("CondicionAdopcion"));
        adoptionPet.setEyeColor(csvRecord.get("ColorOjos"));
        adoptionPet.setRace(csvRecord.get("Raza"));
        adoptionPet.setName(csvRecord.get("Nombre"));
        adoptionPet.setCoatType(csvRecord.get("Pelaje"));

      return adoptionPet;
    }
}
