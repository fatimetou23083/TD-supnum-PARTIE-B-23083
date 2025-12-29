# Documentation du Service SOAP - Gestion des Serveurs

## Vue d'ensemble

Ce service SOAP permet de gérer, superviser et surveiller un ensemble de serveurs dans un datacenter. Il expose des opérations pour créer, lister, modifier, démarrer, arrêter et supprimer des serveurs.

## Informations de base

- **URL du service**: `http://localhost:8081/ws`
- **WSDL**: `http://localhost:8081/ws/servers.wsdl`
- **Namespace**: `http://example.com/soap/servers`
- **Port Type**: `ServersPort`

## Base de données

- **Type**: PostgreSQL
- **Base de données**: `server_management_soap`
- **Port**: 5432

## Modèle de données

### Server

Un serveur est représenté par les propriétés suivantes :

- `id` (Long) : Identifiant unique du serveur (généré automatiquement)
- `name` (String) : Nom du serveur (obligatoire)
- `ipAddress` (String) : Adresse IP du serveur (obligatoire, unique)
- `status` (Boolean) : Statut du serveur (true = démarré, false = arrêté)

## Opérations disponibles

### 1. CreateServer

Crée un nouveau serveur dans le système.

**Request** (`CreateServerRequest`):
```xml
<CreateServerRequest>
    <name>Serveur Web 1</name>
    <ipAddress>192.168.1.10</ipAddress>
</CreateServerRequest>
```

**Response** (`CreateServerResponse`):
```xml
<CreateServerResponse>
    <server>
        <id>1</id>
        <name>Serveur Web 1</name>
        <ipAddress>192.168.1.10</ipAddress>
        <status>false</status>
    </server>
</CreateServerResponse>
```

**Erreurs possibles**:
- `ValidationError`: Si le nom ou l'adresse IP est vide
- `ValidationError`: Si l'adresse IP existe déjà

---

### 2. ListServers

Récupère la liste de tous les serveurs.

**Request** (`ListServersRequest`):
```xml
<ListServersRequest/>
```

**Response** (`ListServersResponse`):
```xml
<ListServersResponse>
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
```

---

### 3. RenameServer

Renomme un serveur existant.

**Request** (`RenameServerRequest`):
```xml
<RenameServerRequest>
    <id>1</id>
    <newName>Serveur Web Principal</newName>
</RenameServerRequest>
```

**Response** (`RenameServerResponse`):
```xml
<RenameServerResponse>
    <server>
        <id>1</id>
        <name>Serveur Web Principal</name>
        <ipAddress>192.168.1.10</ipAddress>
        <status>true</status>
    </server>
</RenameServerResponse>
```

**Erreurs possibles**:
- `ResourceNotFound`: Si le serveur avec l'ID spécifié n'existe pas
- `ValidationError`: Si le nouveau nom est vide

---

### 4. GetServerStatus

Récupère le statut d'un serveur (démarré ou arrêté).

**Request** (`GetServerStatusRequest`):
```xml
<GetServerStatusRequest>
    <id>1</id>
</GetServerStatusRequest>
```

**Response** (`GetServerStatusResponse`):
```xml
<GetServerStatusResponse>
    <status>true</status>
</GetServerStatusResponse>
```

**Erreurs possibles**:
- `ResourceNotFound`: Si le serveur avec l'ID spécifié n'existe pas

---

### 5. StartServer

Démarre un serveur (met son statut à `true`).

**Request** (`StartServerRequest`):
```xml
<StartServerRequest>
    <id>1</id>
</StartServerRequest>
```

**Response** (`StartServerResponse`):
```xml
<StartServerResponse>
    <server>
        <id>1</id>
        <name>Serveur Web 1</name>
        <ipAddress>192.168.1.10</ipAddress>
        <status>true</status>
    </server>
</StartServerResponse>
```

**Erreurs possibles**:
- `ResourceNotFound`: Si le serveur avec l'ID spécifié n'existe pas

---

### 6. StopServer

Arrête un serveur (met son statut à `false`).

**Request** (`StopServerRequest`):
```xml
<StopServerRequest>
    <id>1</id>
</StopServerRequest>
```

**Response** (`StopServerResponse`):
```xml
<StopServerResponse>
    <server>
        <id>1</id>
        <name>Serveur Web 1</name>
        <ipAddress>192.168.1.10</ipAddress>
        <status>false</status>
    </server>
</StopServerResponse>
```

**Erreurs possibles**:
- `ResourceNotFound`: Si le serveur avec l'ID spécifié n'existe pas

---

### 7. DeleteServer

Supprime un serveur du système.

**Important**: Un serveur en cours d'exécution (status = true) ne peut pas être supprimé.

**Request** (`DeleteServerRequest`):
```xml
<DeleteServerRequest>
    <id>1</id>
</DeleteServerRequest>
```

**Response** (`DeleteServerResponse`):
```xml
<DeleteServerResponse>
    <success>true</success>
    <message>Serveur supprimé avec succès</message>
</DeleteServerResponse>
```

**Erreurs possibles**:
- `ResourceNotFound`: Si le serveur avec l'ID spécifié n'existe pas
- `BusinessRuleViolation`: Si le serveur est en cours d'exécution (status = true)

---

## Gestion des erreurs

Le service utilise des SOAP Faults pour signaler les erreurs. Les types d'erreurs sont :

1. **ResourceNotFound**: Le serveur demandé n'existe pas
2. **ValidationError**: Erreur de validation des données (champs vides, IP dupliquée, etc.)
3. **BusinessRuleViolation**: Violation d'une règle métier (ex: tentative de suppression d'un serveur en cours d'exécution)

### Exemple de SOAP Fault

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

## Accès au WSDL

Le WSDL du service est accessible à l'adresse suivante :
```
http://localhost:8081/ws/servers.wsdl
```

Ce WSDL peut être utilisé pour générer les clients SOAP dans différents langages de programmation.

## Exemple d'utilisation avec SoapUI

1. Créer un nouveau projet SOAP dans SoapUI
2. Importer le WSDL depuis : `http://localhost:8081/ws/servers.wsdl`
3. Tester les différentes opérations disponibles

## Notes techniques

- Le service utilise Spring Boot 3.2.0
- Les classes Java sont générées automatiquement depuis le schéma XSD (`servers.xsd`)
- La base de données est gérée via JPA/Hibernate
- Le service écoute sur le port 8081

