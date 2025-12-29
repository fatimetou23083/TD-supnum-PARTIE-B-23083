package com.example.soap_service.service;

import java.util.List;

import com.example.soap_service.model.Server;

public interface ServerService {
    Server createServer(Server server);
    List<Server> listServers();
    Server renameServer(Long serverId, String newName);
    Boolean getServerStatus(Long serverId);
    Server startServer(Long serverId);
    Server stopServer(Long serverId);
    void deleteServer(Long serverId);
}