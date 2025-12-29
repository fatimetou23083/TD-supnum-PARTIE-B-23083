package com.example.consumer_service.controller;

import com.example.consumer_service.dto.CreateServerRequestDTO;
import com.example.consumer_service.dto.DeleteServerResponseDTO;
import com.example.consumer_service.dto.ServerDTO;
import com.example.consumer_service.service.ServerManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consumer/servers")
@Tag(name = "Consumer Server Management", description = "API REST pour consommer les services de gestion des serveurs")
public class ConsumerController {

    private final ServerManagementService serverManagementService;

    @Autowired
    public ConsumerController(ServerManagementService serverManagementService) {
        this.serverManagementService = serverManagementService;
    }

    @PostMapping
    @Operation(summary = "Créer un serveur", description = "Crée un nouveau serveur via le middle-service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serveur créé avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "400", description = "Requête invalide"),
        @ApiResponse(responseCode = "500", description = "Erreur lors de la communication avec le middle-service")
    })
    public ResponseEntity<ServerDTO> createServer(
            @Parameter(description = "Données du serveur à créer", required = true)
            @Valid @RequestBody CreateServerRequestDTO request) {
        ServerDTO server = serverManagementService.createServer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(server);
    }

    @GetMapping
    @Operation(summary = "Lister tous les serveurs", description = "Récupère la liste de tous les serveurs via le middle-service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des serveurs récupérée avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erreur lors de la communication avec le middle-service")
    })
    public ResponseEntity<List<ServerDTO>> listServers() {
        List<ServerDTO> servers = serverManagementService.listAllServers();
        return ResponseEntity.ok(servers);
    }

    @PutMapping("/{id}/rename")
    @Operation(summary = "Renommer un serveur", description = "Renomme un serveur existant via le middle-service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur renommé avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur lors de la communication avec le middle-service")
    })
    public ResponseEntity<ServerDTO> renameServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id,
            @Parameter(description = "Nouveau nom du serveur", required = true) @RequestBody Map<String, String> request) {
        String newName = request.get("newName");
        if (newName == null || newName.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ServerDTO server = serverManagementService.renameServer(id, newName);
        return ResponseEntity.ok(server);
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Obtenir le statut d'un serveur", description = "Récupère le statut (actif/inactif) d'un serveur via le middle-service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statut récupéré avec succès"),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur lors de la communication avec le middle-service")
    })
    public ResponseEntity<Map<String, Boolean>> getServerStatus(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        Map<String, Boolean> status = serverManagementService.getServerStatus(id);
        return ResponseEntity.ok(status);
    }

    @PutMapping("/{id}/start")
    @Operation(summary = "Démarrer un serveur", description = "Démarre un serveur (met son statut à true) via le middle-service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur démarré avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur lors de la communication avec le middle-service")
    })
    public ResponseEntity<ServerDTO> startServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        ServerDTO server = serverManagementService.startServer(id);
        return ResponseEntity.ok(server);
    }

    @PutMapping("/{id}/stop")
    @Operation(summary = "Arrêter un serveur", description = "Arrête un serveur (met son statut à false) via le middle-service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur arrêté avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur lors de la communication avec le middle-service")
    })
    public ResponseEntity<ServerDTO> stopServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        ServerDTO server = serverManagementService.stopServer(id);
        return ResponseEntity.ok(server);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un serveur", description = "Supprime un serveur via le middle-service (un serveur en cours d'exécution ne peut pas être supprimé)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur supprimé avec succès",
            content = @Content(schema = @Schema(implementation = DeleteServerResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Impossible de supprimer (serveur en cours d'exécution)"),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur lors de la communication avec le middle-service")
    })
    public ResponseEntity<DeleteServerResponseDTO> deleteServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        DeleteServerResponseDTO response = serverManagementService.deleteServer(id);
        return ResponseEntity.ok(response);
    }
}
