package com.cardinal.toolrental.controllers;

import com.cardinal.toolrental.entities.Tool;
import com.cardinal.toolrental.repositories.ToolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToolController {

    private final ToolRepository repository;

    public ToolController(ToolRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tools")
    public List<Tool> getAll() {
        return repository.findAll();
    }

    @GetMapping("/tools/code/{code}")
    public Tool getToolByCode(@PathVariable String code) {
        return repository.findByCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No tool code found: %s", code)));
    }

}
