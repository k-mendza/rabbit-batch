package copy.base.fetcher.config;

import copy.base.fetcher.domain.ClientMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    public static final String MESSAGE_EXCHANGE = "clients-exchange";
    public static final String MESSAGE_QUEUE = "clients-queue";
    public static final String MESSAGE_ROUTING_KEY = "clients.msg";

    private final ConnectionFactory connectionFactory;

    public RabbitConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    Queue queue() {
        return new Queue(MESSAGE_QUEUE, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MESSAGE_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MESSAGE_ROUTING_KEY);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(MESSAGE_EXCHANGE);
        rabbitTemplate.setRoutingKey(MESSAGE_ROUTING_KEY);
        return rabbitTemplate;
    }

    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(MESSAGE_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ClientMessageListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
