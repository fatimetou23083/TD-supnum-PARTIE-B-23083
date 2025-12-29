package com.example.consumer_service.client;

import com.example.consumer_service.dto.CreateServerRequestDTO;
import com.example.consumer_service.dto.DeleteServerResponseDTO;
import com.example.consumer_service.dto.ServerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MiddleServiceClient {

    private final RestTemplate restTemplate;
    
    @Value("${middle.service.url}")
    private String middleServiceBaseUrl;

    @Autowired
    public MiddleServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ServerDTO createServer(CreateServerRequestDTO request) {
        try {
            HttpEntity<CreateServerRequestDTO> entity = new HttpEntity<>(request);
            ResponseEntity<ServerDTO> response = restTemplate.exchange(
                    middleServiceBaseUrl,
                    HttpMethod.POST,
                    entity,
                    ServerDTO.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erreur lors de la création du serveur: " + e.getMessage(), e);
        }
    }

    public List<ServerDTO> listServers() {
        try {
            ResponseEntity<List<ServerDTO>> response = restTemplate.exchange(
                    middleServiceBaseUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ServerDTO>>() {}
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erreur lors de la récupération de la liste des serveurs: " + e.getMessage(), e);
        }
    }

    public ServerDTO renameServer(Long id, String newName) {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("newName", newName);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody);
            ResponseEntity<ServerDTO> response = restTemplate.exchange(
                    middleServiceBaseUrl + "/" + id + "/rename",
                    HttpMethod.PUT,
                    entity,
                    ServerDTO.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erreur lors du renommage du serveur: " + e.getMessage(), e);
        }
    }

    public Map<String, Boolean> getServerStatus(Long id) {
        try {
            ResponseEntity<Map<String, Boolean>> response = restTemplate.exchange(
                    middleServiceBaseUrl + "/" + id + "/status",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Boolean>>() {}
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erreur lors de la récupération du statut du serveur: " + e.getMessage(), e);
        }
    }

    public ServerDTO startServer(Long id) {
        try {
            ResponseEntity<ServerDTO> response = restTemplate.exchange(
                    middleServiceBaseUrl + "/" + id + "/start",
                    HttpMethod.PUT,
                    null,
                    ServerDTO.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erreur lors du démarrage du serveur: " + e.getMessage(), e);
        }
    }

    public ServerDTO stopServer(Long id) {
        try {
            ResponseEntity<ServerDTO> response = restTemplate.exchange(
                    middleServiceBaseUrl + "/" + id + "/stop",
                    HttpMethod.PUT,
                    null,
                    ServerDTO.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erreur lors de l'arrêt du serveur: " + e.getMessage(), e);
        }
    }

    public DeleteServerResponseDTO deleteServer(Long id) {
        try {
            ResponseEntity<DeleteServerResponseDTO> response = restTemplate.exchange(
                    middleServiceBaseUrl + "/" + id,
                    HttpMethod.DELETE,
                    null,
                    DeleteServerResponseDTO.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erreur lors de la suppression du serveur: " + e.getMessage(), e);
        }
    }
}
