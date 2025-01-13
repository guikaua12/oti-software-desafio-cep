package tech.guilhermekaua.otisoftwaredesafiocep.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;

import java.util.Optional;

public interface CepRepository extends JpaRepository<CEP, Long> {
    @Query("SELECT cep FROM CEP cep WHERE cep.cep = :cep")
    Optional<CEP> findByCep(String cep);
}
