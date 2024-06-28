package com.example.backend.repository;

import com.example.backend.entity.Dzialanie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class DzialanieRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private DzialanieRepository dzialanieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsert() {
        String opisDzialania = "Opis";
        int planowanyCzas = 120;
        int identyfikatorSerwisanta = 1;
        int identyfikatorObszaru = 2;

        when(jdbcTemplate.update(anyString(), anyInt(), anyInt(), anyInt(), anyString(), anyInt()))
                .thenReturn(1);

        dzialanieRepository.insert(opisDzialania, planowanyCzas, identyfikatorSerwisanta, identyfikatorObszaru);

        verify(jdbcTemplate, times(1))
                .update(
                        anyString(),
                        eq(identyfikatorSerwisanta),
                        eq(identyfikatorObszaru),
                        eq(opisDzialania),
                        eq(planowanyCzas)
                );
    }

    @Test
    public void testDzialaniaDlaSerwisanta() {
        int serwisantId = 1;

        SqlRowSet rowSet = mock(SqlRowSet.class);

        when(jdbcTemplate.queryForRowSet(anyString(), anyInt())).thenReturn(rowSet);
        when(rowSet.next()).thenReturn(true, false);
        when(rowSet.getInt("DzialanieId")).thenReturn(1);
        when(rowSet.getInt("SerwisantId")).thenReturn(serwisantId);
        when(rowSet.getInt("ObszarId")).thenReturn(2);
        when(rowSet.getString("OpisDzialania")).thenReturn("Opis");
        when(rowSet.getInt("PlanowanyCzas")).thenReturn(120);

        List<Dzialanie> result = dzialanieRepository.dzialaniaDlaSerwisanta(serwisantId);

        Dzialanie expected = new Dzialanie(1, 1, 2, "Opis", 120);
        assertThat(result).hasSize(1);
        Dzialanie dzialanie = result.getFirst();
        assertThat(dzialanie).isEqualTo(expected);

        verify(jdbcTemplate, times(1)).queryForRowSet(anyString(), eq(serwisantId));
    }
}
