package com.example.soap_service.service;

import com.example.soap_service.exception.ResourceNotFoundException;
import com.example.soap_service.model.Server;
import com.example.soap_service.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }
    
    @Override
    @Transactional
    public Server createServer(Server server) {
        // Validation
        if (server.getName() == null || server.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du serveur est obligatoire");
        }
        if (server.getIpAddress() == null || server.getIpAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse IP est obligatoire");
        }
        
        // Vérifier si l'IP existe déjà
        if (serverRepository.existsByIpAddress(server.getIpAddress())) {
            throw new IllegalArgumentException("Un serveur avec cette adresse IP existe déjà");
        }

        server.setStatus(false);
        return serverRepository.save(server);
    }
    
    @Override
    public List<Server> listServers() {
        return serverRepository.findAll();
    }
    
    @Override
    @Transactional
    public Server renameServer(Long serverId, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nouveau nom ne peut pas être vide");
        }
        
        Server server = findServer(serverId);
        server.setName(newName.trim());
        return serverRepository.save(server);
    }
    
    @Override
    public Boolean getServerStatus(Long serverId) {
        return findServer(serverId).getStatus();
    }
    
    @Override
    @Transactional
    public Server startServer(Long serverId) {
        Server server = findServer(serverId);
        server.setStatus(true);
        return serverRepository.save(server);
    }
    
    @Override
    @Transactional
    public Server stopServer(Long serverId) {
        Server server = findServer(serverId);
        server.setStatus(false);
        return serverRepository.save(server);
    }
    
    @Override
    @Transactional
    public void deleteServer(Long serverId) {
        Server server = findServer(serverId);
        
        if (Boolean.TRUE.equals(server.getStatus())) {
            throw new IllegalStateException("Impossible de supprimer un serveur en cours d'exécution");
        }

        serverRepository.deleteById(serverId);
    }

    private Server findServer(Long serverId) {
        return serverRepository.findById(serverId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Serveur introuvable avec l'identifiant " + serverId));
    }
}