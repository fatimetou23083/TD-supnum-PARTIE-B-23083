# Contrats de Communication - Architecture SOA_TO_REST

## Vue d'ensemble

Ce document définit les contrats de communication entre les trois services de l'architecture :
1. **Service SOAP** : Service de base exposant les opérations via SOAP/XML
2. **Middle Service** : Service REST qui fait le pont entre REST et SOAP
3. **Consumer Service** : Service REST consommateur qui expose l'API aux clients

## Architecture de communication

```
Client → Consumer Service (REST/JSON) → Middle Service (REST/JSON) → SOAP Service (SOAP/XML)
```

---

# 1. Service SOAP - Contrat de Communication

## Informations générales

- **URL du service** : `http://localhost:8080/ws`
- **WSDL** : `http://localhost:8080/ws/servers.wsdl`
- **Namespace** : `http://example.com/soap/servers`
- **Format** : SOAP/XML
- **Port** : 8080

## Opérations disponibles

### 1.1 CreateServer

**Endpoint** : `http://localhost:8080/ws`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: text/xml`
- `SOAPAction: "http://example.com/soap/servers/CreateServerRequest"`

**Paramètres d'entrée** (XML) :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:CreateServerRequest>
         <ser:name>Serveur Web 1</ser:name>
         <ser:ipAddress>192.168.1.10</ser:ipAddress>
      </ser:CreateServerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Format de réponse** (XML) :
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <CreateServerResponse xmlns="http://example.com/soap/servers">
         <server>
            <id>1</id>
            <name>Serveur Web 1</name>
            <ipAddress>192.168.1.10</ipAddress>
            <status>false</status>
         </server>
      </CreateServerResponse>
   </soap:Body>
</soap:Envelope>
```

---

### 1.2 ListServers

**Endpoint** : `http://localhost:8080/ws`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: text/xml`
- `SOAPAction: "http://example.com/soap/servers/ListServersRequest"`

**Paramètres d'entrée** (XML) :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:ListServersRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

**Format de réponse** (XML) :
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ListServersResponse xmlns="http://example.com/soap/servers">
         <servers>
            <id>1</id>
            <name>Serveur Web 1</name>
            <ipAddress>192.168.1.10</ipAddress>
            <status>true</status>
         </servers>
         <servers>
            <id>2</id>
            <name>Serveur DB 1</name>
            <ipAddress>192.168.1.20</ipAddress>
            <status>false</status>
         </servers>
      </ListServersResponse>
   </soap:Body>
</soap:Envelope>
```

---

### 1.3 RenameServer

**Endpoint** : `http://localhost:8080/ws`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: text/xml`
- `SOAPAction: "http://example.com/soap/servers/RenameServerRequest"`

**Paramètres d'entrée** (XML) :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:RenameServerRequest>
         <ser:id>1</ser:id>
         <ser:newName>Serveur Web Principal</ser:newName>
      </ser:RenameServerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Format de réponse** (XML) :
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <RenameServerResponse xmlns="http://example.com/soap/servers">
         <server>
            <id>1</id>
            <name>Serveur Web Principal</name>
            <ipAddress>192.168.1.10</ipAddress>
            <status>true</status>
         </server>
      </RenameServerResponse>
   </soap:Body>
</soap:Envelope>
```

---

### 1.4 GetServerStatus

**Endpoint** : `http://localhost:8080/ws`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: text/xml`
- `SOAPAction: "http://example.com/soap/servers/GetServerStatusRequest"`

**Paramètres d'entrée** (XML) :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:GetServerStatusRequest>
         <ser:id>1</ser:id>
      </ser:GetServerStatusRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Format de réponse** (XML) :
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <GetServerStatusResponse xmlns="http://example.com/soap/servers">
         <status>true</status>
      </GetServerStatusResponse>
   </soap:Body>
</soap:Envelope>
```

---

### 1.5 StartServer

**Endpoint** : `http://localhost:8080/ws`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: text/xml`
- `SOAPAction: "http://example.com/soap/servers/StartServerRequest"`

**Paramètres d'entrée** (XML) :
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

**Format de réponse** (XML) :
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <StartServerResponse xmlns="http://example.com/soap/servers">
         <server>
            <id>1</id>
            <name>Serveur Web 1</name>
            <ipAddress>192.168.1.10</ipAddress>
            <status>true</status>
         </server>
      </StartServerResponse>
   </soap:Body>
</soap:Envelope>
```

---

### 1.6 StopServer

