package com.example.backend.repository;

import com.example.backend.entity.Serwisant;
import com.example.backend.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SerwisantRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(String nazwisko, String email) {
        String query = "INSERT INTO [SIT].[Serwisant] (nazwisko, email) VALUES (?, ?);";
        jdbcTemplate.update(query, nazwisko, email);
    }

    public Optional<Serwisant> getSerwisantById(int id) {
        String query = "SELECT * FROM [SIT].[Serwisant] WHERE Identyfikator = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(query, id);
        if(result.next()) {
            return Optional.of(
                    new Serwisant(
                            result.getInt("identyfikator"),
                            result.getString("nazwisko"),
                            result.getBoolean("aktywny"),
                            result.getString("email")
                    )
            );
        }
        return Optional.empty();
    }

    public List<Serwisant> getAllSerwisants() {
        String query = "SELECT * FROM [SIT].[Serwisant]";
        SqlRowSet result = jdbcTemplate.queryForRowSet(query);
        List<Serwisant> serwisants = new ArrayList<>();
        while(result.next()) {
            Serwisant serwisant = new Serwisant(
                    result.getInt("identyfikator"),
                    result.getString("nazwisko"),
                    result.getBoolean("aktywny"),
                    result.getString("email")
            );
            serwisants.add(serwisant);
        }
        return serwisants;
    }

    public void updateSerwisant(int id, String nazwisko, boolean aktywny, String email) throws NotFoundException {
        Optional<Serwisant> optSerwisant = getSerwisantById(id);
        if (optSerwisant.isEmpty()) {
            throw new NotFoundException("not found");
        }

        String query = """
            UPDATE [SIT].[Serwisant] SET aktywny = ?, nazwisko = ?, email = ? WHERE identyfikator = ?;
        """;
        jdbcTemplate.update(query, aktywny ? 1 : 0, nazwisko, email, id);
    }
}
