package com.example.soap_service.repository;

import com.example.soap_service.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    boolean existsByIpAddress(String ipAddress);
}