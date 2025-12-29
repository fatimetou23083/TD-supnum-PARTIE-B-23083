package com.example.consumer_service.dto;

public class ServerDTO {
    private Long id;
    private String name;
    private String ipAddress;
    private Boolean status;

    public ServerDTO() {}

    public ServerDTO(Long id, String name, String ipAddress, Boolean status) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", status=" + status +
                '}';
    }
}
