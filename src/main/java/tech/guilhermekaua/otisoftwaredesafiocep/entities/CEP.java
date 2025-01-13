package tech.guilhermekaua.otisoftwaredesafiocep.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cep")
public class CEP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String logradouro;
    private String cidade;
    private String estado;

    public CEP(Long id, String cep, String logradouro, String cidade, String estado) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public CEP() {
    }
}
