package tech.guilhermekaua.otisoftwaredesafiocep.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tech.guilhermekaua.otisoftwaredesafiocep.dao.CepRepository;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;
import tech.guilhermekaua.otisoftwaredesafiocep.error.enums.ErrorCode;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.ErrorCodeException;
import tech.guilhermekaua.otisoftwaredesafiocep.validation.Cep;

@Service
@Validated
public class CepService {
    private final CepRepository cepRepository;

    public CepService(CepRepository cepRepository) {
        this.cepRepository = cepRepository;
    }

    public CEP findByCep(@Cep String cep) {
        return cepRepository.findByCep(cep).orElseThrow(() -> new ErrorCodeException(ErrorCode.CEP_NOT_FOUND));
    }
}
