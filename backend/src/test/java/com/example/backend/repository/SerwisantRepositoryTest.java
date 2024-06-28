package com.example.backend.repository;

import com.example.backend.entity.Serwisant;
import com.example.backend.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SerwisantRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SerwisantRepository serwisantRepository;

    @Test
    void save_shouldInvokeJdbcTemplateUpdate() {
        String nazwisko = "Kowalski";
        String email = "kowalski@example.com";

        serwisantRepository.save(nazwisko, email);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(nazwisko), eq(email));
    }

    @Test
    void getSerwisantById_shouldReturnSerwisant_whenExists() {
        int id = 1;
        SqlRowSet rowSet = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString(), eq(id))).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true);
        when(rowSet.getInt("identyfikator")).thenReturn(id);
        when(rowSet.getString("nazwisko")).thenReturn("Kowalski");
        when(rowSet.getBoolean("aktywny")).thenReturn(true);
        when(rowSet.getString("email")).thenReturn("kowalski@example.com");

        Optional<Serwisant> result = serwisantRepository.getSerwisantById(id);

        Serwisant expected = new Serwisant(
                id,
                "Kowalski",
                true,
                "kowalski@example.com"
        );
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    void getSerwisantById_shouldReturnEmpty_whenNotExists() {
        int id = 1;
        SqlRowSet rowSet = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString(), eq(id))).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(false);

        Optional<Serwisant> result = serwisantRepository.getSerwisantById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void getAllSerwisants_shouldReturnListOfSerwisants() {
        SqlRowSet rowSet = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString())).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, true, false);
        when(rowSet.getInt("identyfikator")).thenReturn(1, 2);
        when(rowSet.getString("nazwisko")).thenReturn("Kowalski", "Nowak");
        when(rowSet.getBoolean("aktywny")).thenReturn(true, false);
        when(rowSet.getString("email")).thenReturn("kowalski@example.com", "nowak@example.com");

        List<Serwisant> result = serwisantRepository.getAllSerwisants();

        Serwisant expectedFirst = new Serwisant(
                1,
                "Kowalski",
                true,
                "kowalski@example.com"
        );
        Serwisant expectedSecond = new Serwisant(
                2,
                "Nowak",
                false,
                "nowak@example.com"
        );
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(expectedFirst);
        assertThat(result.get(1)).isEqualTo(expectedSecond);
    }

    @Test
    void updateSerwisant_shouldUpdateSerwisant_whenExists() throws NotFoundException {
        String nazwisko = "Kowalski";
        boolean aktywny = true;
        String email = "kowalski@example.com";
        int id = 1;
        SqlRowSet rowSet = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString(), eq(id))).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true);
        when(rowSet.getInt("identyfikator")).thenReturn(id);
        when(rowSet.getString("nazwisko")).thenReturn(nazwisko);
        when(rowSet.getBoolean("aktywny")).thenReturn(aktywny);
        when(rowSet.getString("email")).thenReturn(email);
        when(jdbcTemplate.update(anyString(), eq(aktywny ? 1 : 0), eq(nazwisko), eq(email), eq(id))).thenReturn(1);

        serwisantRepository.updateSerwisant(id, nazwisko, aktywny, email);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(aktywny ? 1 : 0), eq(nazwisko), eq(email), eq(id));
    }

    @Test
    void updateSerwisant_shouldThrowNotFoundException_whenNotExists() {
        String nazwisko = "Kowalski";
        boolean aktywny = true;
        String email = "kowalski@example.com";
        int id = 1;
        SqlRowSet rowSet = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString(), eq(id))).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(false);

        assertThatThrownBy(() -> serwisantRepository.updateSerwisant(id, nazwisko, aktywny, email))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("not found");
    }
}
