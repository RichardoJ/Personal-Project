package com.example.user;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.user.model.Student;

import com.example.user.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// @Component
// public class QueueConsumer {
    
//     private final RabbitTemplate rabbitTemplate;

//     // @Value("${queue.name}")
//     // private String queueName;

//     private String queueName = RabbitConfiguration.queueName;

//     @Autowired
//     public QueueConsumer(RabbitTemplate rabbitTemplate) {
//         this.rabbitTemplate = rabbitTemplate;
//     }

//     private String receiveMessage() {
//         String message = (String) rabbitTemplate.receiveAndConvert(queueName);
//         return message;
//     }

//     @RabbitListener(queues = "q.myQueue")
//     public Student processMessage() throws JsonProcessingException {
//         String message = receiveMessage();
//         return new ObjectMapper().readValue(message, Student.class);
//     }

//     public String getQueueName(){
//         return queueName;
//     }
// }

// @Component
// public class QueueConsumer{
    
//     @RabbitListener(queues = RabbitConfiguration.queueName)
//     public void consumeMessageFromQueue(Student student) {
//         System.out.println("Message recieved from queue : " + student);
//     }
// }

