package com.example.helloWorld.controllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.helloWorld.model.Language;
import com.example.helloWorld.repository.LanguageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private LanguageRepository languageRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getHelloRest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/hello-rest")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(content,"Hello World!");
    }

    @Test
    public void getHelloExternal() throws Exception {
        env.setActiveProfiles("external");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/hello")
                .param("language", "English")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(content,"Zdravo svijete!");
    }

    @Test
    public void getHelloDb() throws Exception {
        env.setActiveProfiles("db");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/hello")
                .param("language", "Dutch")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(content,"Hallo Wereld");
    }

    @Test
    public void getHelloDbNotFound() throws Exception {
        env.setActiveProfiles("db");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/hello")
                .param("language", "Test")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getHelloExternalNotFound() throws Exception {
        env.setActiveProfiles("external");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/hello")
                .param("language", "Test")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addLanguage() throws Exception {
        languageRepository.save(new Language("Chinese", "CIU CA"));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/hello")
                .param("language", "Chinese")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(content,"CIU CA");
    }
}
