package copy.base.fetcher.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JobControllerTest {

    @Autowired
    private JobController controller;

    @Value("${fetcher.job.controller.auth.key}")
    private String authKey;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void startJob() throws Exception {
        mockMvc.perform(post("/api/v1/job/{authKey}", authKey))
                .andExpect(status().is2xxSuccessful());
    }
}
