package com.example.backend.repository;


import com.example.backend.entity.Dzialanie;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DzialanieRepository {
    private final JdbcTemplate jdbcTemplate;

    public void insert(String opisDzialania, int planowanyCzas, int identyfikatorSerwisanta,
                       int identyfikatorObszaru) {
        String query = """
            INSERT INTO [SIT].[Działanie] (
                                   IdentyfikatorSerwisanta,
                                   IdentyfikatorObszaru,
                                   OpisDzialania,
                                   PlanowanyCzas
                           )
            VALUES (?, ?, ?, ?);
        """;
        jdbcTemplate.update(query, identyfikatorSerwisanta, identyfikatorObszaru, opisDzialania,
                planowanyCzas);
    }

    public List<Dzialanie> dzialaniaDlaSerwisanta(int serwisantId) {
        List<Dzialanie> result = new ArrayList<>();
        String query = "SELECT * FROM vw_DzialaniaDlaSerwisanta WHERE SerwisantId = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, serwisantId);
        while (rowSet.next()) {
            Dzialanie item = new Dzialanie(
                    rowSet.getInt("DzialanieId"),
                    rowSet.getInt("SerwisantId"),
                    rowSet.getInt("ObszarId"),
                    rowSet.getString("OpisDzialania"),
                    rowSet.getInt("PlanowanyCzas")
            );
            result.add(item);
        }
        return result;
    }
}
