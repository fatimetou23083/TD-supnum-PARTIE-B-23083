# Résumé - Guide de Test Postman

## 📋 Vue d'ensemble

Ce guide permet de tester les 3 services (SOAP, Middle, Consumer) avec Postman et de vérifier la conformité au TD Partie B.

---

## 🚀 Étapes Principales

### 1. Préparation
- Démarrer les 3 services dans l'ordre (ports 8080, 8081, 8082)
- Importer la collection Postman `COLLECTION_POSTMAN.json`
- Créer l'environnement Postman avec les variables d'URL

### 2. Tests Consumer Service (10 tests)
Tests du point d'entrée principal qui consomme le Middle Service :

| # | Test | Méthode | Endpoint | Status Attendu |
|---|------|---------|----------|----------------|
| 1 | Créer serveur | POST | `/api/consumer/servers` | 201 |
| 2 | Lister serveurs | GET | `/api/consumer/servers` | 200 |
| 3 | Statut serveur | GET | `/api/consumer/servers/{id}/status` | 200 |
| 4 | Démarrer serveur | PUT | `/api/consumer/servers/{id}/start` | 200 |
| 5 | Arrêter serveur | PUT | `/api/consumer/servers/{id}/stop` | 200 |
| 6 | Renommer serveur | PUT | `/api/consumer/servers/{id}/rename` | 200 |
| 7 | Supprimer serveur (arrêté) | DELETE | `/api/consumer/servers/{id}` | 200 |
| 8 | Supprimer serveur (démarré) | DELETE | `/api/consumer/servers/{id}` | 400 ❌ |
| 9 | Erreur validation | POST | `/api/consumer/servers` (invalide) | 400 |
| 10 | 404 Not Found | GET | `/api/consumer/servers/999/status` | 404 |

### 3. Tests Middle Service (2 tests)
Vérification du bridge REST → SOAP :

| # | Test | Méthode | Endpoint | Status Attendu |
|---|------|---------|----------|----------------|
| 11 | Créer via Middle | POST | `/api/servers` | 201 |
| 12 | Lister via Middle | GET | `/api/servers` | 200 |

---

## ✅ Checklist de Vérification TD

### Partie B - Point 1 : Branche Git
- [ ] Branche `SOA_TO_REST` créée et poussée

### Partie B - Point 2 : Middle Service
- [ ] Consomme tous les endpoints SOAP
- [ ] 7 endpoints REST implémentés

### Partie B - Point 3 : Contrats Middle Service
- [ ] `API_DOCUMENTATION.md` présent
- [ ] Contrats dans `CONTRATS_COMMUNICATION.md`

### Partie B - Point 4 : Consumer Service
- [ ] Consomme tous les endpoints Middle
- [ ] 7 endpoints REST implémentés

### Partie B - Point 5 : Contrats Consumer Service
- [ ] `API_DOCUMENTATION.md` présent
- [ ] Contrats dans `CONTRATS_COMMUNICATION.md`

### Partie B - Point 6 : Regroupement
- [ ] 3 services dans un seul répertoire
- [ ] Commit et push effectués

---

## 🎯 Critères de Réussite

### ✅ Fonctionnel
- Tous les tests 1-12 passent
- Flux complet fonctionne : Consumer → Middle → SOAP

### ✅ Conformité TD
- Branche Git correcte
- Services communiquent correctement
- Documentation complète

### ✅ Qualité
- Gestion d'erreurs correcte
- Validation des données
- Codes HTTP appropriés

---

## 🚨 Problèmes Courants

| Problème | Solution |
|----------|----------|
| Port déjà utilisé | Vérifier avec `netstat` et arrêter le processus |
| Erreur 503 | Vérifier que Middle Service est démarré |
| Erreur SOAP | Vérifier que SOAP Service est démarré |
| Erreur BDD | Vérifier PostgreSQL et créer la base |

---

## 📊 Résultat Final

**Travail complet et correct si** :
- ✅ Tous les tests passent
- ✅ Checklist TD complète
- ✅ Documentation présente
- ✅ Services fonctionnent ensemble

---

## 📝 Utilisation Rapide

1. **Démarrer les services** (3 terminaux)
2. **Importer** `COLLECTION_POSTMAN.json` dans Postman
3. **Créer environnement** avec les variables d'URL
4. **Exécuter les tests** dans l'ordre (1-12)
5. **Vérifier** la checklist TD
6. **Documenter** les résultats

---

**Temps estimé** : 30-45 minutes pour tous les tests

