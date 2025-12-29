package com.example.soap_service.exception;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class SoapFaultExceptionResolver extends SoapFaultMappingExceptionResolver {

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed ", ex);
        
        try {
            SoapFaultDetail detail = fault.addFaultDetail();
            QName faultElementName;
            
            if (ex instanceof ResourceNotFoundException) {
                faultElementName = new QName("http://example.com/soap/servers", "ResourceNotFound");
            } else if (ex instanceof IllegalArgumentException) {
                faultElementName = new QName("http://example.com/soap/servers", "ValidationError");
            } else if (ex instanceof IllegalStateException) {
                faultElementName = new QName("http://example.com/soap/servers", "BusinessRuleViolation");
            } else {
                return; // Pas de détail pour les autres exceptions
            }
            
            detail.addFaultDetailElement(faultElementName).addText(ex.getMessage());
        } catch (Exception e) {
            logger.error("Error adding fault detail", e);
        }
    }
}

