package copy.base.fetcher.config;

import copy.base.fetcher.domain.Client;
import copy.base.fetcher.domain.ClientRowMapper;
import copy.base.fetcher.domain.ClientUpperCaseProcessor;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Value("${fetcher.spring-batch-chunk-size}")
    private int chunkSize;

    private DataSource dataSource;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource, RabbitTemplate rabbitTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.rabbitTemplate = rabbitTemplate;
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
                .<Client, Client>chunk(chunkSize)
                .reader(cursorItemReader())
                .processor(upperCaseProcessor())
                .writer(clientAmqpItemWriter())
                .taskExecutor(taskExecutor)
                .build();
    }
}
