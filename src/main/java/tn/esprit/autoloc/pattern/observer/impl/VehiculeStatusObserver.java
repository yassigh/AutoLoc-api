package tn.esprit.autoloc.pattern.observer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.pattern.observer.ReservationObserver;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehiculeStatusObserver implements ReservationObserver {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public void update(Reservation reservation) {
        Vehicule vehicule = reservation.getVehicule();
        if (vehicule == null) {
            return;
        }
        StatutReservation statutReservation = reservation.getStatut();
        if (statutReservation == null) {
            return;
        }
        switch (statutReservation) {
            case CONFIRMEE -> {
                if (vehicule.getStatut() != StatutVehicule.LOUE) {
                    vehicule.setStatut(StatutVehicule.LOUE);
                    vehiculeRepository.save(vehicule); log.info("[VEHICULE OBSERVER] Véhicule {} {} (ID: {}) marqué comme LOUE suite à la confirmation de la réservation n°{}",
                            vehicule.getMarque(), vehicule.getModele(), vehicule.getIdVehicule(), reservation.getIdReservation());
                }
            }
            case ANNULEE, TERMINEE -> { if (vehicule.getStatut() != StatutVehicule.DISPONIBLE) {
                    vehicule.setStatut(StatutVehicule.DISPONIBLE);
                    vehiculeRepository.save(vehicule);
                    log.info("[VEHICULE OBSERVER] Véhicule {} {} (ID: {}) remis en DISPONIBLE suite à l'événement ({}) sur la réservation n°{}",
                            vehicule.getMarque(), vehicule.getModele(), vehicule.getIdVehicule(), statutReservation, reservation.getIdReservation());
                }
            }
            default -> {
                // Autres statuts (EN_ATTENTE, etc.) ne modifient pas directement le véhicule
            }
        }
    }
}
