package com.example.user;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    // @Value("${fanout.exchange}")
    // private String fanoutExchange;

    // @Value("${queue.name}")
    // private String queueName;

    public final static String fanoutExchange = "x.fanout";
    public final static String queueName = "q.myQueue";

    @Bean
    Queue queue() {
        System.out.println(queueName);
        return new Queue(queueName, true);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }


    // public static final String QUEUE = "myQueue";
    // public static final String EXCHANGE = "exchange_topic";
    // public static final String ROUTINGKEY = "myRoutingKey";

    // @Bean
    // public Queue queue(){
    //     return new Queue(QUEUE);
    // }

    // @Bean
    // public TopicExchange exchange(){
    //     return new TopicExchange(EXCHANGE);
    // }

    // @Bean
    // public Binding binding(Queue queue, TopicExchange exchange){
    //     return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY);
    // }

    // @Bean
    // public MessageConverter converter(){
    //     return new Jackson2JsonMessageConverter();
    // }

    // @Bean
    // public AmqpTemplate template(ConnectionFactory connectionFactory){
    //     final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    //     rabbitTemplate.setMessageConverter(converter());
    //     return rabbitTemplate;
    // }
}