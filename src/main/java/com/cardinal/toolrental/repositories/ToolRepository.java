package com.cardinal.toolrental.repositories;

import com.cardinal.toolrental.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Optional<Tool> findByCode(String code);
}
