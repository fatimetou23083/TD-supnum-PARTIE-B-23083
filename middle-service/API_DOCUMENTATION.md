# API Documentation - Middle Service

## Vue d'ensemble

Le Middle Service est un service REST qui fait le pont entre les clients et le service SOAP de gestion des serveurs. Il expose une API REST standardisée pour gérer les serveurs hébergés dans le datacenter.

**Base URL**: `http://localhost:8081`

**Documentation Swagger**: `http://localhost:8081/swagger-ui.html`

**API Docs JSON**: `http://localhost:8081/api-docs`

## Endpoints REST

### 1. Créer un serveur

**Endpoint**: `POST /api/servers`

**Description**: Crée un nouveau serveur avec un nom et une adresse IP.

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
- `400`: Requête invalide

---

### 2. Lister tous les serveurs

**Endpoint**: `GET /api/servers`

**Description**: Récupère la liste de tous les serveurs.

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
- `500`: Erreur interne du serveur

---

### 3. Renommer un serveur

**Endpoint**: `PUT /api/servers/{id}/rename`

**Description**: Renomme un serveur existant.

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
- `404`: Serveur non trouvé

---

### 4. Obtenir le statut d'un serveur

**Endpoint**: `GET /api/servers/{id}/status`

**Description**: Récupère le statut (actif/inactif) d'un serveur.

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

---

### 5. Démarrer un serveur

**Endpoint**: `PUT /api/servers/{id}/start`

**Description**: Démarre un serveur (met son statut à true).

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

---

### 6. Arrêter un serveur

**Endpoint**: `PUT /api/servers/{id}/stop`

**Description**: Arrête un serveur (met son statut à false).

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

---

### 7. Supprimer un serveur

**Endpoint**: `DELETE /api/servers/{id}`

**Description**: Supprime un serveur. Un serveur en cours d'exécution ne peut pas être supprimé.

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

### DeleteServerResponseDTO
```json
{
  "success": true,
  "message": "Message de confirmation"
}
```

## Exemples d'utilisation avec cURL

### Créer un serveur
```bash
curl -X POST http://localhost:8081/api/servers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Serveur Web 1",
    "ipAddress": "192.168.1.10"
  }'
```

### Lister tous les serveurs
```bash
curl -X GET http://localhost:8081/api/servers
```

### Renommer un serveur
```bash
curl -X PUT http://localhost:8081/api/servers/1/rename \
  -H "Content-Type: application/json" \
  -d '{
    "newName": "Serveur Web Principal"
  }'
```

### Obtenir le statut
```bash
curl -X GET http://localhost:8081/api/servers/1/status
```

### Démarrer un serveur
```bash
curl -X PUT http://localhost:8081/api/servers/1/start
```

### Arrêter un serveur
```bash
curl -X PUT http://localhost:8081/api/servers/1/stop
```

### Supprimer un serveur
```bash
curl -X DELETE http://localhost:8081/api/servers/1
```

## Architecture

Le Middle Service agit comme un pont entre les clients REST et le service SOAP :

```
Client REST → Middle Service (REST) → SOAP Service
```

- **Port Middle Service**: 8081
- **Port SOAP Service**: 8080
- **Format de communication**: JSON (REST) ↔ XML (SOAP)

## Notes importantes

1. Le service SOAP doit être démarré et accessible sur `http://localhost:8080/ws` avant d'utiliser le Middle Service.
2. Tous les endpoints retournent du JSON.
3. Les erreurs sont gérées avec les codes HTTP appropriés.
4. Un serveur en cours d'exécution (status = true) ne peut pas être supprimé.

