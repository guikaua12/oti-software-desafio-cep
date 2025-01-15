package tech.guilhermekaua.otisoftwaredesafiocep.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication.LoginDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication.RegisterDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication.SuccessfulAuthenticationDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.handler.ApiErrorResponse;
import tech.guilhermekaua.otisoftwaredesafiocep.services.security.AuthenticationService;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @Operation(summary = "Se autentica como um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação bem sucedida."),
            @ApiResponse(responseCode = "400", description = "Erro de validação.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public SuccessfulAuthenticationDTO login(@RequestBody @Valid LoginDTO dto) {
        return authenticationService.login(dto);
    }

    @PostMapping("/register")
    @Operation(summary = "Se registra como um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro bem sucedido."),
            @ApiResponse(responseCode = "400", description = "Erro de validação.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Usuário já existente", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public SuccessfulAuthenticationDTO register(@RequestBody @Valid RegisterDTO dto) {
        return authenticationService.register(dto);
    }
}
