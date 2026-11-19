package tn.esprit.autoloc.pattern.observer;

import tn.esprit.autoloc.domain.Reservation;

public interface ReservationSubject {
    /**
     * Enregistre un nouvel observateur auprès du sujet.
     *
     * @param observer L'observateur à ajouter
     */
    void attach(ReservationObserver observer);

    /**
     * Supprime un observateur de la liste des abonnés.
     *
     * @param observer L'observateur à retirer
     */
    void detach(ReservationObserver observer);

    /**
     * Notifie tous les observateurs enregistrés d'un événement sur une réservation.
     *
     * @param reservation L'entité Réservation concernée
     */
    void notifyObservers(Reservation reservation);
}
