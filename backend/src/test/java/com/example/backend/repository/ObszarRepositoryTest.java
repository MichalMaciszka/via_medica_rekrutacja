package com.example.backend.repository;

import com.example.backend.dto.ObszaryZSerwisantami;
import com.example.backend.entity.Obszar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObszarRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ObszarRepository obszarRepository;

    @Test
    void testFindObszaryZSerwisantami() {
        SqlRowSet rowSetMock = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString())).thenReturn(rowSetMock);
        when(rowSetMock.next()).thenReturn(true, true, false); // two rows
        when(rowSetMock.getInt("ObszarId")).thenReturn(1, 2);
        when(rowSetMock.getString("ObszarNazwa")).thenReturn("Area1", "Area2");
        when(rowSetMock.getInt("SerwisantId")).thenReturn(10, 20);
        when(rowSetMock.getString("SerwisantNazwisko")).thenReturn("Smith", "Johnson");

        List<ObszaryZSerwisantami> result = obszarRepository.findObszaryZSerwisantami();

        ObszaryZSerwisantami expectedFirst = new ObszaryZSerwisantami(
                1,
                "Area1",
                10,
                "Smith"
        );
        ObszaryZSerwisantami expectedSecond = new ObszaryZSerwisantami(
                2,
                "Area2",
                20,
                "Johnson"
        );
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(expectedFirst);
        assertThat(result.get(1)).isEqualTo(expectedSecond);
    }

    @Test
    void testFindObszarById() {
        SqlRowSet rowSetMock = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString(), anyInt())).thenReturn(rowSetMock);
        when(rowSetMock.next()).thenReturn(true);
        when(rowSetMock.getInt("identyfikator")).thenReturn(1);
        when(rowSetMock.getString("nazwa")).thenReturn("Area1");
        when(rowSetMock.getBoolean("aktywny")).thenReturn(true);
        when(rowSetMock.getInt("identyfikatorSerwisanta")).thenReturn(10);

        Optional<Obszar> result = obszarRepository.findObszarById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getIdentyfikator()).isEqualTo(1);
        assertThat(result.get().getNazwa()).isEqualTo("Area1");
        assertThat(result.get().isAktywny()).isTrue();
        assertThat(result.get().getIdentyfikatorSerwisanta()).isEqualTo(10);
    }

    @Test
    void testFindObszarById_NotFound() {
        SqlRowSet rowSetMock = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString(), anyInt())).thenReturn(rowSetMock);
        when(rowSetMock.next()).thenReturn(false);

        Optional<Obszar> result = obszarRepository.findObszarById(1);

        assertThat(result).isNotPresent();
    }
}
