package com.example.controller;

import com.example.client.ServerSoapClient;
import com.example.dto.CreateServerRequestDTO;
import com.example.dto.DeleteServerResponseDTO;
import com.example.dto.ServerDTO;
import com.example.generated.DeleteServerResponse;
import com.example.generated.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servers")
@Tag(name = "Server Management", description = "API REST pour la gestion des serveurs (bridge SOAP)")
public class ServerController {

    private final ServerSoapClient soapClient;

    @Autowired
    public ServerController(ServerSoapClient soapClient) {
        this.soapClient = soapClient;
    }

    @PostMapping
    @Operation(summary = "Créer un serveur", description = "Crée un nouveau serveur avec un nom et une adresse IP")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serveur créé avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    public ResponseEntity<ServerDTO> createServer(
            @Parameter(description = "Données du serveur à créer", required = true)
            @RequestBody CreateServerRequestDTO request) {
        try {
            Server server = soapClient.createServer(request.getName(), request.getIpAddress());
            return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(server));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Lister tous les serveurs", description = "Récupère la liste de tous les serveurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des serveurs récupérée avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<ServerDTO>> listServers() {
        try {
            List<Server> servers = soapClient.listServers();
            List<ServerDTO> dtos = servers.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/rename")
    @Operation(summary = "Renommer un serveur", description = "Renomme un serveur existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur renommé avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé")
    })
    public ResponseEntity<ServerDTO> renameServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id,
            @Parameter(description = "Nouveau nom du serveur", required = true) @RequestBody Map<String, String> request) {
        try {
            String newName = request.get("newName");
            Server server = soapClient.renameServer(id, newName);
            return ResponseEntity.ok(mapToDTO(server));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Obtenir le statut d'un serveur", description = "Récupère le statut (actif/inactif) d'un serveur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statut récupéré avec succès"),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé")
    })
    public ResponseEntity<Map<String, Boolean>> getServerStatus(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        try {
            Boolean status = soapClient.getServerStatus(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("status", status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/start")
    @Operation(summary = "Démarrer un serveur", description = "Démarre un serveur (met son statut à true)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur démarré avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé")
    })
    public ResponseEntity<ServerDTO> startServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        try {
            Server server = soapClient.startServer(id);
            return ResponseEntity.ok(mapToDTO(server));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/stop")
    @Operation(summary = "Arrêter un serveur", description = "Arrête un serveur (met son statut à false)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur arrêté avec succès",
            content = @Content(schema = @Schema(implementation = ServerDTO.class))),
        @ApiResponse(responseCode = "404", description = "Serveur non trouvé")
    })
    public ResponseEntity<ServerDTO> stopServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        try {
            Server server = soapClient.stopServer(id);
            return ResponseEntity.ok(mapToDTO(server));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un serveur", description = "Supprime un serveur (un serveur en cours d'exécution ne peut pas être supprimé)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serveur supprimé avec succès",
            content = @Content(schema = @Schema(implementation = DeleteServerResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Impossible de supprimer (serveur en cours d'exécution)")
    })
    public ResponseEntity<DeleteServerResponseDTO> deleteServer(
            @Parameter(description = "ID du serveur", required = true) @PathVariable Long id) {
        try {
            DeleteServerResponse response = soapClient.deleteServer(id);
            DeleteServerResponseDTO dto = new DeleteServerResponseDTO(
                    response.isSuccess(),
                    response.getMessage()
            );
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private ServerDTO mapToDTO(Server server) {
        return new ServerDTO(
                server.getId(),
                server.getName(),
                server.getIpAddress(),
                server.isStatus()
        );
    }
}
