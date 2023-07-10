package com.cardinal.toolrental.repositories;

import com.cardinal.toolrental.dto.RentalRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRequestRepository extends JpaRepository<RentalRequestDTO, Long> {

}