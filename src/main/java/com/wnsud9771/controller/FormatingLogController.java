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
@RequestMapping("/logs") // '/logs' 경로로 들어오는 요청을 처리
@RequiredArgsConstructor
@Slf4j
public class FormatingLogController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/formating") // /logs/formating 요청 처리
    public ResponseEntity<String> processFormattedLog(@RequestBody LogDTO logDTO) {
        try {
        	// 받은 로그의 제목
            log.info("####################받아온 Title: {}", logDTO.getTitle());
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@받아온 로그: {}", logDTO.getContents());
            kafkaProducerService.sendLogMessage(logDTO);
            return ResponseEntity.ok("Log processed successfully");
        } catch (Exception e) {
            log.error("Error processing log: ", e);
            return ResponseEntity.internalServerError().body("Error processing log");
        }
    }
}
