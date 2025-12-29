# API Documentation - Consumer Service

## Vue d'ensemble

Le Consumer Service est un service REST qui consomme les endpoints du Middle Service pour permettre aux clients de développer leurs propres systèmes de supervision et de monitoring des serveurs hébergés dans le datacenter.

**Base URL**: `http://localhost:8082`

**Documentation Swagger**: `http://localhost:8082/swagger-ui.html`

**API Docs JSON**: `http://localhost:8082/api-docs`

## Architecture

Le Consumer Service agit comme un client qui consomme le Middle Service :

```
Client → Consumer Service (REST) → Middle Service (REST) → SOAP Service
```

- **Port Consumer Service**: 8082
- **Port Middle Service**: 8081
- **Port SOAP Service**: 8080
- **Format de communication**: JSON (REST)

## Endpoints REST

### 1. Créer un serveur

**Endpoint**: `POST /api/consumer/servers`

**Description**: Crée un nouveau serveur via le middle-service.

**Requête**:
```json
{
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10"
}
```

**Réponse (201 Created)**:
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": false
}
```

**Codes de réponse**:
- `201`: Serveur créé avec succès
- `400`: Requête invalide (validation échouée)
- `503`: Erreur de communication avec le middle-service

---

### 2. Lister tous les serveurs

**Endpoint**: `GET /api/consumer/servers`

**Description**: Récupère la liste de tous les serveurs via le middle-service.

**Réponse (200 OK)**:
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

**Codes de réponse**:
- `200`: Liste récupérée avec succès
- `503`: Erreur de communication avec le middle-service

---

### 3. Renommer un serveur

**Endpoint**: `PUT /api/consumer/servers/{id}/rename`

**Description**: Renomme un serveur existant via le middle-service.

**Paramètres**:
- `id` (path): ID du serveur à renommer

**Requête**:
```json
{
  "newName": "Serveur Web Principal"
}
```

**Réponse (200 OK)**:
```json
{
  "id": 1,
  "name": "Serveur Web Principal",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

**Codes de réponse**:
- `200`: Serveur renommé avec succès
- `400`: Requête invalide (newName manquant ou vide)
- `404`: Serveur non trouvé
- `503`: Erreur de communication avec le middle-service

---

### 4. Obtenir le statut d'un serveur

**Endpoint**: `GET /api/consumer/servers/{id}/status`

**Description**: Récupère le statut (actif/inactif) d'un serveur via le middle-service.

**Paramètres**:
- `id` (path): ID du serveur

**Réponse (200 OK)**:
```json
{
  "status": true
}
```

**Codes de réponse**:
- `200`: Statut récupéré avec succès
- `404`: Serveur non trouvé
- `503`: Erreur de communication avec le middle-service

---

### 5. Démarrer un serveur

**Endpoint**: `PUT /api/consumer/servers/{id}/start`

**Description**: Démarre un serveur (met son statut à true) via le middle-service.

**Paramètres**:
- `id` (path): ID du serveur à démarrer

**Réponse (200 OK)**:
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

**Codes de réponse**:
- `200`: Serveur démarré avec succès
- `404`: Serveur non trouvé
- `503`: Erreur de communication avec le middle-service

---

### 6. Arrêter un serveur

**Endpoint**: `PUT /api/consumer/servers/{id}/stop`

**Description**: Arrête un serveur (met son statut à false) via le middle-service.

**Paramètres**:
- `id` (path): ID du serveur à arrêter

**Réponse (200 OK)**:
```json
{
  "id": 1,
  "name": "Serveur Web 1",
  "ipAddress": "192.168.1.10",
  "status": false
}
```

**Codes de réponse**:
- `200`: Serveur arrêté avec succès
- `404`: Serveur non trouvé
- `503`: Erreur de communication avec le middle-service

---

### 7. Supprimer un serveur

**Endpoint**: `DELETE /api/consumer/servers/{id}`

**Description**: Supprime un serveur via le middle-service. Un serveur en cours d'exécution ne peut pas être supprimé.

**Paramètres**:
- `id` (path): ID du serveur à supprimer

**Réponse (200 OK)**:
```json
{
  "success": true,
  "message": "Serveur supprimé avec succès"
}
```

**Réponse en cas d'échec (400 Bad Request)**:
```json
{
  "success": false,
  "message": "Impossible de supprimer un serveur en cours d'exécution"
}
```

**Codes de réponse**:
- `200`: Serveur supprimé avec succès
- `400`: Impossible de supprimer (serveur en cours d'exécution)
- `404`: Serveur non trouvé
- `503`: Erreur de communication avec le middle-service

---

## Formats de données

### ServerDTO
```json
{
  "id": 1,
  "name": "Nom du serveur",
  "ipAddress": "192.168.1.10",
  "status": true
}
```

### CreateServerRequestDTO
```json
{
  "name": "Nom du serveur",
  "ipAddress": "192.168.1.10"
}
```

**Validation**:
- `name`: Obligatoire, ne peut pas être vide
- `ipAddress`: Obligatoire, doit être au format IP valide (ex: 192.168.1.10)

### DeleteServerResponseDTO
```json
{
  "success": true,
  "message": "Message de confirmation"
}
```

### Format d'erreur
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

## Exemples d'utilisation avec cURL

### Créer un serveur
```bash
curl -X POST http://localhost:8082/api/consumer/servers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Serveur Web 1",
    "ipAddress": "192.168.1.10"
  }'
```

### Lister tous les serveurs
```bash
curl -X GET http://localhost:8082/api/consumer/servers
```

### Renommer un serveur
```bash
curl -X PUT http://localhost:8082/api/consumer/servers/1/rename \
  -H "Content-Type: application/json" \
  -d '{
    "newName": "Serveur Web Principal"
  }'
```

### Obtenir le statut
```bash
curl -X GET http://localhost:8082/api/consumer/servers/1/status
```

### Démarrer un serveur
```bash
curl -X PUT http://localhost:8082/api/consumer/servers/1/start
```

### Arrêter un serveur
```bash
curl -X PUT http://localhost:8082/api/consumer/servers/1/stop
```

### Supprimer un serveur
```bash
curl -X DELETE http://localhost:8082/api/consumer/servers/1
```

## Gestion des erreurs

Le service gère plusieurs types d'erreurs :

1. **Erreurs de validation** (400 Bad Request) : Lorsque les données de la requête ne respectent pas les contraintes de validation
2. **Erreurs de communication** (503 Service Unavailable) : Lorsque le middle-service n'est pas accessible
3. **Erreurs serveur** (500 Internal Server Error) : Pour les erreurs inattendues

## Prérequis

Avant d'utiliser le Consumer Service, assurez-vous que :

1. Le **SOAP Service** est démarré et accessible sur `http://localhost:8080/ws`
2. Le **Middle Service** est démarré et accessible sur `http://localhost:8081/api/servers`
3. Le **Consumer Service** est démarré sur le port `8082`

## Notes importantes

1. Tous les endpoints retournent du JSON.
2. Les erreurs sont gérées avec les codes HTTP appropriés et des messages d'erreur détaillés.
3. Un serveur en cours d'exécution (status = true) ne peut pas être supprimé.
4. Le service valide les données d'entrée avant de les transmettre au middle-service.
5. Les timeouts de connexion sont configurables via `application.properties`.

