package tech.guilhermekaua.otisoftwaredesafiocep.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep.CepDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep.CepFilterDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep.CreateCepDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep.UpdateCepDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.handler.ApiErrorResponse;
import tech.guilhermekaua.otisoftwaredesafiocep.services.cep.CepService;
import tech.guilhermekaua.otisoftwaredesafiocep.validation.Cep;

@RestController
@RequestMapping("/cep")
@Validated
@SecurityRequirement(name = "JWT Bearer")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "Sem autorização.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Erro de validação.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
})
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
    })
    public CepDTO findByCep(@PathVariable @Parameter(description = "CEP no formato xxxxxxxx|xxxxx-xxx|xx.xxx-xxx") @Cep String cep) {
        return CepDTO.fromCEP(cepService.findByCep(cep));
    }

    @GetMapping("/filter")
    @Operation(summary = "Retorna uma lista de CEPs filtrados por logradouro e/ou cidade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista paginada com todos os CEPs encontrados."),
    })
    public Page<CepDTO> filterMany(@ModelAttribute @ParameterObject CepFilterDTO filterDTO, @ParameterObject Pageable pageable) {
        return CepDTO.fromCepPage(cepService.filterMany(filterDTO, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um CEP")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cep criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Já existe um CEP com esse valor.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CepDTO create(@RequestBody @Valid CreateCepDTO dto) {
        return CepDTO.fromCEP(cepService.create(dto));
    }

    @PutMapping
    @Operation(summary = "Atualiza os dados de um CEP")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cep atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public CepDTO update(@RequestBody @Valid UpdateCepDTO dto) {
        return CepDTO.fromCEP(cepService.update(dto));
    }
}
