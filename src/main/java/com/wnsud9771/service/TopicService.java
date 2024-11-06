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
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public void createTopicIfNotExists(String topicName) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsResult listTopics = adminClient.listTopics();
            Set<String> existingTopics = listTopics.names().get();

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

