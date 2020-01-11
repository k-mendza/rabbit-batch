package copy.base.fetcher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class RabbitConfiguration {
    public static final String CLIENT_MESSAGE_EXCHANGE = "clients-exchange";
    public static final String CLIENT_MESSAGE_QUEUE = "clients-queue";
    public static final String CLIENT_MESSAGE_ROUTING_KEY = "clients.msg";
    public static final String PRODUCT_MESSAGE_EXCHANGE = "products-exchange";
    public static final String PRODUCT_MESSAGE_QUEUE = "products-queue";
    public static final String PRODUCT_MESSAGE_ROUTING_KEY = "products.msg";

    private final ConnectionFactory connectionFactory;

    public RabbitConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public Queue clientQueue() {
        return new Queue(CLIENT_MESSAGE_QUEUE, true);
    }

    @Bean Queue productQueue() {
        return new Queue(PRODUCT_MESSAGE_QUEUE, true);
    }

    @Bean
    TopicExchange clientExchange() {
        return new TopicExchange(CLIENT_MESSAGE_EXCHANGE);
    }

    @Bean
    TopicExchange productExchange() {
        return new TopicExchange(PRODUCT_MESSAGE_EXCHANGE);
    }

    @Bean
    Binding clientBinding(@Qualifier("clientQueue") Queue queue, @Qualifier("clientExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CLIENT_MESSAGE_ROUTING_KEY);
    }

    Binding productBinding(@Qualifier("productQueue") Queue queue, @Qualifier("productExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(PRODUCT_MESSAGE_ROUTING_KEY);
    }

    @Bean
    RabbitTemplate clientRabbitTemplate(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(CLIENT_MESSAGE_EXCHANGE);
        rabbitTemplate.setRoutingKey(CLIENT_MESSAGE_ROUTING_KEY);
        rabbitTemplate.setTaskExecutor(taskExecutor);
        return rabbitTemplate;
    }

    @Bean
    RabbitTemplate productRabbitTemplate(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(PRODUCT_MESSAGE_EXCHANGE);
        rabbitTemplate.setRoutingKey(PRODUCT_MESSAGE_ROUTING_KEY);
        rabbitTemplate.setTaskExecutor(taskExecutor);
        return rabbitTemplate;
    }
}
