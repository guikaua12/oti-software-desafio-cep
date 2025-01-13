package tech.guilhermekaua.otisoftwaredesafiocep.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;
import tech.guilhermekaua.otisoftwaredesafiocep.services.CepService;

@RestController
@RequestMapping("/cep")
public class CepController {
    private final CepService cepService;


    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/{cep}")
    public CEP getCep(@PathVariable String cep) {
        return cepService.findByCep(cep);
    }
}
