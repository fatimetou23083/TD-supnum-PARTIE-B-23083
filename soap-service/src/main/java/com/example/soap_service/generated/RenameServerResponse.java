//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v3.0.0 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.12.28 à 05:54:31 PM UTC 
//


package com.example.soap_service.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="server" type="{http://example.com/soap/servers}Server"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "server"
})
@XmlRootElement(name = "RenameServerResponse")
public class RenameServerResponse {

    @XmlElement(required = true)
    protected Server server;

    /**
     * Obtient la valeur de la propriété server.
     * 
     * @return
     *     possible object is
     *     {@link Server }
     *     
     */
    public Server getServer() {
        return server;
    }

    /**
     * Définit la valeur de la propriété server.
     * 
     * @param value
     *     allowed object is
     *     {@link Server }
     *     
     */
    public void setServer(Server value) {
        this.server = value;
    }

}
