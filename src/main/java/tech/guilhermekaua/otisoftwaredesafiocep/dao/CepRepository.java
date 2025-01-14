package tech.guilhermekaua.otisoftwaredesafiocep.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.CEP;

import java.util.Optional;

public interface CepRepository extends JpaRepository<CEP, Long>, JpaSpecificationExecutor<CEP> {
    @Query("SELECT cep FROM CEP cep WHERE cep.cep = :cep")
    Optional<CEP> findByCep(String cep);

    @Query(value = """
            SELECT * FROM cep WHERE UNACCENT(cidade) ILIKE UNACCENT(CONCAT('%', COALESCE(:cidade, ''),'%')) AND
                        UNACCENT(logradouro) ILIKE UNACCENT(CONCAT('%', COALESCE(:logradouro, ''),'%'));""", nativeQuery = true)
    Page<CEP> filterMany(String cidade, String logradouro, Pageable pageable);
}
