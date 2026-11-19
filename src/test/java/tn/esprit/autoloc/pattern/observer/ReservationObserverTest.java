package tn.esprit.autoloc.pattern.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.ReservationRequestDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.pattern.observer.impl.AgenceNotificationObserver;
import tn.esprit.autoloc.pattern.observer.impl.EmailNotificationObserver;
import tn.esprit.autoloc.pattern.observer.impl.VehiculeStatusObserver;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.Impl.ReservationServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationObserverTest {

    @Mock
    private IReservationRepository reservationRepository;
    @Mock
    private IClientRepository clientRepository;
    @Mock
    private IVehiculeRepository vehiculeRepository;
    @Mock
    private IContratRepository contratRepository;
    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private ReservationObserver mockCustomObserver;

    private VehiculeStatusObserver vehiculeStatusObserver;
    private EmailNotificationObserver emailNotificationObserver;
    private AgenceNotificationObserver agenceNotificationObserver;
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        vehiculeStatusObserver = new VehiculeStatusObserver(vehiculeRepository);
        emailNotificationObserver = new EmailNotificationObserver();
        agenceNotificationObserver = new AgenceNotificationObserver();

        List<ReservationObserver> observers = new ArrayList<>();
        observers.add(vehiculeStatusObserver);
        observers.add(emailNotificationObserver);
        observers.add(agenceNotificationObserver);

        reservationService = new ReservationServiceImpl(
                reservationRepository,
                clientRepository,
                vehiculeRepository,
                contratRepository,
                reservationMapper,
                observers
        );
    }

    @Test
    @DisplayName("Quand une réservation est confirmée, le VehiculeStatusObserver passe le véhicule en LOUE")
    void testVehiculeStatusObserverOnConfirmation() {
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(1L);
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        Reservation reservation = new Reservation();
        reservation.setIdReservation(10L);
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setVehicule(vehicule);

        vehiculeStatusObserver.update(reservation);

        ArgumentCaptor<Vehicule> captor = ArgumentCaptor.forClass(Vehicule.class);
        verify(vehiculeRepository).save(captor.capture());
        assertEquals(StatutVehicule.LOUE, captor.getValue().getStatut());
    }

    @Test
    @DisplayName("Quand une réservation est annulée, le VehiculeStatusObserver remet le véhicule en DISPONIBLE")
    void testVehiculeStatusObserverOnCancellation() {
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(2L);
        vehicule.setMarque("Peugeot");
        vehicule.setModele("208");
        vehicule.setStatut(StatutVehicule.LOUE);

        Reservation reservation = new Reservation();
        reservation.setIdReservation(11L);
        reservation.setStatut(StatutReservation.ANNULEE);
        reservation.setVehicule(vehicule);

        vehiculeStatusObserver.update(reservation);

        ArgumentCaptor<Vehicule> captor = ArgumentCaptor.forClass(Vehicule.class);
        verify(vehiculeRepository).save(captor.capture());
        assertEquals(StatutVehicule.DISPONIBLE, captor.getValue().getStatut());
    }

    @Test
    @DisplayName("ReservationServiceImpl notifie les observateurs lors de la création d'une réservation")
    void testReservationServiceNotifiesObserversOnCreate() {
        reservationService.attach(mockCustomObserver);

        ReservationRequestDTO dto = new ReservationRequestDTO();
        dto.setDateDebut(LocalDate.now());
        dto.setDateFin(LocalDate.now().plusDays(3));
        dto.setStatut(StatutReservation.CONFIRMEE);
        dto.setIdVehicule(1L);

        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(1L);
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        Reservation entity = new Reservation();
        entity.setIdReservation(100L);
        entity.setStatut(StatutReservation.CONFIRMEE);
        entity.setVehicule(vehicule);

        when(reservationMapper.toEntity(any(ReservationRequestDTO.class))).thenReturn(entity);
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(entity);
        when(reservationMapper.toResponseDTO(any(Reservation.class))).thenReturn(new ReservationResponseDTO());

        reservationService.create(dto);

        // Vérifier que notre observateur personnalisé a été notifié
        verify(mockCustomObserver, times(1)).update(entity);
        // Vérifier que le statut du véhicule a été mis à jour via le VehiculeStatusObserver
        verify(vehiculeRepository).save(any(Vehicule.class));
    }

    @Test
    @DisplayName("Exemple simple d'exécution pour capture d'écran de présentation")
    void testDemonstrationSimpleReservation() {
        Client client = new Client();
        client.setNom("Ben Ali");
        client.setPrenom("Ahmed");
        client.setEmail("ahmed.benali@esprit.tn");

        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(1L);
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setImmatriculation("245-TUN-1234");
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        Reservation reservation = new Reservation();
        reservation.setIdReservation(10L);
        reservation.setDateDebut(LocalDate.of(2026, 6, 1));
        reservation.setDateFin(LocalDate.of(2026, 6, 5));
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setVehicule(vehicule);
        reservation.setClient(client);

        System.out.println("\n==================== [CAPTURE PRESENTATION] ====================");
        reservationService.notifyObservers(reservation);
        System.out.println("================================================================\n");

        verify(vehiculeRepository).save(any(Vehicule.class));
        assertEquals(StatutVehicule.LOUE, vehicule.getStatut());
    }
}
