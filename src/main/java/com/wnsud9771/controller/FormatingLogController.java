package com.wnsud9771.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnsud9771.dto.LogDTO;
import com.wnsud9771.service.KafkaProducerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
@Slf4j
public class FormatingLogController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/formating")
    public ResponseEntity<String> processFormattedLog(@RequestBody LogDTO logDTO) {
        try {
            log.info("Received formatted log - Title: {}", logDTO.getTitle());
            kafkaProducerService.sendLogMessage(logDTO);
            return ResponseEntity.ok("Log processed successfully");
        } catch (Exception e) {
            log.error("Error processing log: ", e);
            return ResponseEntity.internalServerError().body("Error processing log");
        }
    }
}