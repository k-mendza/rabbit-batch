package copy.base.fetcher.config;

import copy.base.fetcher.domain.Client;
import copy.base.fetcher.domain.ClientItemWriteListener;
import copy.base.fetcher.domain.ClientRowMapper;
import copy.base.fetcher.domain.ClientUpperCaseProcessor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.amqp.AmqpItemWriter;
import org.springframework.batch.item.amqp.builder.AmqpItemWriterBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {
    public static final int CHUNK_SIZE = 2048;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;
    private RabbitTemplate rabbitTemplate;
    private ClientItemWriteListener clientItemWriteListener;

    @Autowired
    public BatchConfiguration(ConnectionFactory rabbitConnectionFactory, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource, RabbitTemplate rabbitTemplate, ClientItemWriteListener clientItemWriteListener) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.rabbitTemplate = rabbitTemplate;
        this.clientItemWriteListener = clientItemWriteListener;
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
    public AmqpItemWriter<Client> clientAmqpItemWriter() {
        return new AmqpItemWriterBuilder<Client>()
                .amqpTemplate(rabbitTemplate)
                .build();
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
    public Step step1(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        return stepBuilderFactory.get("step1")
                .<Client, Client>chunk(CHUNK_SIZE)
                .reader(cursorItemReader())
                .processor(upperCaseProcessor())
                .writer(clientAmqpItemWriter())
                .listener(clientItemWriteListener)
                .taskExecutor(taskExecutor)
                .build();
    }
}
