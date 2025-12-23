package org.ecommerce.authserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import org.ecommerce.authserver.dto.UserRegisterRequest;
import org.ecommerce.authserver.dto.UserRegisterResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AuthController {

    @Operation(
            operationId = "userRegistration",
            summary = "Register new users",
            description = "This endpoint is used to register new users and is open for all users",
            tags = {"Auth"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration request body",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRegisterRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRegisterResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = """
                                        {
                                          "id": 1,
                                          "email": "user@test.com",
                                          "role": "CUSTOMER"
                                        }
                                        """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Validation error or user already exists"
                    )
            }
    )
    @PostMapping("/register")
    ResponseEntity<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest);

    @Operation(
            summary = "Login and get JWT token",
            description = "Authenticate user and return JWT token",
            tags = {"Auth"},
            parameters = {
                    @Parameter(
                            name = "username",
                            description = "User email or username",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(example = "user@email.com")
                    ),
                    @Parameter(
                            name = "password",
                            description = "User password",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(example = "secret123")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "JWT token generated",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> token(Authentication authentication);

    @Operation(
            summary = "Get all users",
            description = "Retrieve all registered users (ADMIN only)",
            tags = {"Admin"},
            security = @SecurityRequirement(name = "BearerJWT"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of users",
                            content = @Content(
                                    schema = @Schema(implementation = UserRegisterResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied"
                    )
            }
    )
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserRegisterResponse>> showAllUsers();

    @Operation(
            summary = "Suspend user",
            description = "Suspend user account by user ID (ADMIN only)",
            tags = {"Admin"},
            security = @SecurityRequirement(name = "BearerJWT"),
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID of the user to suspend",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User suspended successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Access denied"
                    )
            }
    )
    @PatchMapping("/postpone/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity showUserById(@PathVariable Long userId);
}
