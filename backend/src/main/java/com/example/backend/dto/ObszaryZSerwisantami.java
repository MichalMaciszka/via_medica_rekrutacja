package com.example.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObszaryZSerwisantami {
    private int obszarId;
    private String obszarName;
    private int serwisantId;
    private String serwisantName;
}
