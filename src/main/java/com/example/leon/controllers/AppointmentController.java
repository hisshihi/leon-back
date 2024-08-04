package com.example.leon.controllers;

import com.example.leon.domain.dto.AppointmentDto;
import com.example.leon.domain.entities.Appointment;
import com.example.leon.domain.entities.Masters;
import com.example.leon.mappers.impl.AppointmentMapper;
import com.example.leon.services.AppointmentService;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final MastersService mastersService;
    private final AppointmentMapper appointmentMapper;
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
    private ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment, Principal principal) {
        if (!appointmentService.existsByDateAndTime(appointment.getDate(), appointment.getTime())) {

            System.out.println(appointment.getMaster().getId());

            if (appointment.getMaster().getId() == null) {
                Masters masters = (Masters) mastersService.masterDetailService().loadUserByUsername(principal.getName());
                appointment.setMaster(masters);
            }

            appointmentService.create(appointment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    private ResponseEntity<List<Appointment>> getListAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        return new ResponseEntity<>(new ArrayList<>(appointments), HttpStatus.OK);
    }

//    Получение записей мастера
    @GetMapping("/master")
    private ResponseEntity<List<Appointment>> getListAppointmentsForMaster(Principal principal) {
        List<Appointment> appointments = appointmentService.findAllByMaster(principal);
        return ResponseEntity.ok(new ArrayList<>(appointments));
    }

    @PutMapping(value = "/{id}")
    private ResponseEntity<Void> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment updatedAppointment
    ) {
        appointmentService.updatedAppointment(id, updatedAppointment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/client-name")
    private ResponseEntity<List<Appointment>> findByClientName(@RequestParam String name) {
        List<Appointment> appointments = appointmentService.findByClientName(name);
        return ResponseEntity.ok(new ArrayList<>(appointments));
    }

    @GetMapping("/total")
    private ResponseEntity<Integer> getTotalEarnings() {
        int totalEarnings = appointmentService.getTotalEarnings();
        return ResponseEntity.ok(totalEarnings);
    }

    @GetMapping("/total/master")
    private ResponseEntity<Integer> getEarningsByMaster(Principal principal) {
        Masters master = (Masters) mastersService.masterDetailService().loadUserByUsername(principal.getName());
        int earnings = appointmentService.getEarningsByMaster(master.getId());
        return ResponseEntity.ok(earnings);
    }

//    Поиск записи по числу
    @GetMapping("/date")
    private ResponseEntity<List<AppointmentDto>> getAppointmentForDate(@RequestParam LocalDate date) {
        List<Appointment> appointments = appointmentService.findByDate(date);
        List<AppointmentDto> appointmentDtos = appointments.stream().map(appointmentMapper::mapTo).toList();
        return ResponseEntity.ok(appointmentDtos);
    }

}
