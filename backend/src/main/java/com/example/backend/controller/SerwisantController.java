package com.example.backend.controller;


import com.example.backend.entity.Dzialanie;
import com.example.backend.entity.Serwisant;
import com.example.backend.exception.NotFoundException;
import com.example.backend.service.CrudService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/serwisant")
@RequiredArgsConstructor
public class SerwisantController {
    private final CrudService service;

    @GetMapping
    public List<Serwisant> getAllSerwisants() {
        return service.getAllSerwisant();
    }

    @GetMapping("{id}")
    public ResponseEntity<Serwisant> getSerwisant(@PathVariable Integer id) {
        return service.getSerwisantById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateSerwisant(@PathVariable Integer id,
    @RequestBody Serwisant serwisant) {
        try {
            service.updateSerwisant(
                    id,
                    serwisant.getNazwisko(),
                    serwisant.isAktywny(),
                    serwisant.getEmail()
            );
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/dzialania")
    public ResponseEntity<List<Dzialanie>> dzialaniaSerwisanta(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.getDzialaniaSerwisanata(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
