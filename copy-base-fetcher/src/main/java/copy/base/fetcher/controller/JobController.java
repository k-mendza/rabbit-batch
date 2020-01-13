package copy.base.fetcher.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/job")
public class JobController {

    @RequestMapping("/{authKey}")
    public @ResponseBody int startJob(@PathVariable(value="authKey") String authKey){
        return HttpStatus.OK.value();
    }
}
