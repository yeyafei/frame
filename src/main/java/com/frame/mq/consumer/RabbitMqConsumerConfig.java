package com.frame.mq.consumer;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
* RabbitMqConsumerConfig
*
* @author yyf
* @date 2018年11月8日
 */
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.consumer")
public class RabbitMqConsumerConfig {

    @Value("${rabbitmq.consumer.addresses}")
    private String addresses;
    @Value("${rabbitmq.consumer.userName}")
    private String username;
    @Value("${rabbitmq.consumer.password}")
    private String password;
    @Value("${rabbitmq.consumer.publisher-confirms}")
    private Boolean publisherConfirms;
    @Value("${rabbitmq.consumer.virtual-host}")
    private String virtualHost;

    // 构建mq实例工厂,bean指定name为了防止和mq producer的冲突,下同
    @Bean(name="consumerConnectionFactory")
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean(name="consumerRabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean(name="consumerRabbitTemplate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
    @Bean(name="consumerJackson2JsonMessageConverter")
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
}
