package tech.guilhermekaua.otisoftwaredesafiocep.services;

import org.springframework.stereotype.Service;
import tech.guilhermekaua.otisoftwaredesafiocep.dao.CepRepository;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;
import tech.guilhermekaua.otisoftwaredesafiocep.error.enums.ErrorCode;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.ErrorCodeException;

@Service
public class CepService {
    private final CepRepository cepRepository;

    public CepService(CepRepository cepRepository) {
        this.cepRepository = cepRepository;
    }

    public CEP findByCep(String cep) {
        return cepRepository.findByCep(cep).orElseThrow(() -> new ErrorCodeException(ErrorCode.CEP_NOT_FOUND));
    }
}
