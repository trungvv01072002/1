package com.example.managementuser.controllers;

import com.example.managementuser.entities.EmailMessage;
import com.example.managementuser.services.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final ProducerService producer;

    @PostMapping("/send")
    ResponseEntity<?> sendMail(@RequestBody EmailMessage emailMessage) {
       try {
              producer.sendMessage(emailMessage);
              return ResponseEntity.ok("Email sent successfully");
         } catch (Exception e) {
              return ResponseEntity.badRequest().body("Error sending email");
       }
    }
}
