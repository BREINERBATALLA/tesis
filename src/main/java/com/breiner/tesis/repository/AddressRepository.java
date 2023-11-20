package com.breiner.tesis.repository;

import com.breiner.tesis.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