**Endpoint** : `http://localhost:8080/ws`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: text/xml`
- `SOAPAction: "http://example.com/soap/servers/StopServerRequest"`

**Paramètres d'entrée** (XML) :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:StopServerRequest>
         <ser:id>1</ser:id>
      </ser:StopServerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Format de réponse** (XML) :
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <StopServerResponse xmlns="http://example.com/soap/servers">
         <server>
            <id>1</id>
            <name>Serveur Web 1</name>
            <ipAddress>192.168.1.10</ipAddress>
            <status>false</status>
         </server>
      </StopServerResponse>
   </soap:Body>
</soap:Envelope>
```

---

### 1.7 DeleteServer

**Endpoint** : `http://localhost:8080/ws`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: text/xml`
- `SOAPAction: "http://example.com/soap/servers/DeleteServerRequest"`

**Paramètres d'entrée** (XML) :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:DeleteServerRequest>
         <ser:id>1</ser:id>
      </ser:DeleteServerRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**Format de réponse** (XML) :
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <DeleteServerResponse xmlns="http://example.com/soap/servers">
         <success>true</success>
         <message>Serveur supprimé avec succès</message>
      </DeleteServerResponse>
   </soap:Body>
</soap:Envelope>
```

**Réponse en cas d'échec** (XML) :
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

---

# 2. Middle Service - Contrat de Communication

## Informations générales

- **Base URL** : `http://localhost:8081`
- **Format** : REST/JSON
- **Port** : 8081
- **Documentation Swagger** : `http://localhost:8081/swagger-ui.html`

## Endpoints REST

### 2.1 Créer un serveur

**Endpoint** : `POST /api/servers`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: application/json`

**Paramètres d'entrée** (JSON) :
```json
{
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10"
}
```

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": false
}
```

**Codes de réponse** :
- `201 Created` : Serveur créé avec succès
- `400 Bad Request` : Requête invalide

---

### 2.2 Lister tous les serveurs

**Endpoint** : `GET /api/servers`

**Méthode HTTP** : `GET`

**Headers requis** : Aucun

**Paramètres d'entrée** : Aucun

**Format de réponse** (JSON) :
```json
[
  {
    "id": 1,
    "name": "Serveur Web 1",
    "ipAddress": "192.168.1.10",
    "status": true
  },
  {
    "id": 2,
    "name": "Serveur DB 1",
    "ipAddress": "192.168.1.20",
    "status": false
  }
]
```

**Codes de réponse** :
- `200 OK` : Liste récupérée avec succès
- `500 Internal Server Error` : Erreur interne du serveur

---

### 2.3 Renommer un serveur

**Endpoint** : `PUT /api/servers/{id}/rename`

**Méthode HTTP** : `PUT`

**Headers requis** :
- `Content-Type: application/json`

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à renommer

**Body** (JSON) :
```json
{
  "newName": "Serveur Web Principal"
}
```

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web Principal",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

**Codes de réponse** :
- `200 OK` : Serveur renommé avec succès
- `404 Not Found` : Serveur non trouvé

---

### 2.4 Obtenir le statut d'un serveur

**Endpoint** : `GET /api/servers/{id}/status`

**Méthode HTTP** : `GET`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur

**Format de réponse** (JSON) :
```json
{
  "status": true
}
```

**Codes de réponse** :
- `200 OK` : Statut récupéré avec succès
- `404 Not Found` : Serveur non trouvé

---

### 2.5 Démarrer un serveur

**Endpoint** : `PUT /api/servers/{id}/start`

**Méthode HTTP** : `PUT`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à démarrer

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

**Codes de réponse** :
- `200 OK` : Serveur démarré avec succès
- `404 Not Found` : Serveur non trouvé

---

### 2.6 Arrêter un serveur

**Endpoint** : `PUT /api/servers/{id}/stop`

**Méthode HTTP** : `PUT`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à arrêter

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": false
}
```

**Codes de réponse** :
- `200 OK` : Serveur arrêté avec succès
- `404 Not Found` : Serveur non trouvé

---

### 2.7 Supprimer un serveur

**Endpoint** : `DELETE /api/servers/{id}`

**Méthode HTTP** : `DELETE`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à supprimer

**Format de réponse** (JSON) :
```json
{
  "success": true,
  "message": "Serveur supprimé avec succès"
}
```

**Réponse en cas d'échec** (JSON) :
```json
{
  "success": false,
  "message": "Impossible de supprimer un serveur en cours d'exécution"
}
```

**Codes de réponse** :
- `200 OK` : Serveur supprimé avec succès
- `400 Bad Request` : Impossible de supprimer (serveur en cours d'exécution)
- `404 Not Found` : Serveur non trouvé

---

# 3. Consumer Service - Contrat de Communication

## Informations générales

- **Base URL** : `http://localhost:8082`
- **Format** : REST/JSON
- **Port** : 8082
- **Documentation Swagger** : `http://localhost:8082/swagger-ui.html`

