package com.wnsud9771.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnsud9771.dto.LogDTO;
import com.wnsud9771.service.KafkaConsumerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // 이 부분 추가

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnsud9771.dto.LogDTO;
import com.wnsud9771.service.KafkaConsumerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
@Slf4j
public class ConsumLogSend {
//	private final KafkaConsumerService kafkaConsumerService;
//	
//	@PostMapping("/send")
//    public ResponseEntity<LogDTO> sendUserToSecondService(@RequestBody LogDTO logDto) {
//        return ResponseEntity.ok(kafkaConsumerService.sendLogData(logDto));
//    }
	private final KafkaConsumerService kafkaConsumerService;
    
    @PostMapping("/send")
    public ResponseEntity<String> sendUserToSecondService(@RequestBody LogDTO logDto) {
        // 메시지 수신만 로그로 남기기
        log.info("Received message: {}", logDto);
        return ResponseEntity.ok("Message received successfully");
    }
}
