package copy.base.pusher.config;

import copy.base.pusher.domain.Client;
import copy.base.pusher.domain.ClientLowerCaseProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.batch.item.amqp.builder.AmqpItemReaderBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
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

    @Value("${pusher.spring-batch-chunk-size}")
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
    public JdbcBatchItemWriter<Client> cursorItemWriter() {
        return new JdbcBatchItemWriterBuilder<Client>()
                .dataSource(this.dataSource)
                .sql("INSERT INTO CLIENT VALUES (?,?,?,?,?)")
                .build();
    }

    @Bean
    public AmqpItemReader<Client> clientAmqpItemReader() {
        return new AmqpItemReaderBuilder<Client>()
                .amqpTemplate(rabbitTemplate)
                .build();
    }

    @Bean
    public ClientLowerCaseProcessor lowerCaseProcessor() {
        return new ClientLowerCaseProcessor();
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
                .reader(clientAmqpItemReader())
                .processor(lowerCaseProcessor())
                .writer(cursorItemWriter())
                .taskExecutor(taskExecutor)
                .build();
    }
}
