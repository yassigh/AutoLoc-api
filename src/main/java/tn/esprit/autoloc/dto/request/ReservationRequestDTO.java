package tn.esprit.autoloc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @NotNull(message = "Le statut de la réservation est obligatoire")
    private StatutReservation statut;

    private Long idClient;
    private Long idVehicule;
    private Long idContrat;
}
