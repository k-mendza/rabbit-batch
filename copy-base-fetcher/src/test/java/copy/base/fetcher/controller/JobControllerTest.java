package copy.base.fetcher.controller;

import copy.base.fetcher.config.JobControllerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @InjectMocks
    private JobController controller;

    @InjectMocks
    private JobControllerConfiguration configuration;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void startJobWithCorrectAuthKey() throws Exception {
        mockMvc.perform(post("/api/v1/job/{authKey}", configuration.getAuthKey())).andExpect(status().is2xxSuccessful());
    }

    @Test
    void startJobWithWrongAuthKey() throws Exception {
        mockMvc.perform(post("/api/v1/job/{authKey}", "112233")).andExpect(status().is4xxClientError());
    }
}
