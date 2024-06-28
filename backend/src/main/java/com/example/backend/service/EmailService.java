package com.example.backend.service;


import com.example.backend.entity.Dzialanie;
import com.example.backend.entity.Serwisant;
import com.example.backend.repository.DzialanieRepository;
import com.example.backend.repository.SerwisantRepository;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class EmailService {
    private final DzialanieRepository dzialanieRepository;
    private final SerwisantRepository serwisantRepository;
    private final JavaMailSender mailSender;

    public void sendEmailToAll() {
        List<Serwisant> serwisantList = serwisantRepository.getAllSerwisants();
        serwisantList.forEach(s -> sendEmail(s.getEmail(),
                dzialanieRepository.dzialaniaDlaSerwisanta(s.getIdentyfikator())));
    }

    private void sendEmail(String email, List<Dzialanie> dzialania) {
        String message = buildMessage(dzialania);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Dzialanie dla serwisania");
        mailMessage.setText(message);
        mailMessage.setFrom("dzialanie@noreply.com");
        mailSender.send(mailMessage);
        log.info("Message sent to " + email);
        try {
            //serwer SMTP ogranicza ilość maili na sekundę
            Thread.sleep(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
//            e.printStackTrace();
            log.severe(e.getMessage());
        }
    }

    private String buildMessage(List<Dzialanie> dzialania) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dzialania dla serwisanta\n");
        dzialania.forEach(d -> stringBuilder.append(String.format("Dzialane: %d \t Obszar: %d \t " +
                "Opis: %s \t Czas: %d%n", d.getIdentyfikator(), d.getIdentyfikatorObszaru(),
                d.getOpisDzialania(), d.getPlanowanyCzas())));
        return stringBuilder.toString();
    }
}
