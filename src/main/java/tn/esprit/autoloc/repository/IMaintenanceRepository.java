package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Maintenance;

import java.util.Optional;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
    Optional<Maintenance> findFirstByDescription(String description);
}