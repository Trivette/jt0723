package com.cardinal.toolrental.controllers;

import com.cardinal.toolrental.dto.RentalRequestDTO;
import com.cardinal.toolrental.entities.RentalAgreement;
import com.cardinal.toolrental.entities.Tool;
import com.cardinal.toolrental.repositories.RentalAgreementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RentalController {
    private final RentalAgreementRepository repository;

    @Autowired
    private ToolController toolController;
    @Autowired
    private ObjectMapper mapper;

    public RentalController(RentalAgreementRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rentals")
    public List<RentalAgreement> getAll() {
        return repository.findAll();
    }

    @PostMapping("/rental")
    @JsonDeserialize
    public ResponseEntity<RentalAgreement> createRentalAgreement(@Valid @RequestBody RentalRequestDTO request) {
        System.out.println(String.format("Req: %s", request));
        Tool tool = toolController.getToolByCode(request.getToolCode());
        RentalAgreement newRental = new RentalAgreement(tool, request);
        return new ResponseEntity<>(repository.save(newRental), HttpStatus.CREATED);
    }

}
