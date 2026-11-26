package tn.esprit.autoloc.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class VehiculeSchedulerService {

    private final IReservationRepository reservationRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final tn.esprit.autoloc.repository.IContratRepository contratRepository;

    public VehiculeSchedulerService(IReservationRepository reservationRepository, IVehiculeRepository vehiculeRepository, tn.esprit.autoloc.repository.IContratRepository contratRepository) {
        this.reservationRepository = reservationRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.contratRepository = contratRepository;
    }

    @Scheduled(cron = "0 0 1 * * *") // 01:00 daily
    @Transactional
    public void finalizeEndedReservations() {
        LocalDate today = LocalDate.now();
      List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.getDateFin() != null &&
                !reservation.getStatut().equals(StatutReservation.TERMINEE) &&
                    reservation.getDateFin().isBefore(today.plusDays(1))) { // ended up to today
             reservation.setStatut(StatutReservation.TERMINEE);
        if (reservation.getVehicule() != null) {
            reservation.getVehicule().setStatut(StatutVehicule.DISPONIBLE);vehiculeRepository.save(reservation.getVehicule());
                }
                reservationRepository.save(reservation);
            }
        }
    }
}
