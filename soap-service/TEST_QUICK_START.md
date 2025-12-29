# Guide de test rapide - Service SOAP

## 1. Préparer la base de données PostgreSQL

Si PostgreSQL n'est pas installé, vous pouvez utiliser Docker :

```bash
docker run --name postgres-soap -e POSTGRES_PASSWORD=password -e POSTGRES_DB=server_management_soap -p 5432:5432 -d postgres:13
```

Ou créer manuellement la base de données :
```sql
CREATE DATABASE server_management_soap;
```

## 2. Démarrer le service

```bash
cd soap-service
mvn spring-boot:run
```

Attendez le message : `Started SoapServiceApplication`

## 3. Vérifier que le service fonctionne

Ouvrez votre navigateur et allez à :
```
http://localhost:8081/ws/servers.wsdl
```

Vous devriez voir le fichier WSDL XML.

## 4. Tester avec SoapUI (Exemple rapide)

### Exemple 1 : Créer un serveur

**Requête SOAP :**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:CreateServerRequest>
         <ser:name>Serveur Web Production</ser:name>
         <ser:ipAddress>192.168.1.100</ser:ipAddress>
      </ser:CreateServerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Réponse attendue :**
```xml
<soap:Envelope>
   <soap:Body>
      <CreateServerResponse>
         <server>
            <id>1</id>
            <name>Serveur Web Production</name>
            <ipAddress>192.168.1.100</ipAddress>
            <status>false</status>
         </server>
      </CreateServerResponse>
   </soap:Body>
</soap:Envelope>
```

### Exemple 2 : Lister tous les serveurs

**Requête SOAP :**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:ListServersRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

### Exemple 3 : Démarrer un serveur

**Requête SOAP :**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:StartServerRequest>
         <ser:id>1</ser:id>
      </ser:StartServerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

## 5. Test de cas d'erreur

### Tester la suppression d'un serveur en cours d'exécution

1. Créer un serveur (ID 1)
2. Démarrer le serveur (ID 1)
3. Essayer de supprimer le serveur (ID 1) → Devrait retourner une SOAP Fault avec "BusinessRuleViolation"

**Réponse d'erreur attendue :**
```xml
<soap:Fault>
   <faultcode>soap:Server</faultcode>
   <faultstring>Impossible de supprimer un serveur en cours d'exécution</faultstring>
   <detail>
      <BusinessRuleViolation xmlns="http://example.com/soap/servers">
         Impossible de supprimer un serveur en cours d'exécution
      </BusinessRuleViolation>
   </detail>
</soap:Fault>
```

## Vérification de la base de données

Vous pouvez vérifier les données dans PostgreSQL :

```sql
-- Se connecter à la base
\c server_management_soap

-- Lister les serveurs
SELECT * FROM servers;

-- Vérifier le statut
SELECT id, name, ip_address, status FROM servers;
```

