package tech.guilhermekaua.otisoftwaredesafiocep.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tech.guilhermekaua.otisoftwaredesafiocep.dao.CepRepository;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.CepFilterDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.CreateCepDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;
import tech.guilhermekaua.otisoftwaredesafiocep.error.enums.ErrorCode;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.ErrorCodeException;
import tech.guilhermekaua.otisoftwaredesafiocep.utils.Utils;
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

    public Page<CEP> filterMany(CepFilterDTO filter, Pageable pageable) {
        return cepRepository.filterMany(filter.cidade(), filter.logradouro(), pageable);
    }

    public CEP create(@Valid CreateCepDTO dto) {
        final String unformattedCep = Utils.unformatCep(dto.cep());

        cepRepository.findByCep(unformattedCep).ifPresent((cep) -> {
            throw new ErrorCodeException(ErrorCode.CEP_ALREADY_EXISTS);
        });

        final CEP cep = new CEP(null, unformattedCep, dto.logradouro(), dto.bairro(), dto.cidade(), dto.estado());
        return cepRepository.save(cep);
    }
}
