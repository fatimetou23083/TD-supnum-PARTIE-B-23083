# Middle Service - REST to SOAP Bridge

## Description

Le Middle Service est un service REST qui fait le pont entre les clients et le service SOAP de gestion des serveurs. Il expose une API REST standardisée pour gérer les serveurs hébergés dans le datacenter.

## Architecture

```
Client REST → Middle Service (REST) → SOAP Service
```

- **Port Middle Service**: 8081
- **Port SOAP Service**: 8080
- **Format de communication**: JSON (REST) ↔ XML (SOAP)

## Prérequis

1. **Java 17** ou supérieur
2. **Maven 3.6+**
3. **Service SOAP** démarré et accessible sur `http://localhost:8080/ws`

## Configuration

Le fichier `application.properties` contient la configuration suivante :

```properties
server.port=8081
soap.service.url=http://localhost:8080/ws
spring.application.name=middle-service
```

## Compilation et démarrage

### Compiler le projet
```bash
mvn clean compile
```

### Démarrer le service
```bash
mvn spring-boot:run
```

Le service démarrera sur le port **8081**.

## Documentation API

### Swagger UI

Une fois le service démarré, accédez à la documentation interactive Swagger :

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **API Docs JSON**: http://localhost:8081/api-docs

### Documentation complète

Voir le fichier `API_DOCUMENTATION.md` pour la documentation complète de l'API avec tous les exemples de requêtes et réponses.

## Endpoints REST

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/servers` | Créer un serveur |
| GET | `/api/servers` | Lister tous les serveurs |
| PUT | `/api/servers/{id}/rename` | Renommer un serveur |
| GET | `/api/servers/{id}/status` | Obtenir le statut d'un serveur |
| PUT | `/api/servers/{id}/start` | Démarrer un serveur |
| PUT | `/api/servers/{id}/stop` | Arrêter un serveur |
| DELETE | `/api/servers/{id}` | Supprimer un serveur |

## Structure du projet

```
middle-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── client/          # Client SOAP
│   │   │       ├── config/          # Configuration
│   │   │       ├── controller/      # Contrôleurs REST
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       └── middle_service/   # Application principale
│   │   └── resources/
│   │       ├── application.properties
│   │       └── wsdl/
│   │           └── servers.wsdl    # WSDL du service SOAP
│   └── test/
└── target/
    └── generated-sources/
        └── wsimport/
            └── com/example/generated/  # Classes générées depuis WSDL
```

## Génération des classes depuis WSDL

Les classes Java sont automatiquement générées depuis le WSDL lors de la compilation grâce au plugin `jaxws-maven-plugin`. Les classes générées se trouvent dans :

`target/generated-sources/wsimport/com/example/generated/`

## Exemple d'utilisation

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

## Vérification rapide

Pour vérifier que le service fonctionne :

1. Démarrer le service SOAP sur le port 8080
2. Démarrer le Middle Service sur le port 8081
3. Accéder à : http://localhost:8081/swagger-ui.html

Si Swagger UI s'affiche correctement, le service est opérationnel !

## Notes importantes

1. Le service SOAP doit être démarré avant le Middle Service
2. Tous les endpoints retournent du JSON
3. Un serveur en cours d'exécution (status = true) ne peut pas être supprimé
4. Les erreurs sont gérées avec les codes HTTP appropriés

