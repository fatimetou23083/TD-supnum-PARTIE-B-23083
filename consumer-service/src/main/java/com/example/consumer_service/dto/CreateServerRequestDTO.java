package com.example.consumer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateServerRequestDTO {
    
    @NotBlank(message = "Le nom du serveur est obligatoire")
    private String name;
    
    @NotBlank(message = "L'adresse IP est obligatoire")
    @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Format d'adresse IP invalide")
    private String ipAddress;

    public CreateServerRequestDTO() {}

    public CreateServerRequestDTO(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
