package tech.guilhermekaua.otisoftwaredesafiocep.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CEP cep = (CEP) o;
        return Objects.equals(id, cep.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
