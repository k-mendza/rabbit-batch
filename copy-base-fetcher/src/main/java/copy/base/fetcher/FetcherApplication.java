package copy.base.fetcher;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableBatchProcessing
@SpringBootApplication
public class FetcherApplication {

    @Value(value = "${fetcher.core-pool-size}")
    private int corePoolSize;

    @Value(value = "${fetcher.max-core-pool-size}")
    private int maxCorePoolSize;

    public static void main(String[] args) {
        SpringApplication.run(FetcherApplication.class, args);
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxCorePoolSize);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
