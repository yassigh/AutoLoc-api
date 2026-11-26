
package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Contrat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IContratRepository extends JpaRepository<Contrat, Long> {
    Optional<Contrat> findFirstByDateSignature(LocalDate dateSignature);
    List<Contrat> findByDateSignatureBetween(LocalDate start, LocalDate end);

}
