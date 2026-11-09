package tn.esprit.autoloc.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratRequestDTO {

    @NotNull(message = "La date de signature est obligatoire")
    private LocalDate dateSignature;

    @NotNull(message = "Le montant total est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le montant total ne peut pas être négatif")
    private BigDecimal montantTotal;

    private boolean valide;

    private Long idReservation;

    @Valid
    private List<PaiementRequestDTO> paiements;
}
