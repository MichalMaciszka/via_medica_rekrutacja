package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Dzialanie {
    private int identyfikator;
    private int identyfikatorSerwisanta;
    private int identyfikatorObszaru;
    private String opisDzialania;
    private int planowanyCzas;
}
