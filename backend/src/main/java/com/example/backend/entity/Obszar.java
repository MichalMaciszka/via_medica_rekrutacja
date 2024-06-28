package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Obszar {
    private int identyfikator;
    private String nazwa;
    private boolean aktywny;
    private int identyfikatorSerwisanta;
}
