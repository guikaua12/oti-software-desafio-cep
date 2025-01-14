package tech.guilhermekaua.otisoftwaredesafiocep.controllers;

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

    @GetMapping("/{cep}")
    public CEP findByCep(@PathVariable @Cep String cep) {
        return cepService.findByCep(cep);
    }
}
