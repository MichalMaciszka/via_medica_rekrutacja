package com.example.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDzialanieRequest {
    private String identyfikatorSerwisanta;
    private String identyfikatorObszaru;
    private String opisDzialania;
    private String planowanyCzas;
}
