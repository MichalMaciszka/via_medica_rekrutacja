package com.example.backend.controller;


import com.example.backend.dto.ObszaryZSerwisantami;
import com.example.backend.service.CrudService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/obszar")
@RequiredArgsConstructor
public class ObszarController {
    private final CrudService service;

    @GetMapping
    public List<ObszaryZSerwisantami> getAllObszar() {
        return service.getAllObszaryZSerwisantami();
    }
}
