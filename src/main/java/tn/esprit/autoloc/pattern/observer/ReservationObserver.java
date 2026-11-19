package tn.esprit.autoloc.pattern.observer;

import tn.esprit.autoloc.domain.Reservation;

public interface ReservationObserver {
    /**
     * Méthode appelée lorsque l'état d'une réservation change ou est créée.
     *
     * @param reservation L'entité Réservation mise à jour
     */
    void update(Reservation reservation);
}
