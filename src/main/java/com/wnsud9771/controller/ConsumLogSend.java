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
@RequestMapping("/logs") //"/logs"경로로 들어오는 요청 처리
@RequiredArgsConstructor 
@Slf4j
public class ConsumLogSend {
	
	//REST API로 받은 메시지를 다른 서비스로 전달하는 버전
//	private final KafkaConsumerService kafkaConsumerService;
//	
//	@PostMapping("/send")
//    public ResponseEntity<LogDTO> sendUserToSecondService(@RequestBody LogDTO logDto) {
//        return ResponseEntity.ok(kafkaConsumerService.sendLogData(logDto));
//    }
	//메시지 수신 확인용 간단한 구현
	private final KafkaConsumerService kafkaConsumerService;
    
	//logs/send 요청 처리
    @PostMapping("/send")
    public ResponseEntity<String> sendUserToSecondService(@RequestBody LogDTO logDto) {
        // 받은 메시지 로그 기록
        log.info("Received message: {}", logDto);
        return ResponseEntity.ok("Message received successfully");
    }
}
