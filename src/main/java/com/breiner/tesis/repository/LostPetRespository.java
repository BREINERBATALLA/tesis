package com.breiner.tesis.repository;

import com.breiner.tesis.entity.LostPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostPetRespository extends JpaRepository<LostPet, Long> {
}
