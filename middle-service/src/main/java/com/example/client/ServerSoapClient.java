package com.example.client;


import com.example.generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Component
public class ServerSoapClient {

    private static final String NAMESPACE_URI = "http://example.com/soap/servers";
    
    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    public ServerSoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public Server createServer(String name, String ipAddress) {
        CreateServerRequest request = new CreateServerRequest();
        request.setName(name);
        request.setIpAddress(ipAddress);
        
        CreateServerResponse response = (CreateServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServer();
    }

    public List<Server> listServers() {
        ListServersRequest request = new ListServersRequest();
        
        ListServersResponse response = (ListServersResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServers();
    }

    public Server renameServer(Long id, String newName) {
        RenameServerRequest request = new RenameServerRequest();
        request.setId(id);
        request.setNewName(newName);
        
        RenameServerResponse response = (RenameServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServer();
    }

    public Boolean getServerStatus(Long id) {
        GetServerStatusRequest request = new GetServerStatusRequest();
        request.setId(id);
        
        GetServerStatusResponse response = (GetServerStatusResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.isStatus();
    }

    public Server startServer(Long id) {
        StartServerRequest request = new StartServerRequest();
        request.setId(id);
        
        StartServerResponse response = (StartServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServer();
    }

    public Server stopServer(Long id) {
        StopServerRequest request = new StopServerRequest();
        request.setId(id);
        
        StopServerResponse response = (StopServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServer();
    }

    public DeleteServerResponse deleteServer(Long id) {
        DeleteServerRequest request = new DeleteServerRequest();
        request.setId(id);
        
        DeleteServerResponse response = (DeleteServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response;
    }
}
