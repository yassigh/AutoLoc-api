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
import tn.esprit.autoloc.dto.request.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.response.MaintenanceResponseDTO;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenances", description = "Gestion des opérations de maintenance des véhicules")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Créer une opération de maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides (ex: date de fin antérieure à date de début)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponseDTO create(@Valid @RequestBody MaintenanceRequestDTO dto) {
        return maintenanceService.create(dto);
    }

    @Operation(summary = "Consulter une opération de maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance trouvée"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @GetMapping("/{id}")
    public MaintenanceResponseDTO findById(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Lister toutes les opérations de maintenance")
    @ApiResponse(responseCode = "200", description = "Liste des opérations de maintenance récupérée avec succès")
    @GetMapping
    public List<MaintenanceResponseDTO> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Modifier une opération de maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance modifiée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @PutMapping("/{id}")
    public MaintenanceResponseDTO update(
            @Parameter(description = "Identifiant de la maintenance") @PathVariable Long id,
            @Valid @RequestBody MaintenanceRequestDTO dto) {
        return maintenanceService.update(id, dto);
    }

    @Operation(summary = "Supprimer une opération de maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
