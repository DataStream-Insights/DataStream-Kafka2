package com.wnsud9771.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Set;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicService {
	// application.properties에서 Kafka 서버 주소 넣음
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * 토픽이 존재하지 않는 경우 새로운 토픽을 생성하는 메소드
     * @param topicName 생성할 토픽 이름
     */
    public void createTopicIfNotExists(String topicName) {
    	// Kafka Admin Client 설정
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Admin Client 사용 (try-with-resources로 자동 리소스 해제)
        try (AdminClient adminClient = AdminClient.create(props)) {
        	// 존재하는 모든 토픽 조회
            ListTopicsResult listTopics = adminClient.listTopics();
            Set<String> existingTopics = listTopics.names().get();

            // 토픽이 존재하지 않는 경우에만 새로 생성
            if (!existingTopics.contains(topicName)) {
                NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
                adminClient.createTopics(Collections.singleton(newTopic)).all().get();
                log.info("Created new topic: {}", topicName);
            }
        } catch (Exception e) {
            log.error("Error creating/checking topic {}: ", topicName, e);
            throw new RuntimeException("Topic creation failed", e);
        }
    }
}

