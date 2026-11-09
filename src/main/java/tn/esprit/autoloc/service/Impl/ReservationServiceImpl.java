package tn.esprit.autoloc.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.ReservationRequestDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        if (dto.getDateDebut() != null && dto.getDateFin() != null
                && dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        Reservation reservation = reservationMapper.toEntity(dto);

        if (dto.getIdClient() != null) {
            Client client = clientRepository.findById(dto.getIdClient())
                    .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getIdClient()));
            reservation.setClient(client);
        }

        if (dto.getIdVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));
            reservation.setVehicule(vehicule);
        }

        if (dto.getIdContrat() != null) {
            Contrat contrat = contratRepository.findById(dto.getIdContrat())
                    .orElseThrow(() -> new ResourceNotFoundException("Contrat", dto.getIdContrat()));
            reservation.setContrat(contrat);
        }

        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toResponseDTO(saved);
    }

    @Override
    public ReservationResponseDTO findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        return reservationMapper.toResponseDTO(reservation);
    }

    @Override
    public List<ReservationResponseDTO> findAll() {
        return reservationMapper.toResponseDTOList(reservationRepository.findAll());
    }

    @Override
    public ReservationResponseDTO update(Long id, ReservationRequestDTO dto) {
        Reservation existante = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));

        if (dto.getDateDebut() != null && dto.getDateFin() != null
                && dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        reservationMapper.updateEntityFromDTO(dto, existante);

        if (dto.getIdClient() != null) {
            Client client = clientRepository.findById(dto.getIdClient())
                    .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getIdClient()));
            existante.setClient(client);
        } else {
            existante.setClient(null);
        }

        if (dto.getIdVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getIdVehicule()));
            existante.setVehicule(vehicule);
        } else {
            existante.setVehicule(null);
        }

        if (dto.getIdContrat() != null) {
            Contrat contrat = contratRepository.findById(dto.getIdContrat())
                    .orElseThrow(() -> new ResourceNotFoundException("Contrat", dto.getIdContrat()));
            existante.setContrat(contrat);
        } else {
            existante.setContrat(null);
        }

        Reservation updated = reservationRepository.save(existante);
        return reservationMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }
}