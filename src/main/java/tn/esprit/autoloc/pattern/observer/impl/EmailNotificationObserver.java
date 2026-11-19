package tn.esprit.autoloc.pattern.observer.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.pattern.observer.ReservationObserver;

@Slf4j
@Component
public class EmailNotificationObserver implements ReservationObserver {

    @Override
    public void update(Reservation reservation) {
        Client client = reservation.getClient();
        if (client == null) {
            log.info("[EMAIL OBSERVER] Aucun client associé à la réservation n°{}", reservation.getIdReservation());
            return;
        }

        switch (reservation.getStatut()) {
            case CONFIRMEE -> log.info("[EMAIL OBSERVER] Email de confirmation envoyé à {} ({}) pour la réservation n°{}",
                    client.getPrenom() + " " + client.getNom(), client.getEmail(), reservation.getIdReservation());
            case ANNULEE -> log.info("[EMAIL OBSERVER] Email d'annulation envoyé à {} ({}) pour la réservation n°{}",
                    client.getPrenom() + " " + client.getNom(), client.getEmail(), reservation.getIdReservation());
            case TERMINEE -> log.info("[EMAIL OBSERVER] Enquête de satisfaction envoyée à {} ({}) suite à la fin de location n°{}",
                    client.getPrenom() + " " + client.getNom(), client.getEmail(), reservation.getIdReservation());
            default -> log.info("[EMAIL OBSERVER] Notification envoyée à {} pour la réservation n°{} (statut: {})",
                    client.getEmail(), reservation.getIdReservation(), reservation.getStatut());
        }
    }
}
