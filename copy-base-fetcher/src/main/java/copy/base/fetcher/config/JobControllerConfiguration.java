package copy.base.fetcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobControllerConfiguration {

    @Value("${fetcher.job.controller.auth.key}")
    private String authKey;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
