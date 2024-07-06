package com.example.leon.controllers;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.services.AppointmentService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final String ACCOUNT_SID = "ACbfbbf74795104a6c2c63b3d268b46d3a";
    private final String AUTH_TOKEN = "db111304a236c10f8e1a50bfe16cad42";
    private final String SERVICE_SID = "VA81459b11d4d58ee79f41b89e8367f42a";

    /*
    * Разработал подтверждение записи через телефон
    * Сначала отправляется запрос на request-code с телефоном в url
    * Затем запрос на appointment c кодом, телефон должен быть вида - +79088379382
    * */
//    @PostMapping("/request-code")
//    public ResponseEntity<Void> sendSMS(@RequestParam String smsNumber) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        Verification verification = Verification.creator(
//                SERVICE_SID,
//                "+" + smsNumber,
//                "sms"
//        ).create();
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping("/verify-code")
//    private ResponseEntity<Void> verifyCode(@RequestParam String smsNumber, @RequestParam String code) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        VerificationCheck verificationCheck = VerificationCheck.creator(SERVICE_SID)
//                .setTo("+" + smsNumber)
//                .setCode(code)
//                .create();
//
//        if (verificationCheck.getStatus().equals("approved")) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping
//    private ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment, @RequestParam String code) {
//        // Проверяем код перед созданием записи
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        VerificationCheck verificationCheck = VerificationCheck.creator(SERVICE_SID)
//                .setTo(appointment.getClientPhone())
//                .setCode(code)
//                .create();
//
//        if (!verificationCheck.getStatus().equals("approved")) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        // Создаем запись только если код подтвержден
//        if (!appointmentService.existsByDateAndTime(appointment.getDate(), appointment.getTime())) {
//            appointmentService.create(appointment);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }

    @PostMapping
    private ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment) {
        if (!appointmentService.existsByDateAndTime(appointment.getDate(), appointment.getTime())) {
            appointmentService.create(appointment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    private ResponseEntity<List<Appointment>> getListAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        return new ResponseEntity<>(new ArrayList<>(appointments), HttpStatus.OK);
    }

}
