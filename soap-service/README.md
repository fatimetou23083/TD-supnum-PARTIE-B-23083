# Service SOAP - Gestion des Serveurs

## Prérequis

1. **Java 17** ou supérieur
2. **Maven 3.6+**
3. **PostgreSQL** (version 12 ou supérieure)

## Configuration de la base de données

1. Créer une base de données PostgreSQL :
```sql
CREATE DATABASE server_management_soap;
```

2. Modifier les paramètres de connexion dans `src/main/resources/application.properties` si nécessaire :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/server_management_soap
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe
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

## Accès au service

- **WSDL** : http://localhost:8081/ws/servers.wsdl
- **Point d'accès SOAP** : http://localhost:8081/ws

## Test du service

### Méthode 1 : Utiliser SoapUI (Recommandé)

1. Télécharger et installer [SoapUI](https://www.soapui.org/)
2. Créer un nouveau projet SOAP
3. Importer le WSDL : `http://localhost:8081/ws/servers.wsdl`
4. Tester les opérations disponibles :
   - CreateServer
   - ListServers
   - GetServerStatus
   - RenameServer
   - StartServer
   - StopServer
   - DeleteServer

### Méthode 2 : Utiliser cURL

Exemple pour créer un serveur :

```bash
curl -X POST http://localhost:8081/ws \
  -H "Content-Type: text/xml" \
  -H "SOAPAction: \"http://example.com/soap/servers/CreateServerRequest\"" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:CreateServerRequest>
         <ser:name>Serveur Web 1</ser:name>
         <ser:ipAddress>192.168.1.10</ser:ipAddress>
      </ser:CreateServerRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### Méthode 3 : Utiliser Postman

1. Créer une nouvelle requête POST
2. URL : `http://localhost:8081/ws`
3. Headers :
   - `Content-Type: text/xml`
   - `SOAPAction: http://example.com/soap/servers/CreateServerRequest`
4. Body : Sélectionner "raw" et "XML", puis coller le XML de la requête SOAP

## Documentation complète

Voir le fichier `API_DOCUMENTATION.md` pour la documentation complète de l'API avec tous les exemples de requêtes et réponses.

## Vérification rapide

Pour vérifier que le service fonctionne, accédez à :
- http://localhost:8081/ws/servers.wsdl (doit afficher le WSDL)

Si le WSDL s'affiche correctement, le service est opérationnel !

