# Consumer Service - Gestion des Serveurs

## Description

Le Consumer Service est un service REST qui consomme les endpoints du Middle Service pour permettre aux clients de développer leurs propres systèmes de supervision et de monitoring des serveurs hébergés dans le datacenter.

## Architecture

```
Client → Consumer Service (REST) → Middle Service (REST) → SOAP Service
```

- **Port Consumer Service**: 8082
- **Port Middle Service**: 8081
- **Port SOAP Service**: 8080

## Prérequis

1. **Java 17** ou supérieur
2. **Maven 3.6+**
3. Le **SOAP Service** doit être démarré et accessible sur `http://localhost:8080/ws`
4. Le **Middle Service** doit être démarré et accessible sur `http://localhost:8081/api/servers`

## Configuration

Modifiez les paramètres dans `src/main/resources/application.properties` si nécessaire :

```properties
# Port du service
server.port=8082

# URL du middle-service
middle.service.url=http://localhost:8081/api/servers

# Timeouts pour RestTemplate
rest.client.connect-timeout=5000
rest.client.read-timeout=5000
```

## Compilation et démarrage

### Compiler le projet
```bash
cd consumer-service
mvn clean compile
```

### Démarrer le service
```bash
mvn spring-boot:run
```

Le service démarrera sur le port **8082**.

## Accès au service

- **Base URL**: `http://localhost:8082`
- **API Documentation (Swagger)**: `http://localhost:8082/swagger-ui.html`
- **API Docs JSON**: `http://localhost:8082/api-docs`

## Endpoints disponibles

Tous les endpoints sont préfixés par `/api/consumer/servers` :

- `POST /api/consumer/servers` - Créer un serveur
- `GET /api/consumer/servers` - Lister tous les serveurs
- `PUT /api/consumer/servers/{id}/rename` - Renommer un serveur
- `GET /api/consumer/servers/{id}/status` - Obtenir le statut d'un serveur
- `PUT /api/consumer/servers/{id}/start` - Démarrer un serveur
- `PUT /api/consumer/servers/{id}/stop` - Arrêter un serveur
- `DELETE /api/consumer/servers/{id}` - Supprimer un serveur

Pour plus de détails, consultez [API_DOCUMENTATION.md](API_DOCUMENTATION.md).

## Test du service

### Méthode 1 : Utiliser Swagger UI (Recommandé)

1. Démarrer le service
2. Ouvrir votre navigateur et aller à : `http://localhost:8082/swagger-ui.html`
3. Tester les endpoints directement depuis l'interface Swagger

### Méthode 2 : Utiliser cURL

Exemple pour créer un serveur :

```bash
curl -X POST http://localhost:8082/api/consumer/servers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Serveur Web 1",
    "ipAddress": "192.168.1.10"
  }'
```

Exemple pour lister tous les serveurs :

```bash
curl -X GET http://localhost:8082/api/consumer/servers
```

### Méthode 3 : Utiliser Postman

1. Importer la collection depuis Swagger : `http://localhost:8082/api-docs`
2. Tester les endpoints disponibles

## Gestion des erreurs

Le service gère automatiquement les erreurs et retourne des réponses JSON structurées :

- **400 Bad Request** : Erreurs de validation
- **404 Not Found** : Ressource non trouvée
- **503 Service Unavailable** : Erreur de communication avec le middle-service
- **500 Internal Server Error** : Erreurs inattendues

## Structure du projet

```
consumer-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/consumer_service/
│   │   │       ├── client/          # Client REST pour le middle-service
│   │   │       ├── config/         # Configuration (RestTemplate)
│   │   │       ├── controller/     # Contrôleurs REST
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       ├── exception/      # Gestion des exceptions
│   │   │       └── service/        # Services métier
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── API_DOCUMENTATION.md
└── README.md
```

## Dépendances principales

- **Spring Boot Web MVC** : Pour les endpoints REST
- **Spring Boot Validation** : Pour la validation des données
- **Springdoc OpenAPI** : Pour la documentation Swagger/OpenAPI
- **RestTemplate** : Pour consommer le middle-service

## Notes importantes

1. Assurez-vous que le middle-service est démarré avant de démarrer le consumer-service.
2. Tous les endpoints retournent du JSON.
3. Les erreurs sont gérées avec les codes HTTP appropriés.
4. Un serveur en cours d'exécution (status = true) ne peut pas être supprimé.
5. Le service valide les données d'entrée avant de les transmettre au middle-service.

