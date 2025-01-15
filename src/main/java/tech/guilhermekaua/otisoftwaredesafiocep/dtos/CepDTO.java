package tech.guilhermekaua.otisoftwaredesafiocep.dtos;

import org.springframework.data.domain.Page;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;
import tech.guilhermekaua.otisoftwaredesafiocep.utils.Utils;

public record CepDTO(Long id,
                     String cep,
                     String logradouro,
                     String bairro,
                     String cidade,
                     String estado) {
    public static CepDTO fromCEP(CEP cep) {
        return new CepDTO(cep.getId(), Utils.formatCep(cep.getCep()), cep.getLogradouro(), cep.getBairro(), cep.getCidade(), cep.getEstado());
    }

    public static Page<CepDTO> fromCepPage(Page<CEP> page) {
        return page.map(CepDTO::fromCEP);
    }
}
