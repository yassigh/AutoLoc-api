package tn.esprit.autoloc.pattern.observer.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.pattern.observer.ReservationObserver;

@Slf4j
@Component
public class AgenceNotificationObserver implements ReservationObserver {

    @Override
    public void update(Reservation reservation) {
        if (reservation.getStatut() == StatutReservation.CONFIRMEE) {
            Vehicule vehicule = reservation.getVehicule();
            Agence agence = (vehicule != null) ? vehicule.getAgence() : null;

            String nomAgence = (agence != null) ? agence.getNom() : "Agence centrale";
            String immatVehicule = (vehicule != null) ? vehicule.getImmatriculation() : "N/A";

            log.info("[AGENCE OBSERVER] Alerte préparation envoyée à [{}] : Préparer le véhicule [{}] pour la réservation n°{} du {} au {}",
                    nomAgence, immatVehicule, reservation.getIdReservation(), reservation.getDateDebut(),
                    reservation.getDateFin());
        }
    }
}
