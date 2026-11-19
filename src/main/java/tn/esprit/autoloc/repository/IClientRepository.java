package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Client;

import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNomAndPrenom(String nom, String prenom);
}