package com.example.user;

import com.example.user.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    // @Value("${fanout.exchange}")
    // private String fanoutExchange;

    // private final RabbitTemplate rabbitTemplate;

    // @Autowired
    // public QueueProducer(RabbitTemplate rabbitTemplate) {
    //     this.rabbitTemplate = rabbitTemplate;
    // }
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void produce(Student student) throws JsonProcessingException {
        rabbitTemplate.setExchange(RabbitConfiguration.fanoutExchange);
        rabbitTemplate.convertAndSend(student);
    }
    
}
