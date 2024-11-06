package com.wnsud9771.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka //kafka 관련 기능 활성화
@Configuration 
public class KafkaConsumerConfig {

	
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        //kafka 서버 주소 설정
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //컨슈머 그룹 ID 설정
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_group02");
        //가장 처음부터 메시지 읽음
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //메시지 키와 값의 역직렬화 방식 설정
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //한 번에 폴링하는 최대 시간 설정 (10초)
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
        return new DefaultKafkaConsumerFactory<>(props);
    }
    // Kafka 리스너 컨테이너 팩토리 설정
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
