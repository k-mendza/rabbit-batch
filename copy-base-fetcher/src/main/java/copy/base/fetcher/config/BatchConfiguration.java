package copy.base.fetcher.config;

import copy.base.fetcher.domain.client.Client;
import copy.base.fetcher.domain.client.ClientRowMapper;
import copy.base.fetcher.domain.client.ClientUpperCaseProcessor;
import copy.base.fetcher.domain.product.Product;
import copy.base.fetcher.domain.product.ProductRowMapper;
import copy.base.fetcher.domain.product.ProductUpperCaseProcessor;
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
    private RabbitTemplate clientRabbitTemplate;
    private RabbitTemplate productRabbitTemplate;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource, @Qualifier("clientRabbitTemplate") RabbitTemplate clientRabbitTemplate, @Qualifier("productRabbitTemplate") RabbitTemplate productRabbitTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.clientRabbitTemplate = clientRabbitTemplate;
        this.productRabbitTemplate = productRabbitTemplate;
    }

    @Bean
    public JdbcCursorItemReader<Client> clientCursorItemReader() {
        return new JdbcCursorItemReaderBuilder<Client>()
                .dataSource(this.dataSource)
                .name("clientReader")
                .sql("SELECT * FROM CLIENT")
                .rowMapper(new ClientRowMapper())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Product> productCursorItemReader() {
        return new JdbcCursorItemReaderBuilder<Product>()
                .dataSource(this.dataSource)
                .name("productReader")
                .sql("SELECT * FROM PRODUCT")
                .rowMapper(new ProductRowMapper())
                .build();
    }

    @Bean
    public AmqpItemWriter<Client> clientAmqpItemWriter() {
        return new AmqpItemWriterBuilder<Client>()
                .amqpTemplate(clientRabbitTemplate)
                .build();
    }

    @Bean
    public AmqpItemWriter<Product> productAmqpItemWriter() {
        return new AmqpItemWriterBuilder<Product>()
                .amqpTemplate(productRabbitTemplate)
                .build();
    }

    @Bean
    public ClientUpperCaseProcessor clientUpperCaseProcessor() {
        return new ClientUpperCaseProcessor();
    }

    @Bean
    public ProductUpperCaseProcessor productUpperCaseProcessor() {
        return new ProductUpperCaseProcessor();
    }

    @Bean
    public Job importClientJob(Step step1, Step step2) {
        return jobBuilderFactory.get("importClientJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .next(step2)
                .end()
                .build();
    }

    @Bean
    public Step step1(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        return stepBuilderFactory.get("step1")
                .<Client, Client>chunk(chunkSize)
                .reader(clientCursorItemReader())
                .processor(clientUpperCaseProcessor())
                .writer(clientAmqpItemWriter())
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step step2(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        return stepBuilderFactory.get("step2")
                .<Product, Product>chunk(chunkSize)
                .reader(productCursorItemReader())
                .processor(productUpperCaseProcessor())
                .writer(productAmqpItemWriter())
                .taskExecutor(taskExecutor)
                .build();
    }
}
