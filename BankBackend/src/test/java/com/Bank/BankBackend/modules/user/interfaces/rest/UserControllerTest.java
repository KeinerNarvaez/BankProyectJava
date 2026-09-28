package com.Bank.BankBackend.modules.user.interfaces.rest;

import com.Bank.BankBackend.modules.user.application.dto.request.*;
import com.Bank.BankBackend.modules.user.application.dto.response.*;
import com.Bank.BankBackend.modules.user.application.port.in.*;
import com.Bank.BankBackend.modules.user.domain.model.RolType;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import tools.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.MethodSecurityTestConfig.class)
class UserControllerTest {

    @TestConfiguration
    @EnableMethodSecurity
    static class MethodSecurityTestConfig {
    }
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateUserPort createUserPort;

    @MockitoBean
    private ChangePasswordPort changePasswordPort;

    @MockitoBean
    private LoginPort loginPort;

    @MockitoBean
    private UserActivationPort userActivationPort;

    @MockitoBean
    private ForgotPasswordPort forgotPasswordPort;

    @MockitoBean
    private UserDeletePort userDeletePort;

    @MockitoBean
    private GetUsersPort getUsersPort;


    @Test
    void shouldCreateUserSuccessfully() throws Exception {

        CreateUserRequest request = CreateUserRequest.builder()
                .email("user@gmail.com")
                .password("Password123**")
                .rolType(RolType.ADVISOR)
                .build();

        UserResponse response = UserResponse.builder()
                .userId(1)
                .email("user@gmail.com")
                .UserStatus(UserStatus.INACTIVE)
                .RolType(RolType.ADVISOR)
                .build();

        when(createUserPort.create(any(CreateUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());

        verify(createUserPort)
                .create(any(CreateUserRequest.class));
    }


    @Test
    void shouldLoginSuccessfully() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .email("user@gmail.com")
                .password("Password123**")
                .build();

        LoginResponse response = LoginResponse.builder()
                .token("fake-jwt-token")
                .build();

        when(loginPort.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/users/login")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(loginPort)
                .login(any(LoginRequest.class));
    }


    @Test
    void shouldSendForgotPasswordCode() throws Exception {

        ForgotPasswordRequest request =
                ForgotPasswordRequest.builder()
                        .email("user@gmail.com")
                        .build();

        mockMvc.perform(
                        post("/api/v1/users/forgot-password")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(forgotPasswordPort)
                .forgotPassword(any(ForgotPasswordRequest.class));
    }


    @Test
    void shouldVerifyForgotPasswordCode() throws Exception {

        VerifyCodeForgotPasswordRequest request =
                VerifyCodeForgotPasswordRequest.builder()
                        .email("user@gmail.com")
                        .verificationCode("ABC123")
                        .build();

        VerifyCodeForgotPasswordResponse response =
                VerifyCodeForgotPasswordResponse.builder()
                        .valid(true)
                        .message("Código válido")
                        .build();

        when(forgotPasswordPort.verifyCode(
                any(VerifyCodeForgotPasswordRequest.class)
        )).thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/users/forgot-password/verify-code")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(forgotPasswordPort)
                .verifyCode(any(VerifyCodeForgotPasswordRequest.class));
    }


    @Test
    void shouldChangeForgotPassword() throws Exception {

        ChangeForgotPasswordRequest request =
                ChangeForgotPasswordRequest.builder()
                        .email("user@gmail.com")
                        .token("ABC123")
                        .newPassword("NewPassword123**")
                        .build();

        mockMvc.perform(
                        post("/api/v1/users/forgot-password/change")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(forgotPasswordPort)
                .changePassword(any(ChangeForgotPasswordRequest.class));
    }


    @Test
    void shouldActivateUserSuccessfully() throws Exception {

        UserActivationTokenRequest request =
                UserActivationTokenRequest.builder()
                        .email("user@gmail.com")
                        .code("ABC123")
                        .build();

        UserActivationResponse response =
                UserActivationResponse.builder()
                        .valid(true)
                        .message("Se activo correctamente la cuenta")
                        .build();

        when(userActivationPort.userActivation(
                any(UserActivationTokenRequest.class)
        )).thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/users/activate")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(userActivationPort)
                .userActivation(any(UserActivationTokenRequest.class));
    }


    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldDeleteUserWhenAdmin() throws Exception {

        UserDeleteRequest request =
                UserDeleteRequest.builder()
                        .userId(1)
                        .build();

        UserDeleteResponse response =
                UserDeleteResponse.builder()
                        .valid(true)
                        .message("Usuario eliminado correctamente")
                        .build();

        when(userDeletePort.delete(any(UserDeleteRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/users/delete")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(userDeletePort)
                .delete(any(UserDeleteRequest.class));
    }


    @WithMockUser(roles = "ADVISOR")
    @Test
    void shouldReturn403WhenAdvisorTriesToDelete() throws Exception {

        UserDeleteRequest request =
                UserDeleteRequest.builder()
                        .userId(1)
                        .build();

        mockMvc.perform(
                        post("/api/v1/users/delete")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isForbidden());
    }


    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturnUsersWhenAdmin() throws Exception {

        UserListResponse user1 =
                new UserListResponse(
                        1,
                        "user1@gmail.com"
                );

        UserListResponse user2 =
                new UserListResponse(
                        2,
                        "user2@gmail.com"
                );

        when(getUsersPort.listUsers())
                .thenReturn(List.of(user1, user2));

        mockMvc.perform(
                        post("/api/v1/users/findAllUsers")
                )
                .andExpect(status().isOk());

        verify(getUsersPort)
                .listUsers();
    }


    @WithMockUser(roles = "ADVISOR")
    @Test
    void shouldReturn403WhenAdvisorTriesToListUsers() throws Exception {

        mockMvc.perform(
                        post("/api/v1/users/findAllUsers")
                )
                .andExpect(status().isForbidden());
    }
}