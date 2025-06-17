package com.bookstore.onlinebookstore.Controller;

import com.bookstore.onlinebookstore.dto.request.UserCreationRequest;
import com.bookstore.onlinebookstore.dto.response.UserResponse;
import com.bookstore.onlinebookstore.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.convention.TestBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;

    @MockitoBean
    private UserService userService;

    @BeforeEach
    public void initData(){
        userCreationRequest = UserCreationRequest.builder()
                .username("john")
                .password("123456789")
                .full_name("John Doe")
                .address("city")
                .build();

        userResponse = UserResponse.builder()
                .username("john")
                .full_name("John Doe")
                .address("city")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String content = mapper.writeValueAsString(userCreationRequest);

        Mockito.when(userService.addUser(ArgumentMatchers.any()))
                        .thenReturn(userResponse);

        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value("1000"))

        ;




    }
}
