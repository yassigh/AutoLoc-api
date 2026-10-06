package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;
import java.util.Optional;

public interface IVehiculeService {

    Vehicule add(Vehicule vehicule);

    Vehicule update(Vehicule vehicule);

    List<Vehicule> findAll();

    Optional<Vehicule> findById(Long id);

    void deleteById(Long id);
}