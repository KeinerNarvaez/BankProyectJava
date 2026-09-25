package com.Bank.BankBackend.modules.user.interfaces.rest;

import com.Bank.BankBackend.modules.user.application.dto.request.*;
import com.Bank.BankBackend.modules.user.application.dto.response.*;
import com.Bank.BankBackend.modules.user.application.port.in.*;
import com.Bank.BankBackend.shared.infrastructure.security.SecurityUser;
import com.Bank.BankBackend.shared.interfaces.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints de gestión de usuarios: creación, login y recuperación de contraseña")
public class UserController {
    private final CreateUserPort createUserPort;
    private final ChangePasswordPort changePasswordPort;
    private final LoginPort loginPort;
    private final UserActivationPort userActivationPort;
    private final ForgotPasswordPort forgotPasswordPort;
    private final UserDeletePort userDeletePort;
    private final GetUsersPort getUsersPort;

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse response = createUserPort.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @Operation(summary = "Cambiar la contraseña del usuario autenticado")
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody ChangePasswordRequest request) {

        changePasswordPort.changePassword(securityUser.getUserId(),request);

        return ResponseEntity.ok(
                ApiResponse.ok("Contraseña cambiada correctamente")
        );
    }

    @Operation(summary = "Solicitar código para recuperar la contraseña")
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        forgotPasswordPort.forgotPassword(request);

        return ResponseEntity.ok(
                ApiResponse.ok("Código de restablecimiento de contraseña enviado correctamente")
        );
    }

    @Operation(summary = "Verificar código de recuperación de contraseña")
    @PostMapping("/forgot-password/verify-code")
    public ResponseEntity<ApiResponse<VerifyCodeForgotPasswordResponse>> verifyCode(
            @Valid @RequestBody VerifyCodeForgotPasswordRequest request) {

        VerifyCodeForgotPasswordResponse response =
                forgotPasswordPort.verifyCode(request);

        return ResponseEntity.ok(
                ApiResponse.ok(response)
        );
    }

    @Operation(summary = "Cambiar contraseña mediante recuperación")
    @PostMapping("/forgot-password/change")
    public ResponseEntity<ApiResponse<String>> changeForgotPassword(
            @Valid @RequestBody ChangeForgotPasswordRequest request) {

        forgotPasswordPort.changePassword(request);

        return ResponseEntity.ok(
                ApiResponse.ok("Contraseña cambiada correctamente")
        );
    }

    @Operation(summary = "Iniciar sesión")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = loginPort.login(request);

        return ResponseEntity.ok(
                ApiResponse.ok(response)
        );
    }
    
    /*    @Operation(summary = "Cerrar sesión")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @Valid @RequestBody LogoutRequest request) {

        logoutPort.logout(request);

        return ResponseEntity.ok(
                ApiResponse.ok("")
        );
    }*/

    @Operation(summary = "Activar la cuenta de un usuario")
    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<UserActivationResponse>> activateUser(
            @Valid @RequestBody UserActivationTokenRequest request) {

        UserActivationResponse response =
                userActivationPort.userActivation(request);

        return ResponseEntity.ok(
                ApiResponse.ok(response)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary="Desactivar cuenta solo ADMIN")
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<UserDeleteResponse>> delete(
            @Valid @RequestBody UserDeleteRequest request) {

        UserDeleteResponse response =
                userDeletePort.delete(request);

        return ResponseEntity.ok(
                ApiResponse.ok(response)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary="Revisar los usuarios activos")
    @PostMapping("/findAllUsers")
    public ResponseEntity<ApiResponse<List<UserListResponse>>> findAll(){
        List<UserListResponse> users = getUsersPort.listUsers();
        return ResponseEntity.ok(
                ApiResponse.ok(users)
        );
    }

}