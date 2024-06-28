package com.example.backend.service;

import com.example.backend.dto.ObszaryZSerwisantami;
import com.example.backend.entity.Dzialanie;
import com.example.backend.entity.Obszar;
import com.example.backend.entity.Serwisant;
import com.example.backend.exception.NotFoundException;
import com.example.backend.repository.DzialanieRepository;
import com.example.backend.repository.ObszarRepository;
import com.example.backend.repository.SerwisantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrudServiceTest {

    @Mock
    private DzialanieRepository dzialanieRepository;

    @Mock
    private ObszarRepository obszarRepository;

    @Mock
    private SerwisantRepository serwisantRepository;

    @InjectMocks
    private CrudService crudService;

    @Test
    void getAllObszaryZSerwisantami() {
        List<ObszaryZSerwisantami> obszaryList = List.of(new ObszaryZSerwisantami());
        when(obszarRepository.findObszaryZSerwisantami()).thenReturn(obszaryList);

        List<ObszaryZSerwisantami> result = crudService.getAllObszaryZSerwisantami();

        assertThat(result).isEqualTo(obszaryList);
        verify(obszarRepository, times(1)).findObszaryZSerwisantami();
    }

    @Test
    void getAllSerwisant() {
        List<Serwisant> serwisants = List.of(new Serwisant());
        when(serwisantRepository.getAllSerwisants()).thenReturn(serwisants);

        List<Serwisant> result = crudService.getAllSerwisant();

        assertThat(result).isEqualTo(serwisants);
        verify(serwisantRepository, times(1)).getAllSerwisants();
    }

    @Test
    void updateSerwisant() throws NotFoundException {
        doNothing().when(serwisantRepository).updateSerwisant(anyInt(), anyString(), anyBoolean(), anyString());

        crudService.updateSerwisant(1, "Kowalski", true, "kowalski@example.com");

        verify(serwisantRepository, times(1))
                .updateSerwisant(1, "Kowalski", true, "kowalski@example.com");
    }

    @Test
    void addDzialanie_Success() throws NotFoundException {
        when(obszarRepository.findObszarById(anyInt())).thenReturn(Optional.of(new Obszar()));
        when(serwisantRepository.getSerwisantById(anyInt())).thenReturn(Optional.of(new Serwisant()));
        doNothing().when(dzialanieRepository).insert(anyString(), anyInt(), anyInt(), anyInt());

        crudService.addDzialanie("Test", 120, 1, 1);

        verify(obszarRepository, times(1)).findObszarById(1);
        verify(serwisantRepository, times(1)).getSerwisantById(1);
        verify(dzialanieRepository, times(1)).insert("Test", 120, 1, 1);
    }

    @Test
    void addDzialanie_ObszarNotFound() {
        when(obszarRepository.findObszarById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> crudService.addDzialanie("Test", 120, 1, 1))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("obszar not found");

        verify(obszarRepository, times(1)).findObszarById(1);
        verify(serwisantRepository, never()).getSerwisantById(anyInt());
        verify(dzialanieRepository, never()).insert(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void addDzialanie_SerwisantNotFound() {
        when(obszarRepository.findObszarById(anyInt())).thenReturn(Optional.of(new Obszar()));
        when(serwisantRepository.getSerwisantById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> crudService.addDzialanie("Test", 120, 1, 1))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("serwisant not found");

        verify(obszarRepository, times(1)).findObszarById(1);
        verify(serwisantRepository, times(1)).getSerwisantById(1);
        verify(dzialanieRepository, never()).insert(anyString(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void getDzialaniaSerwisanata_Success() throws NotFoundException {
        List<Dzialanie> dzialaniaList = List.of(new Dzialanie());
        when(serwisantRepository.getSerwisantById(anyInt())).thenReturn(Optional.of(new Serwisant()));
        when(dzialanieRepository.dzialaniaDlaSerwisanta(anyInt())).thenReturn(dzialaniaList);

        List<Dzialanie> result = crudService.getDzialaniaSerwisanata(1);

        assertThat(result).isEqualTo(dzialaniaList);
        verify(serwisantRepository, times(1)).getSerwisantById(1);
        verify(dzialanieRepository, times(1)).dzialaniaDlaSerwisanta(1);
    }

    @Test
    void getDzialaniaSerwisanata_SerwisantNotFound() {
        when(serwisantRepository.getSerwisantById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> crudService.getDzialaniaSerwisanata(1))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("serwisant not found");

        verify(serwisantRepository, times(1)).getSerwisantById(1);
        verify(dzialanieRepository, never()).dzialaniaDlaSerwisanta(anyInt());
    }

    @Test
    void getSerwisantById() {
        Serwisant serwisant = new Serwisant();
        when(serwisantRepository.getSerwisantById(anyInt())).thenReturn(Optional.of(serwisant));

        Optional<Serwisant> result = crudService.getSerwisantById(1);

        assertThat(result).contains(serwisant);
        verify(serwisantRepository, times(1)).getSerwisantById(1);
    }
}
