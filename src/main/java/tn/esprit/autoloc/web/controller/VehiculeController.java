package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.request.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.response.VehiculeResponseDTO;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Tag(name = "Véhicules", description = "Gestion du parc de véhicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Créer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Véhicule créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides (ex: tarif journalier négatif)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponseDTO create(@Valid @RequestBody VehiculeRequestDTO dto) {
        return vehiculeService.create(dto);
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @GetMapping("/{id}")
    public VehiculeResponseDTO findById(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Lister tous les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules récupérée avec succès")
    @GetMapping
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @PutMapping("/{id}")
    public VehiculeResponseDTO update(
            @Parameter(description = "Identifiant du véhicule") @PathVariable Long id,
            @Valid @RequestBody VehiculeRequestDTO dto) {
        return vehiculeService.update(id, dto);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Véhicule supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }

    @Operation(summary = "Affecter un véhicule à un équipement")
    @PutMapping("/affecterVehiculeAEquipement/{idVehicule}/{idEquipement}")
    public tn.esprit.autoloc.dto.response.VehiculeDTO affecterVehiculeAEquipement(
            @PathVariable Long idVehicule,
            @PathVariable Long idEquipement) {
        return vehiculeService.affecterVehiculeAEquipement(idVehicule, idEquipement);
    }

    @Operation(summary = "Affecter une maintenance à un véhicule")
    @PutMapping("/affecterMaintenanceAvehicule/{description}/{immatriculation}")
    public tn.esprit.autoloc.dto.response.VehiculeDTO affecterMaintenanceAvehicule(
            @PathVariable String description,
            @PathVariable String immatriculation) {
        return vehiculeService.affecterMaintenanceAvehicule(description, immatriculation);
    }
}
