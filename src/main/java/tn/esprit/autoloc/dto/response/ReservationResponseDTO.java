package tn.esprit.autoloc.dto.response;

import lombok.*;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDTO {

    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    private Long idClient;
    private String nomClient;
    private String prenomClient;
    private Long idVehicule;
    private String immatriculationVehicule;
    private String marqueVehicule;
    private String modeleVehicule;
    private Long idContrat;
}
