package com.example.managementuser.services;

import com.example.managementuser.entities.EmailMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private static final String TOPIC = "email_topic";
    @Autowired
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public void sendMessage(EmailMessage message){
        this.kafkaTemplate.send(TOPIC,message);
    }

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(TOPIC,3,(short) 1);
    }



}
