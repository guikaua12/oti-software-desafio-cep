package tech.guilhermekaua.otisoftwaredesafiocep.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.CepFilterDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.CreateCepDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.handler.ApiErrorResponse;
import tech.guilhermekaua.otisoftwaredesafiocep.services.CepService;
import tech.guilhermekaua.otisoftwaredesafiocep.validation.Cep;

@RestController
@RequestMapping("/cep")
@Validated
public class CepController {
    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @Operation(summary = "Retorna um CEP pelo valor dele")
    @GetMapping("/{cep}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CEP encontrado."),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CEP findByCep(@PathVariable @Cep String cep) {
        return cepService.findByCep(cep);
    }

    @GetMapping("/filter")
    @Operation(summary = "Retorna uma lista de CEPs filtrados por logradouro e/ou cidade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista com todos os CEPs encontrados."),
            @ApiResponse(responseCode = "400", description = "Erro de validação.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public Page<CEP> filterMany(@ModelAttribute CepFilterDTO filterDTO, Pageable pageable) {
        return cepService.filterMany(filterDTO, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um CEP")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cep criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Já existe um CEP com esse valor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CEP create(@RequestBody @Valid CreateCepDTO dto) {
        return cepService.create(dto);
    }
}
