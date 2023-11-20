package com.breiner.tesis.repository;


import com.breiner.tesis.entity.AdoptionPet;
import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionPetRepository extends JpaRepository<AdoptionPet, Long> {

    List<AdoptionPet> findAllByTypePet(TypePet typePet);

    List<AdoptionPet> findAllByAdoptedIsTrue();

    List<AdoptionPet> findAllByAdoptedIsFalse();

    List<AdoptionPet> findAllByRace(String race);

    List<AdoptionPet> findAllByDewormedIsTrueAndAdoptedIsTrue();

    List<AdoptionPet> findAllBySizeIsLessThanEqual(Integer size);

    List<AdoptionPet> findAllBySex(Sex sex);
}
