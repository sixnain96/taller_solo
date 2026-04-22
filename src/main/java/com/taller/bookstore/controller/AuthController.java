package com.taller.bookstore.controller;

import com.taller.bookstore.config.ApiResponseBuilder;
import com.taller.bookstore.dto.request.LoginRequest;
import com.taller.bookstore.dto.request.RegisterRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.AuthResponse;
import com.taller.bookstore.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@io.swagger.v3.oas.annotations.tags.Tag(
        name = "Autenticación",
        description = "Endpoints públicos para registro e inicio de sesión"
)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ApiResponseBuilder responseBuilder;

    public AuthController(AuthService authService, ApiResponseBuilder responseBuilder) {
        this.authService = authService;
        this.responseBuilder = responseBuilder;
    }

    @io.swagger.v3.oas.annotations.Operation(
            summary = "Registrar usuario",
            description = "Crea un nuevo usuario en el sistema y devuelve un token JWT"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error de validación"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "El correo ya está registrado")
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBuilder.success(HttpStatus.CREATED, "Usuario registrado correctamente", response));
    }

    @io.swagger.v3.oas.annotations.Operation(
            summary = "Iniciar sesión",
            description = "Autentica al usuario y devuelve un token JWT válido"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error de validación"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Inicio de sesión exitoso", response));
    }
}