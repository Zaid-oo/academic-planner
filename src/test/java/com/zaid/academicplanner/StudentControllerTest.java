package com.zaid.academicplanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginWithCorrectCredentialsShouldReturnToken() throws Exception {

        String requestBody = """
                {
                    "universityId": "202355555",
                    "password": "123456"
                }
                """;

        mockMvc.perform(post("/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void loginWithWrongPasswordShouldReturnUnauthorized() throws Exception {

        String requestBody = """
            {
                "universityId": "202355555",
                "password": "wrongpassword"
            }
            """;

        mockMvc.perform(post("/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpointWithoutTokenShouldBeBlocked() throws Exception {

        mockMvc.perform(get("/courses"))
                .andExpect(status().isForbidden());
    }

    @Test
    void protectedEndpointWithValidTokenShouldSucceed() throws Exception {

        String requestBody = """
            {
                "universityId": "202355555",
                "password": "123456"
            }
            """;

        MvcResult loginResult = mockMvc.perform(post("/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();

        String token = responseBody
                .split("\"token\":\"")[1]
                .split("\"")[0];

        mockMvc.perform(get("/courses")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void studentShouldNotAccessAnotherStudent() throws Exception {

        String requestBody = """
            {
                "universityId": "202355555",
                "password": "123456"
            }
            """;

        MvcResult loginResult = mockMvc.perform(post("/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();

        String token = responseBody
                .split("\"token\":\"")[1]
                .split("\"")[0];

        mockMvc.perform(get("/student/999999999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }


}