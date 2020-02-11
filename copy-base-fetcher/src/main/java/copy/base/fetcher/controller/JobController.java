package copy.base.fetcher.controller;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/job")
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job job;

    public JobController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @RequestMapping("/{authKey}")
    public @ResponseBody int startJob(@PathVariable(value="authKey") String authKey) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
        final JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        return authKey.equals("abcd") ? HttpStatus.OK.value() : HttpStatus.UNAUTHORIZED.value();
    }
}
