package copy.base.fetcher.config;

import copy.base.fetcher.domain.Client;
import copy.base.fetcher.domain.ClientRowMapper;
import copy.base.fetcher.domain.ClientUpperCaseProcessor;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.batch.item.amqp.builder.AmqpItemReaderBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class BatchConfiguration {
    public static final int CHUNK_SIZE = 2048;
    public static final int CORE_POOL_SIZE = 4;
    public static final int MAX_CORE_POOL_SIZE = 16;
    public static final String CLIENT_QUEUE = "clients-queue";

    private final ConnectionFactory rabbitConnectionFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Queue clientQueue() {
        return new Queue(CLIENT_QUEUE, true);
    }

    @Bean
    public RabbitTemplate getMyQueueTemplate() {
        return new RabbitTemplate(this.rabbitConnectionFactory);
    }

    @Bean
    public JdbcCursorItemReader<Client> cursorItemReader() {
        return new JdbcCursorItemReaderBuilder<Client>()
                .dataSource(this.dataSource)
                .name("clientReader")
                .sql("SELECT * FROM CLIENT")
                .rowMapper(new ClientRowMapper())
                .build();
    }

    @Bean
    public AmqpItemReader<Client> clientAmqpItemReader() {
        return new AmqpItemReaderBuilder<Client>().build();
    }

    @Bean
    public ClientUpperCaseProcessor upperCaseProcessor() {
        return new ClientUpperCaseProcessor();
    }

    @Bean
    public Job importClientJob(Step step1) {
        return jobBuilderFactory.get("importClientJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_CORE_POOL_SIZE);
        taskExecutor.afterPropertiesSet();

        return stepBuilderFactory.get("step1")
                .<Client, Client>chunk(CHUNK_SIZE)
                .reader(cursorItemReader())
                .processor(upperCaseProcessor())
                .writer()
                .taskExecutor(taskExecutor)
                .build();
    }
}
