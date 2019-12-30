package copy.base.pusher;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class PusherApplication {

    public static void main(String[] args) {
        SpringApplication.run(PusherApplication.class, args);
    }

}
