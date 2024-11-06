package com.wnsud9771.service;

import com.wnsud9771.dto.LogDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
	@KafkaListener(topicPattern = ".*", groupId = "consumer_group02")  // 모든 토픽 리스닝
    public void consume(String message) {
        try {
            log.info("Consumed Message: {}", message);
            // 여기서는 단순히 메시지를 로깅만 합니다.
        } catch (Exception e) {
            log.error("Error processing message: ", e);
        }
    }
//    private final RestTemplate restTemplate;
//    private static final String RECEIVE_LOG_URL = "http://localhost:8084/logs/receive";
//
//    @KafkaListener(topicPattern = ".*", groupId = "consumer_group02")
//    public void consume(String message) {
//        try {
//            log.info("Consumed Message: {}", message);
//            LogDTO dto = new LogDTO();
//            dto.setContents(message);  // setLog_data를 setContents로 변경
//            sendLogData(dto);
//        } catch (Exception e) {
//            log.error("Error processing message: ", e);
//        }
//    }
//
//    public LogDTO sendLogData(LogDTO logDTO) {
//        try {
//            log.info("Sending log data: {}", logDTO.getContents());  // getLog_data를 getContents로 변경
//            return restTemplate.postForObject(RECEIVE_LOG_URL, logDTO, LogDTO.class);
//        } catch (Exception e) {
//            log.error("Error sending log data: ", e);
//            throw new RuntimeException("Failed to send log data", e);
//        }
//    }
}