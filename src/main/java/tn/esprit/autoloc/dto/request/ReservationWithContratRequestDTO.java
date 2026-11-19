package tn.esprit.autoloc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.esprit.autoloc.domain.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationWithContratRequestDTO {

    // Informations Réservation
    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @NotNull(message = "Le statut de réservation est obligatoire")
    private StatutReservation statut;

    private Long idClient;
    private Long idVehicule;

    // Informations Contrat
    @NotNull(message = "La date de signature est obligatoire")
    private LocalDate dateSignature;

    @NotNull(message = "Le montant total est obligatoire")
    private BigDecimal montantTotal;

    private boolean valide;
}