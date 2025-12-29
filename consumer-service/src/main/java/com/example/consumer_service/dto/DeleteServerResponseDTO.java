package com.example.consumer_service.dto;

public class DeleteServerResponseDTO {
    private Boolean success;
    private String message;

    public DeleteServerResponseDTO() {}

    public DeleteServerResponseDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
