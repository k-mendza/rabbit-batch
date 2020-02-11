package copy.base.fetcher.controller;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${fetcher.job.controller.auth.key}")
    private String expectedAuthKey;

    public JobController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @RequestMapping("/{authKey}")
    public @ResponseBody int startJob(@PathVariable(value="authKey") String authKey) throws Exception {
        if (authKey.equals(expectedAuthKey)) {
            JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
            jobLauncher.run(job, jobParameters);
            return HttpStatus.OK.value();
        }
        return HttpStatus.UNAUTHORIZED.value();
    }
}
