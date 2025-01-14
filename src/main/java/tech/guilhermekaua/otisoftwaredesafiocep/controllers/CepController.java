package tech.guilhermekaua.otisoftwaredesafiocep.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;
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
            @ApiResponse(responseCode = "404", description = "CEP não encontrado."),
            @ApiResponse(responseCode = "400", description = "Erro de validação.")
    })
    public CEP findByCep(@PathVariable @Cep String cep) {
        return cepService.findByCep(cep);
    }
}
