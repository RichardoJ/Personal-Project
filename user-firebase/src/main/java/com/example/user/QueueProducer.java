package com.example.user;

import com.example.user.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void produce(Student student) throws JsonProcessingException {
        rabbitTemplate.setExchange(RabbitConfiguration.fanoutExchange);
        rabbitTemplate.convertAndSend(student);
    }
    
}
