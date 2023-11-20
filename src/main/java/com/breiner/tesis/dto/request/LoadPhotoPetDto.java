package com.breiner.tesis.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record LoadPhotoPetDto(Long idPet, MultipartFile file) {
}
