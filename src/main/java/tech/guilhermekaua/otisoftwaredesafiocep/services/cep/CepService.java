package tech.guilhermekaua.otisoftwaredesafiocep.services.cep;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tech.guilhermekaua.otisoftwaredesafiocep.dao.CepRepository;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep.CepFilterDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep.CreateCepDTO;
import tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep.UpdateCepDTO;
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
        final String unformattedCep = Utils.unformatCep(cep);

        return cepRepository.findByCep(unformattedCep).orElseThrow(() -> new ErrorCodeException(ErrorCode.CEP_NOT_FOUND));
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

    public CEP update(@Valid UpdateCepDTO dto) {
        final String unformattedCep = Utils.unformatCep(dto.cep());

        final CEP cep = cepRepository.findByCep(unformattedCep).orElseThrow(() -> new ErrorCodeException(ErrorCode.CEP_NOT_FOUND));

        if (dto.logradouro() != null && !dto.logradouro().isBlank())
            cep.setLogradouro(dto.logradouro());

        if (dto.bairro() != null && !dto.bairro().isBlank())
            cep.setBairro(dto.bairro());

        if (dto.cidade() != null && !dto.cidade().isBlank())
            cep.setCidade(dto.cidade());

        if (dto.estado() != null && !dto.estado().isBlank())
            cep.setEstado(dto.estado());

        return cepRepository.save(cep);
    }
}
