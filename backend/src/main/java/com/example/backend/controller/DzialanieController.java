package com.example.backend.controller;


import com.example.backend.dto.AddDzialanieRequest;
import com.example.backend.exception.NotFoundException;
import com.example.backend.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Log
public class DzialanieController {
    private final CrudService service;

    @PostMapping("dzialanie")
    public ResponseEntity<Void> create(@RequestBody AddDzialanieRequest dzialanie) {
        try {
            log.info(dzialanie.toString());
            service.addDzialanie(
                    dzialanie.getOpisDzialania(),
                    Integer.parseInt(dzialanie.getPlanowanyCzas()),
                    Integer.parseInt(dzialanie.getIdentyfikatorSerwisanta()),
                    Integer.parseInt(dzialanie.getIdentyfikatorObszaru())
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
