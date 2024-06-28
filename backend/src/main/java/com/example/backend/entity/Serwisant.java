package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Serwisant {
    private int identyfikator;
    private String nazwisko;
    private boolean aktywny;
    private String email;
}
