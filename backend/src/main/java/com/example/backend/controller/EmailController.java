package com.example.backend.controller;


import com.example.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/api/sendEmails")
    public ResponseEntity<Void> sendEmails() {
        try {
            emailService.sendEmailToAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
//            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }
}
