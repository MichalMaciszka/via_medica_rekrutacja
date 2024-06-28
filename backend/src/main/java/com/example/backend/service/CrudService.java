package com.example.backend.service;


import com.example.backend.dto.ObszaryZSerwisantami;
import com.example.backend.entity.Dzialanie;
import com.example.backend.entity.Serwisant;
import com.example.backend.exception.NotFoundException;
import com.example.backend.repository.DzialanieRepository;
import com.example.backend.repository.ObszarRepository;
import com.example.backend.repository.SerwisantRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrudService {
    private final DzialanieRepository dzialanieRepository;
    private final ObszarRepository obszarRepository;
    private final SerwisantRepository serwisantRepository;

    public List<ObszaryZSerwisantami> getAllObszaryZSerwisantami() {
        return obszarRepository.findObszaryZSerwisantami();
    }

    public List<Serwisant> getAllSerwisant() {
        return serwisantRepository.getAllSerwisants();
    }

    public void updateSerwisant(int id, String nazwisko, boolean aktywny, String email) throws NotFoundException {
        serwisantRepository.updateSerwisant(id, nazwisko, aktywny, email);
    }

    public void addDzialanie(String opisDzialania, int planowanyCzas, int identyfikatorSerwisanta,
                             int identyfikatorObszaru) throws NotFoundException {
        if (obszarRepository.findObszarById(identyfikatorObszaru).isEmpty()) {
            throw new NotFoundException("obszar not found");
        }
        if (serwisantRepository.getSerwisantById(identyfikatorSerwisanta).isEmpty()) {
            throw new NotFoundException("serwisant not found");
        }

        dzialanieRepository.insert(
                opisDzialania,
                planowanyCzas,
                identyfikatorSerwisanta,
                identyfikatorObszaru
        );
    }

    public List<Dzialanie> getDzialaniaSerwisanata(int idSerwisanta) throws NotFoundException{
        if (serwisantRepository.getSerwisantById(idSerwisanta).isEmpty()) {
            throw new NotFoundException("serwisant not found");
        }
        return dzialanieRepository.dzialaniaDlaSerwisanta(idSerwisanta);
    }

    public Optional<Serwisant> getSerwisantById(int idSerwisanta) {
        return serwisantRepository.getSerwisantById(idSerwisanta);
    }
}