## Endpoints REST

### 3.1 Créer un serveur

**Endpoint** : `POST /api/consumer/servers`

**Méthode HTTP** : `POST`

**Headers requis** :
- `Content-Type: application/json`

**Paramètres d'entrée** (JSON) :
```json
{
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10"
}
```

**Validation** :
- `name` : Obligatoire, ne peut pas être vide
- `ipAddress` : Obligatoire, doit être au format IP valide (ex: 192.168.1.10)

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": false
}
```

**Codes de réponse** :
- `201 Created` : Serveur créé avec succès
- `400 Bad Request` : Requête invalide (validation échouée)
- `503 Service Unavailable` : Erreur de communication avec le middle-service

---

### 3.2 Lister tous les serveurs

**Endpoint** : `GET /api/consumer/servers`

**Méthode HTTP** : `GET`

**Headers requis** : Aucun

**Paramètres d'entrée** : Aucun

**Format de réponse** (JSON) :
```json
[
  {
    "id": 1,
    "name": "Serveur Web 1",
    "ipAddress": "192.168.1.10",
    "status": true
  },
  {
    "id": 2,
    "name": "Serveur DB 1",
    "ipAddress": "192.168.1.20",
    "status": false
  }
]
```

**Codes de réponse** :
- `200 OK` : Liste récupérée avec succès
- `503 Service Unavailable` : Erreur de communication avec le middle-service

---

### 3.3 Renommer un serveur

**Endpoint** : `PUT /api/consumer/servers/{id}/rename`

**Méthode HTTP** : `PUT`

**Headers requis** :
- `Content-Type: application/json`

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à renommer

**Body** (JSON) :
```json
{
  "newName": "Serveur Web Principal"
}
```

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web Principal",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

**Codes de réponse** :
- `200 OK` : Serveur renommé avec succès
- `400 Bad Request` : Requête invalide (newName manquant ou vide)
- `404 Not Found` : Serveur non trouvé
- `503 Service Unavailable` : Erreur de communication avec le middle-service

---

### 3.4 Obtenir le statut d'un serveur

**Endpoint** : `GET /api/consumer/servers/{id}/status`

**Méthode HTTP** : `GET`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur

**Format de réponse** (JSON) :
```json
{
  "status": true
}
```

**Codes de réponse** :
- `200 OK` : Statut récupéré avec succès
- `404 Not Found` : Serveur non trouvé
- `503 Service Unavailable` : Erreur de communication avec le middle-service

---

### 3.5 Démarrer un serveur

**Endpoint** : `PUT /api/consumer/servers/{id}/start`

**Méthode HTTP** : `PUT`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à démarrer

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

**Codes de réponse** :
- `200 OK` : Serveur démarré avec succès
- `404 Not Found` : Serveur non trouvé
- `503 Service Unavailable` : Erreur de communication avec le middle-service

---

### 3.6 Arrêter un serveur

**Endpoint** : `PUT /api/consumer/servers/{id}/stop`

**Méthode HTTP** : `PUT`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à arrêter

**Format de réponse** (JSON) :
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": false
}
```

**Codes de réponse** :
- `200 OK` : Serveur arrêté avec succès
- `404 Not Found` : Serveur non trouvé
- `503 Service Unavailable` : Erreur de communication avec le middle-service

---

### 3.7 Supprimer un serveur

**Endpoint** : `DELETE /api/consumer/servers/{id}`

**Méthode HTTP** : `DELETE`

**Headers requis** : Aucun

**Paramètres d'entrée** :
- `id` (path parameter) : ID du serveur à supprimer

**Format de réponse** (JSON) :
```json
{
  "success": true,
  "message": "Serveur supprimé avec succès"
}
```

**Réponse en cas d'échec** (JSON) :
```json
{
  "success": false,
  "message": "Impossible de supprimer un serveur en cours d'exécution"
}
```

