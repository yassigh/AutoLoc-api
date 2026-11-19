package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Reservation;

import java.time.LocalDate;
import java.util.Optional;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);
}