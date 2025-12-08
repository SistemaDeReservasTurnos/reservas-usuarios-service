package com.servicio.reservas.usuarios.infraestructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicio.reservas.usuarios.aplication.dto.UpdateEmailRequest;
import com.servicio.reservas.usuarios.aplication.dto.UpdatePasswordRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;
import com.servicio.reservas.usuarios.aplication.services.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    // ---- Configuración ----
    @TestConfiguration
    static class MockConfig {
        @Bean
        UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    // ---------------------------------------------------------------
    // TEST: POST /api/users/create
    // ---------------------------------------------------------------
    @Test
    void createUser_Returns201() throws Exception {
        UserRequest request = new UserRequest(
                "Juan",
                "test@mail.com",
                "123",
                "3001234567",
                "CLIENTE"
        );

        UserResponse response = new UserResponse(
                1L,
                "Juan",
                "test@mail.com",
                "123",
                "3001234567",
                "CLIENTE"
        );

        Mockito.when(userService.createuser(any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@mail.com"));
    }

    // ---------------------------------------------------------------
    // TEST: GET /api/users/user/{id}
    // ---------------------------------------------------------------
    @Test
    void getUserById_Returns200() throws Exception {

        UserResponse response = new UserResponse(
                1L,
                "Juan",
                "test@mail.com",
                "123",
                "3001234567",
                "CLIENTE"
        );

        Mockito.when(userService.getUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/users/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.com"));
    }

    // ---------------------------------------------------------------
    // TEST: PUT /deactivate/{email}
    // ---------------------------------------------------------------
    @Test
    void deactivateUser_Returns200() throws Exception {

        mockMvc.perform(put("/api/users/deactivate/test@mail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully deactivated"));
    }

    // ---------------------------------------------------------------
    // TEST: PUT /update/{email}/{column}/{value}
    // ---------------------------------------------------------------
    @Test
    void updateUser_Returns200() throws Exception {

        mockMvc.perform(put("/api/users/update/test@mail.com/name/Carlos"))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully updated"));
    }

    // ---------------------------------------------------------------
    // TEST: PUT /update-password/{email}
    // ---------------------------------------------------------------
    @Test
    void updatePassword_Returns200() throws Exception {

        UpdatePasswordRequest request = new UpdatePasswordRequest("old123", "new123");

        mockMvc.perform(put("/api/users/update-password/test@mail.com")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password updated correctly"));
    }


    // ---------------------------------------------------------------
    // TEST: PUT /update-email/{email}
    // ---------------------------------------------------------------
    @Test
    void updateEmail_Returns200() throws Exception {

        UpdateEmailRequest request = new UpdateEmailRequest("old123", "nuevo@mail.com");

        mockMvc.perform(put("/api/users/update-email/test@mail.com")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Email updated correctly"));
    }
}
