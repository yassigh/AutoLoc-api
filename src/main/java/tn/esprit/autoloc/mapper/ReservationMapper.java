package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.request.ReservationRequestDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;

import java.util.Collections;
import java.util.List;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());
        return reservation;
    }

    public ReservationResponseDTO toResponseDTO(Reservation entity) {
        if (entity == null) {
            return null;
        }
        return ReservationResponseDTO.builder()
                .idReservation(entity.getIdReservation())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .statut(entity.getStatut())
                .idClient(entity.getClient() != null ? entity.getClient().getIdClient() : null)
                .nomClient(entity.getClient() != null ? entity.getClient().getNom() : null)
                .prenomClient(entity.getClient() != null ? entity.getClient().getPrenom() : null)
                .idVehicule(entity.getVehicule() != null ? entity.getVehicule().getIdVehicule() : null)
                .immatriculationVehicule(entity.getVehicule() != null ? entity.getVehicule().getImmatriculation() : null)
                .marqueVehicule(entity.getVehicule() != null ? entity.getVehicule().getMarque() : null)
                .modeleVehicule(entity.getVehicule() != null ? entity.getVehicule().getModele() : null)
                .idContrat(entity.getContrat() != null ? entity.getContrat().getIdContrat() : null)
                .build();
    }

    public List<ReservationResponseDTO> toResponseDTOList(List<Reservation> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(ReservationRequestDTO dto, Reservation entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setDateDebut(dto.getDateDebut());
        entity.setDateFin(dto.getDateFin());
        entity.setStatut(dto.getStatut());
    }
}
