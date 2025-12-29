# Guide de test avec SoapUI

## Prérequis

1. **SoapUI** doit être installé : https://www.soapui.org/downloads/soapui.html
2. Le service SOAP doit être démarré sur `http://localhost:8081`

## Étape 1 : Démarrer le service SOAP

```bash
cd soap-service
mvn spring-boot:run
```

Attendez le message : `Started SoapServiceApplication`

## Étape 2 : Créer un nouveau projet dans SoapUI

1. Ouvrez **SoapUI**
2. Allez dans **File → New SOAP Project** (ou cliquez sur **SOAP** dans la barre d'outils)
3. Dans le champ **Project Name**, entrez : `ServerManagementSOAP`
4. Dans le champ **Initial WSDL**, entrez : `http://localhost:8081/ws/servers.wsdl`
5. Cochez **Create Requests** (optionnel, pour créer automatiquement des requêtes)
6. Cliquez sur **OK**

SoapUI va télécharger le WSDL et créer automatiquement toutes les opérations.

## Étape 3 : Tester l'opération CreateServer

1. Dans le panneau de gauche, développez : **ServerManagementSOAP → ServersPortBinding → CreateServer**
2. Double-cliquez sur **Request 1**
3. Une fenêtre s'ouvre avec le corps de la requête SOAP

### Requête à envoyer :

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

4. Remplacez le contenu de la requête par celui ci-dessus (ou modifiez juste les valeurs `name` et `ipAddress`)
5. Cliquez sur le bouton **►** (Play) en haut à gauche pour envoyer la requête
6. Vous devriez voir la réponse dans le panneau de droite

### Réponse attendue :

```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:CreateServerResponse xmlns:ns2="http://example.com/soap/servers">
         <ns2:server>
            <ns2:id>1</ns2:id>
            <ns2:name>Serveur Web Production</ns2:name>
            <ns2:ipAddress>192.168.1.100</ns2:ipAddress>
            <ns2:status>false</ns2:status>
         </ns2:server>
      </ns2:CreateServerResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

**Notez l'ID du serveur créé** (ici `1`) pour les prochains tests.

## Étape 4 : Tester ListServers

1. Développez : **ListServers → Request 1**
2. Le corps de la requête devrait être :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ser="http://example.com/soap/servers">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:ListServersRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```
3. Cliquez sur **►** pour envoyer
4. Vous devriez voir tous les serveurs créés

## Étape 5 : Tester GetServerStatus

1. Développez : **GetServerStatus → Request 1**
2. Modifiez la requête pour utiliser l'ID du serveur créé (remplacez `?` par `1`) :
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
3. Cliquez sur **►** pour envoyer
4. Réponse attendue : `<ns2:status>false</ns2:status>` (serveur arrêté)

## Étape 6 : Tester StartServer

1. Développez : **StartServer → Request 1**
2. Utilisez l'ID du serveur :
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
3. Cliquez sur **►** pour envoyer
4. Vérifiez que `status` est maintenant `true` dans la réponse

## Étape 7 : Tester StopServer

1. Développez : **StopServer → Request 1**
2. Utilisez le même ID :
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
3. Cliquez sur **►** pour envoyer
4. Vérifiez que `status` est maintenant `false`

## Étape 8 : Tester RenameServer

1. Développez : **RenameServer → Request 1**
2. Modifiez la requête :
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
3. Cliquez sur **►** pour envoyer
4. Vérifiez que le nom a changé dans la réponse

## Étape 9 : Tester DeleteServer (avec erreur)

**Important** : Un serveur en cours d'exécution ne peut pas être supprimé.

1. Assurez-vous que le serveur est arrêté (utilisez StopServer si nécessaire)
2. Développez : **DeleteServer → Request 1**
3. Modifiez la requête :
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
4. Cliquez sur **►** pour envoyer
5. Vous devriez recevoir une réponse avec `success: true`

### Tester le cas d'erreur :

1. Créez un nouveau serveur (ID 2)
2. Démarrez-le (StartServer)
3. Essayez de le supprimer (DeleteServer avec ID 2)
4. Vous devriez recevoir une **SOAP Fault** avec le message d'erreur

## Exemples de SOAP Fault (erreurs)

### Erreur : Serveur introuvable
```xml
<SOAP-ENV:Fault>
   <faultcode>SOAP-ENV:Server</faultcode>
   <faultstring>Serveur introuvable avec l'identifiant 999</faultstring>
   <detail>
      <ResourceNotFound xmlns="http://example.com/soap/servers">
         Serveur introuvable avec l'identifiant 999
      </ResourceNotFound>
   </detail>
</SOAP-ENV:Fault>
```

### Erreur : Suppression d'un serveur en cours d'exécution
```xml
<SOAP-ENV:Fault>
   <faultcode>SOAP-ENV:Server</faultcode>
   <faultstring>Impossible de supprimer un serveur en cours d'exécution</faultstring>
   <detail>
      <BusinessRuleViolation xmlns="http://example.com/soap/servers">
         Impossible de supprimer un serveur en cours d'exécution
      </BusinessRuleViolation>
   </detail>
</SOAP-ENV:Fault>
```

## Astuces SoapUI

1. **Sauvegarder les requêtes** : Cliquez-droit sur une requête → **Add to TestCase** pour créer des tests automatisés
2. **Properties** : Vous pouvez utiliser des propriétés pour réutiliser les IDs entre requêtes
3. **Assertions** : Ajoutez des assertions pour valider automatiquement les réponses
4. **Logs** : Vérifiez les logs du service Spring Boot pour voir les requêtes SQL exécutées

## Vérification dans PostgreSQL

Vous pouvez vérifier les données directement dans PostgreSQL :

```sql
\c server_management_soap
SELECT * FROM servers;
```

## URL du service

- **WSDL** : http://localhost:8081/ws/servers.wsdl
- **Endpoint SOAP** : http://localhost:8081/ws

