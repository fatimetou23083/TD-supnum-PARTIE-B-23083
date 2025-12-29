package com.example.consumer_service.service;

import com.example.consumer_service.client.MiddleServiceClient;
import com.example.consumer_service.dto.CreateServerRequestDTO;
import com.example.consumer_service.dto.DeleteServerResponseDTO;
import com.example.consumer_service.dto.ServerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServerManagementService {

    private final MiddleServiceClient middleServiceClient;

    @Autowired
    public ServerManagementService(MiddleServiceClient middleServiceClient) {
        this.middleServiceClient = middleServiceClient;
    }

    public ServerDTO createServer(CreateServerRequestDTO request) {
        return middleServiceClient.createServer(request);
    }

    public List<ServerDTO> listAllServers() {
        return middleServiceClient.listServers();
    }

    public ServerDTO renameServer(Long id, String newName) {
        return middleServiceClient.renameServer(id, newName);
    }

    public Map<String, Boolean> getServerStatus(Long id) {
        return middleServiceClient.getServerStatus(id);
    }

    public ServerDTO startServer(Long id) {
        return middleServiceClient.startServer(id);
    }

    public ServerDTO stopServer(Long id) {
        return middleServiceClient.stopServer(id);
    }

    public DeleteServerResponseDTO deleteServer(Long id) {
        return middleServiceClient.deleteServer(id);
    }
}
