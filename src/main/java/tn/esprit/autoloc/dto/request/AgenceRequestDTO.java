package tn.esprit.autoloc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgenceRequestDTO {

    @NotBlank(message = "Le nom de l'agence est obligatoire")
    @Size(max = 100, message = "Le nom de l'agence ne doit pas dépasser 100 caractères")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 50, message = "La ville ne doit pas dépasser 50 caractères")
    private String ville;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 150, message = "L'adresse ne doit pas dépasser 150 caractères")
    private String adresse;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[+0-9\\s-]{8,20}$", message = "Format de numéro de téléphone invalide")
    private String telephone;
}
