package com.example.soap_service.endpoint;

import com.example.soap_service.generated.*;
import com.example.soap_service.model.Server;
import com.example.soap_service.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ServerEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/soap/servers";

    private final ServerService serverService;

    @Autowired
    public ServerEndpoint(ServerService serverService) {
        this.serverService = serverService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateServerRequest")
    @ResponsePayload
    public CreateServerResponse createServer(@RequestPayload CreateServerRequest request) {
        Server server = new Server(request.getName(), request.getIpAddress());
        Server created = serverService.createServer(server);
        
        CreateServerResponse response = new CreateServerResponse();
        response.setServer(mapToSoapServer(created));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListServersRequest")
    @ResponsePayload
    public ListServersResponse listServers(@RequestPayload ListServersRequest request) {
        List<Server> servers = serverService.listServers();
        
        ListServersResponse response = new ListServersResponse();
        servers.forEach(s -> response.getServers().add(mapToSoapServer(s)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RenameServerRequest")
    @ResponsePayload
    public RenameServerResponse renameServer(@RequestPayload RenameServerRequest request) {
        Server server = serverService.renameServer(request.getId(), request.getNewName());
        
        RenameServerResponse response = new RenameServerResponse();
        response.setServer(mapToSoapServer(server));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetServerStatusRequest")
    @ResponsePayload
    public GetServerStatusResponse getServerStatus(@RequestPayload GetServerStatusRequest request) {
        Boolean status = serverService.getServerStatus(request.getId());
        
        GetServerStatusResponse response = new GetServerStatusResponse();
        response.setStatus(status);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StartServerRequest")
    @ResponsePayload
    public StartServerResponse startServer(@RequestPayload StartServerRequest request) {
        Server server = serverService.startServer(request.getId());
        
        StartServerResponse response = new StartServerResponse();
        response.setServer(mapToSoapServer(server));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StopServerRequest")
    @ResponsePayload
    public StopServerResponse stopServer(@RequestPayload StopServerRequest request) {
        Server server = serverService.stopServer(request.getId());
        
        StopServerResponse response = new StopServerResponse();
        response.setServer(mapToSoapServer(server));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteServerRequest")
    @ResponsePayload
    public DeleteServerResponse deleteServer(@RequestPayload DeleteServerRequest request) {
        serverService.deleteServer(request.getId());
        
        DeleteServerResponse response = new DeleteServerResponse();
        response.setSuccess(true);
        response.setMessage("Serveur supprimé avec succès");
        return response;
    }

    // Mapper l'entité JPA vers l'objet SOAP généré
    private com.example.soap_service.generated.Server mapToSoapServer(Server server) {
        com.example.soap_service.generated.Server soapServer = 
            new com.example.soap_service.generated.Server();
        soapServer.setId(server.getId());
        soapServer.setName(server.getName());
        soapServer.setIpAddress(server.getIpAddress());
        soapServer.setStatus(server.getStatus());
        return soapServer;
    }
}