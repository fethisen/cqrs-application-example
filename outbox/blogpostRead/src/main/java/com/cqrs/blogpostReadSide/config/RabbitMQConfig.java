package com.cqrs.blogpostReadSide.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.publisher.name}")
    private String queueNamePublisher;

    @Value("${rabbitmq.queue.listener.name}")
    private String queueNameListener;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.publisher.key}")
    private String routingKeyPublisher;

    @Value("${rabbitmq.routing.listener.key}")
    private String routingKeyListener;

    // spring bean for rabbitmq queue
    @Bean
    public Queue queuePublisher() {
        return new Queue(queueNamePublisher);
    }

    @Bean
    public Queue queueListener() {
        return new Queue(queueNameListener);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding bindingPublisher() {
        return BindingBuilder
                .bind(queuePublisher())
                .to(exchange())
                .with(routingKeyPublisher);
    }

    @Bean
    public Binding bindingListener() {
        return BindingBuilder
                .bind(queueListener())
                .to(exchange())
                .with(routingKeyListener);
    }
}
