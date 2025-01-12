package pig.chat.springboot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutExchangeAndQueueConfig {

    // 创建队列queue.fanout
    @Bean
    public Queue fanoutQueue(){
        return QueueBuilder.durable("queue.pigchat").build();
    }

    @Bean
    public Queue directQueue(){
        return QueueBuilder.durable("queue.direct").build();
    }

    // 创建交换机exchange.fanout
    @Bean
    public FanoutExchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange("exchange.pigchat").build();
    }

    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange("exchange.direct").build();
    }

    // 将队列绑定到交换机
    @Bean
    public Binding ExchangeBindQueue(){
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    // 将队列绑定到交换机并加上bindingKey
    @Bean
    public Binding ExchangeBindQueueAndKey(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("red");
    }
}
