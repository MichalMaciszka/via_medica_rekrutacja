package com.example.backend.repository;

import com.example.backend.dto.ObszaryZSerwisantami;
import com.example.backend.entity.Obszar;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ObszarRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<ObszaryZSerwisantami> findObszaryZSerwisantami() {
        List<ObszaryZSerwisantami> result = new ArrayList<>();
        String query = "SELECT * FROM vw_obszaryzserwisantami;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
        while (rowSet.next()) {
            ObszaryZSerwisantami item = new ObszaryZSerwisantami(
                    rowSet.getInt("ObszarId"),
                    rowSet.getString("ObszarNazwa"),
                    rowSet.getInt("SerwisantId"),
                    rowSet.getString("SerwisantNazwisko")
            );
            result.add(item);
        }
        return result;
    }

    public Optional<Obszar> findObszarById(int id) {
        String query = "SELECT * FROM obszar WHERE Identyfikator = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, id);
        if (rowSet.next()) {
            return Optional.of(new Obszar(
                    rowSet.getInt("identyfikator"),
                    rowSet.getString("nazwa"),
                    rowSet.getBoolean("aktywny"),
                    rowSet.getInt("identyfikatorSerwisanta")
            ));
        }
        return Optional.empty();
    }
}
