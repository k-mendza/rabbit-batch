package copy.base.fetcher.controller;


import copy.base.fetcher.config.JobControllerConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {
    private final JobLauncher jobLauncher;
    private final Job job;
    private final JobControllerConfiguration configuration;

    public JobController(JobLauncher jobLauncher, Job job, JobControllerConfiguration configuration) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.configuration = configuration;
    }

    @PostMapping("/{authKey}")
    public ResponseEntity<String> startJob(@PathVariable String authKey) throws Exception {
        return verifyAuthToken(authKey) ? handleBatchJob() : getInvalidTokenResponse();
    }

    private ResponseEntity<String> getInvalidTokenResponse() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<String> handleBatchJob() throws Exception {
        runJob();
        return ResponseEntity.ok("Job started successfully");
    }

    private void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
        jobLauncher.run(job, jobParameters);
    }

    private boolean verifyAuthToken(String authKey) {
        return authKey.equals(configuration.getAuthKey());
    }
}
