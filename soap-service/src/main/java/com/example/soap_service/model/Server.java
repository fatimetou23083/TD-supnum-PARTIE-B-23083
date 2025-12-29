package com.example.soap_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "ip_address", nullable = false, unique = true)
    private String ipAddress;
    
    @Column(nullable = false)
    private Boolean status = false;

    // Constructeur personnalisé
    public Server(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.status = false;
    }
}