**Codes de réponse** :
- `200 OK` : Serveur supprimé avec succès
- `400 Bad Request` : Impossible de supprimer (serveur en cours d'exécution)
- `404 Not Found` : Serveur non trouvé
- `503 Service Unavailable` : Erreur de communication avec le middle-service

---

# 4. Formats de données communs

## 4.1 ServerDTO (JSON)

Structure utilisée par les services REST (Middle Service et Consumer Service) :

```json
{
  "id": 1,
  "name": "Nom du serveur",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

**Propriétés** :
- `id` (Long) : Identifiant unique du serveur
- `name` (String) : Nom du serveur
- `ipAddress` (String) : Adresse IP du serveur
- `status` (Boolean) : Statut du serveur (true = démarré, false = arrêté)

---

## 4.2 CreateServerRequestDTO (JSON)

Structure pour créer un serveur :

```json
{
  "name": "Nom du serveur",
  "ipAddress": "192.168.1.10"
}
```

**Propriétés** :
- `name` (String, obligatoire) : Nom du serveur
- `ipAddress` (String, obligatoire) : Adresse IP du serveur (format IP valide)

---

## 4.3 DeleteServerResponseDTO (JSON)

Structure de réponse pour la suppression :

```json
{
  "success": true,
  "message": "Message de confirmation"
}
```

**Propriétés** :
- `success` (Boolean) : Indique si l'opération a réussi
- `message` (String) : Message de confirmation ou d'erreur

---

## 4.4 Server (XML - SOAP)

Structure utilisée par le service SOAP :

```xml
<server xmlns="http://example.com/soap/servers">
   <id>1</id>
   <name>Nom du serveur</name>
   <ipAddress>192.168.1.10</ipAddress>
   <status>true</status>
</server>
```

---

# 5. Gestion des erreurs

## 5.1 Service SOAP

Les erreurs sont retournées sous forme de **SOAP Fault** :

```xml
<soap:Fault>
   <faultcode>soap:Server</faultcode>
   <faultstring>Message d'erreur</faultstring>
   <detail>
      <BusinessRuleViolation xmlns="http://example.com/soap/servers">
         Détails de l'erreur
      </BusinessRuleViolation>
   </detail>
</soap:Fault>
```

---

## 5.2 Middle Service et Consumer Service

Les erreurs sont retournées sous forme de **JSON** avec les codes HTTP appropriés :

```json
{
  "success": false,
  "message": "Description de l'erreur",
  "error": "Type d'erreur",
  "errors": {
    "fieldName": "Message d'erreur de validation"
  }
}
```

**Codes HTTP** :
- `400 Bad Request` : Erreur de validation ou requête invalide
- `404 Not Found` : Ressource non trouvée
- `500 Internal Server Error` : Erreur interne du serveur
- `503 Service Unavailable` : Service non disponible (communication avec un autre service échouée)

---

# 6. Flux de communication

## 6.1 Exemple : Créer un serveur

### Flux complet

1. **Client** → **Consumer Service** (REST/JSON)
   ```
   POST http://localhost:8082/api/consumer/servers
   Content-Type: application/json
   {
     "name": "Serveur Web 1",
     "ipAddress": "192.168.1.10"
   }
   ```

2. **Consumer Service** → **Middle Service** (REST/JSON)
   ```
   POST http://localhost:8081/api/servers
   Content-Type: application/json
   {
     "name": "Serveur Web 1",
     "ipAddress": "192.168.1.10"
   }
   ```

3. **Middle Service** → **SOAP Service** (SOAP/XML)
   ```xml
   POST http://localhost:8080/ws
   Content-Type: text/xml
   SOAPAction: "http://example.com/soap/servers/CreateServerRequest"
   
   <soapenv:Envelope>
      <soapenv:Body>
         <ser:CreateServerRequest>
            <ser:name>Serveur Web 1</ser:name>
            <ser:ipAddress>192.168.1.10</ser:ipAddress>
         </ser:CreateServerRequest>
      </soapenv:Body>
   </soapenv:Envelope>
   ```

4. **SOAP Service** → **Middle Service** (SOAP/XML)
   ```xml
   <soap:Envelope>
      <soap:Body>
         <CreateServerResponse>
            <server>
               <id>1</id>
               <name>Serveur Web 1</name>
               <ipAddress>192.168.1.10</ipAddress>
               <status>false</status>
            </server>
         </CreateServerResponse>
      </soap:Body>
   </soap:Envelope>
   ```

5. **Middle Service** → **Consumer Service** (REST/JSON)
   ```json
   {
     "id": 1,
     "name": "Serveur Web 1",
     "ipAddress": "192.168.1.10",
     "status": false
   }
   ```

6. **Consumer Service** → **Client** (REST/JSON)
   ```json
   {
     "id": 1,
     "name": "Serveur Web 1",
     "ipAddress": "192.168.1.10",
     "status": false
   }
   ```

---

# 7. Résumé des contrats

## 7.1 Service SOAP

| Opération | Endpoint | Méthode | Format Entrée | Format Sortie |
|-----------|----------|---------|---------------|---------------|
| CreateServer | `/ws` | POST | XML (SOAP) | XML (SOAP) |
| ListServers | `/ws` | POST | XML (SOAP) | XML (SOAP) |
| RenameServer | `/ws` | POST | XML (SOAP) | XML (SOAP) |
| GetServerStatus | `/ws` | POST | XML (SOAP) | XML (SOAP) |
| StartServer | `/ws` | POST | XML (SOAP) | XML (SOAP) |
| StopServer | `/ws` | POST | XML (SOAP) | XML (SOAP) |
| DeleteServer | `/ws` | POST | XML (SOAP) | XML (SOAP) |

---

## 7.2 Middle Service

| Opération | Endpoint | Méthode | Format Entrée | Format Sortie |
|-----------|----------|---------|---------------|---------------|
| Créer serveur | `/api/servers` | POST | JSON | JSON |
| Lister serveurs | `/api/servers` | GET | - | JSON |
| Renommer serveur | `/api/servers/{id}/rename` | PUT | JSON | JSON |
| Statut serveur | `/api/servers/{id}/status` | GET | - | JSON |
| Démarrer serveur | `/api/servers/{id}/start` | PUT | - | JSON |
| Arrêter serveur | `/api/servers/{id}/stop` | PUT | - | JSON |
| Supprimer serveur | `/api/servers/{id}` | DELETE | - | JSON |

---

## 7.3 Consumer Service

| Opération | Endpoint | Méthode | Format Entrée | Format Sortie |
|-----------|----------|---------|---------------|---------------|
| Créer serveur | `/api/consumer/servers` | POST | JSON | JSON |
| Lister serveurs | `/api/consumer/servers` | GET | - | JSON |
| Renommer serveur | `/api/consumer/servers/{id}/rename` | PUT | JSON | JSON |
| Statut serveur | `/api/consumer/servers/{id}/status` | GET | - | JSON |
| Démarrer serveur | `/api/consumer/servers/{id}/start` | PUT | - | JSON |
| Arrêter serveur | `/api/consumer/servers/{id}/stop` | PUT | - | JSON |
| Supprimer serveur | `/api/consumer/servers/{id}` | DELETE | - | JSON |

---

# 8. Notes importantes

1. **Ordre de démarrage** : Les services doivent être démarrés dans l'ordre suivant :
   - 1. SOAP Service (port 8080)
   - 2. Middle Service (port 8081)
   - 3. Consumer Service (port 8082)

2. **Formats de communication** :
   - SOAP Service : XML uniquement
   - Middle Service : JSON uniquement (convertit XML ↔ JSON)
   - Consumer Service : JSON uniquement

3. **Validation** :
   - Le Consumer Service valide les données avant de les transmettre
   - Le SOAP Service valide également les données côté serveur

4. **Gestion des erreurs** :
   - Chaque service gère ses propres erreurs et les propage de manière appropriée
   - Les erreurs de communication entre services sont gérées avec le code HTTP 503

5. **Documentation Swagger** :
   - Middle Service : `http://localhost:8081/swagger-ui.html`
   - Consumer Service : `http://localhost:8082/swagger-ui.html`

---

# 9. Exemples d'utilisation

## 9.1 Exemple avec cURL - Consumer Service

```bash
# Créer un serveur
curl -X POST http://localhost:8082/api/consumer/servers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Serveur Web 1",
    "ipAddress": "192.168.1.10"
  }'

# Lister tous les serveurs
curl -X GET http://localhost:8082/api/consumer/servers

# Démarrer un serveur
curl -X PUT http://localhost:8082/api/consumer/servers/1/start

# Supprimer un serveur
curl -X DELETE http://localhost:8082/api/consumer/servers/1
```

---

## 9.2 Exemple avec cURL - Middle Service

```bash
# Créer un serveur
curl -X POST http://localhost:8081/api/servers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Serveur Web 1",
    "ipAddress": "192.168.1.10"
  }'
```

---

## 9.3 Exemple avec SoapUI - SOAP Service

Voir le fichier `soap-service/GUIDE_TEST_SOAPUI.md` pour des exemples détaillés d'utilisation avec SoapUI.

---

**Document créé le** : 2024  
**Version** : 1.0  
**Auteur** : Équipe de développement

