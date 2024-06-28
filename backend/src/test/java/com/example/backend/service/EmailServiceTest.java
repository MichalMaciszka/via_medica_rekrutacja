package com.example.backend.service;

import com.example.backend.entity.Dzialanie;
import com.example.backend.entity.Serwisant;
import com.example.backend.repository.DzialanieRepository;
import com.example.backend.repository.SerwisantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private DzialanieRepository dzialanieRepository;

    @Mock
    private SerwisantRepository serwisantRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private List<Serwisant> serwisantList;
    private List<Dzialanie> dzialanieList;

    @BeforeEach
    void setUp() {
        Serwisant serwisant = new Serwisant();
        serwisant.setIdentyfikator(1);
        serwisant.setEmail("test@example.com");
        serwisantList = Collections.singletonList(serwisant);

        Dzialanie dzialanie = new Dzialanie();
        dzialanie.setIdentyfikator(1);
        dzialanie.setIdentyfikatorObszaru(2);
        dzialanie.setOpisDzialania("Test");
        dzialanie.setPlanowanyCzas(60);
        dzialanieList = Collections.singletonList(dzialanie);
    }

    @Test
    void sendEmailToAll() {
        when(serwisantRepository.getAllSerwisants()).thenReturn(serwisantList);
        when(dzialanieRepository.dzialaniaDlaSerwisanta(1)).thenReturn(dzialanieList);

        emailService.sendEmailToAll();

        verify(serwisantRepository).getAllSerwisants();
        verify(dzialanieRepository).dzialaniaDlaSerwisanta(1);
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
