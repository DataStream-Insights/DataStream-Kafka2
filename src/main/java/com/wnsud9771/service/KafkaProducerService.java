package com.wnsud9771.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wnsud9771.dto.LogDTO;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final TopicService topicService;

    public void sendLogMessage(LogDTO logDTO) {
        try {
            // 토픽명 정제
            String topicName = generateTopicName(logDTO.getTitle());
            
            // 토픽 존재 확인 및 생성
            topicService.createTopicIfNotExists(topicName);
            
            // JSON 변환
            String jsonMessage = objectMapper.writeValueAsString(logDTO);
            
            // Kafka로 전송
            CompletableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topicName, jsonMessage);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Message sent successfully to topic: {}", topicName);
                } else {
                    log.error("Failed to send message to topic: {}", ex.getMessage());
                }
            });
            
        } catch (Exception e) {
            log.error("Error sending message: ", e);
            throw new RuntimeException("Failed to send message", e);
        }
    }

    private String generateTopicName(String title) {
        return title.replaceAll("[^a-zA-Z0-9-.]", "_")
                   .toLowerCase()
                   .substring(0, Math.min(title.length(), 249));
    }

    @PostConstruct
    public void init() {
        try {
            LogDTO testLog1 = new LogDTO();
            testLog1.setTitle("사용자 A의 전자제품 조회 로그");
            testLog1.setContents("{\"timestamp\":\"2024-10-31T14:23:45+09:00\"," +
                                "\"visitor_id\":\"2cff4a12e87f499b\"," +
                                "\"url\":\"https://example.com/products/category/electronics\"," +
                                "\"event_action\":\"View\"," +
                                "\"user_id\":\"user_123456\"}");

            sendLogMessage(testLog1);
            log.info("Test message sent successfully!");
        } catch (Exception e) {
            log.error("Error sending test message: ", e);
        }
    }
}