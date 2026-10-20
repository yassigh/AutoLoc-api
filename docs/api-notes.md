# Atelier 5 : API REST, Tests Postman et Documentation Swagger

## A. Endpoints

*Note : Avec le context-path configuré dans `application.properties` (`server.servlet.context-path=/api`), toutes les routes ci-dessous sont préfixées par `/api` (ex. `/api/contrats`).*

| Contrôleur | Verbe | Route | Service appelé | Code succès | Codes d'erreur |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`ContratController`** | `POST` | `/contrats` | `IContratService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/contrats/{id}` | `IContratService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/contrats` | `IContratService.findAll` | `200 OK` | — |
| | `PUT` | `/contrats/{id}` | `IContratService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/contrats/{id}` | `IContratService.deleteById` | `204 No Content` | `404 Not Found` |
| **`PaiementController`** | `GET` | `/paiements/{id}` | `IPaiementService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/paiements` | `IPaiementService.findAll` | `200 OK` | — |
| *(Paiement lecture seule)*| `POST` | `/paiements` | *(non mappé)* | — | `405 Method Not Allowed` |
| **`AgenceController`** | `POST` | `/agences` | `IAgenceService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/agences/{id}` | `IAgenceService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/agences` | `IAgenceService.findAll` | `200 OK` | — |
| | `PUT` | `/agences/{id}` | `IAgenceService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/agences/{id}` | `IAgenceService.deleteById` | `204 No Content` | `404 Not Found` |
| **`ClientController`** | `POST` | `/clients` | `IClientService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/clients/{id}` | `IClientService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/clients` | `IClientService.findAll` | `200 OK` | — |
| | `PUT` | `/clients/{id}` | `IClientService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/clients/{id}` | `IClientService.deleteById` | `204 No Content` | `404 Not Found` |
| **`EmployeController`** | `POST` | `/employes` | `IEmployeService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/employes/{id}` | `IEmployeService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/employes` | `IEmployeService.findAll` | `200 OK` | — |
| | `PUT` | `/employes/{id}` | `IEmployeService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/employes/{id}` | `IEmployeService.deleteById` | `204 No Content` | `404 Not Found` |
| **`EquipementController`** | `POST` | `/equipements` | `IEquipementService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/equipements/{id}` | `IEquipementService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/equipements` | `IEquipementService.findAll` | `200 OK` | — |
| | `PUT` | `/equipements/{id}` | `IEquipementService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/equipements/{id}` | `IEquipementService.deleteById` | `204 No Content` | `404 Not Found` |
| **`MaintenanceController`** | `POST` | `/maintenances` | `IMaintenanceService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/maintenances/{id}` | `IMaintenanceService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/maintenances` | `IMaintenanceService.findAll` | `200 OK` | — |
| | `PUT` | `/maintenances/{id}` | `IMaintenanceService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/maintenances/{id}` | `IMaintenanceService.deleteById` | `204 No Content` | `404 Not Found` |
| **`ReservationController`** | `POST` | `/reservations` | `IReservationService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/reservations/{id}` | `IReservationService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/reservations` | `IReservationService.findAll` | `200 OK` | — |
| | `PUT` | `/reservations/{id}` | `IReservationService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/reservations/{id}` | `IReservationService.deleteById` | `204 No Content` | `404 Not Found` |
| **`VehiculeController`** | `POST` | `/vehicules` | `IVehiculeService.create` | `201 Created` | `400 Bad Request` |
| | `GET` | `/vehicules/{id}` | `IVehiculeService.findById` | `200 OK` | `404 Not Found` |
| | `GET` | `/vehicules` | `IVehiculeService.findAll` | `200 OK` | — |
| | `PUT` | `/vehicules/{id}` | `IVehiculeService.update` | `200 OK` | `400 Bad Request`, `404 Not Found` |
| | `DELETE` | `/vehicules/{id}` | `IVehiculeService.deleteById` | `204 No Content` | `404 Not Found` |

---

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
| :--- | :--- | :--- |
| **`Contrat.paiements` ↔ `Paiement.contrat`** | `@JsonManagedReference("contrat-paiements")` sur `Contrat.paiements`<br>`@JsonBackReference("contrat-paiements")` sur `Paiement.contrat` | Association Parent-Enfant forte (agrégation/composition avec cascade). Lors de la consultation d'un contrat, on désire sérialiser ses paiements associés (`@JsonManagedReference`). Côté `Paiement`, `@JsonBackReference` omet le pointeur retour vers le `Contrat` lors de la sérialisation, rompant ainsi la boucle récursive infinie sans perdre la relation lors de la désérialisation. |
| **`Agence.vehicules`** | `@JsonIgnore` | Évite le cycle de sérialisation bidirectionnel avec `Vehicule.agence` et évite le sur-chargement de la base de données (sérialiser une agence chargerait tous ses véhicules, puis leurs réservations, etc.). |
| **`Agence.employes`** | `@JsonIgnore` | Évite le cycle infini avec `Employe.agence` et empêche le déclenchement intempestif de proxies Lazy hors session. |
| **`Vehicule.reservations`** | `@JsonIgnore` | Évite la boucle récursive avec `Reservation.vehicule`. |
| **`Vehicule.equipements`** | `@JsonIgnore` | Évite la boucle récursive avec `Equipement.vehicules` (relation ManyToMany). |
| **`Client.reservations`** | `@JsonIgnore` | Évite la boucle récursive avec `Reservation.client`. |
| **`Contrat.reservation`** | `@JsonIgnore` | Évite le cycle avec `Reservation.contrat` (relation OneToOne). |
| **`Equipement.vehicules`** | `@JsonIgnore` | Évite le cycle avec la collection inverse dans `Vehicule`. |

### Différence fondamentale entre `@JsonBackReference` et `@JsonIgnore`
- **`@JsonIgnore`** : Ignore inconditionnellement le champ annoté à la fois lors de la **sérialisation** (Java vers JSON) et lors de la **désérialisation** (JSON vers Java). L'attribut est totalement masqué du flux JSON dans les deux sens.
- **`@JsonBackReference`** : Agit en tandem avec `@JsonManagedReference`. Le champ annoté `@JsonBackReference` est omis lors de la sérialisation pour éviter la récursion cyclique, mais lors de la **désérialisation**, Jackson réinjecte automatiquement la référence de l'objet parent (`@JsonManagedReference`) dans ce champ enfant, restaurant ainsi la cohérence bidirectionnelle du graphe d'objets en mémoire.

---

## C. Postman et Swagger

| Outil | Rôle principal | Apports | Exemple concret dans l'atelier |
| :--- | :--- | :--- | :--- |
| **Swagger UI** | **Documentation & Exploration interactive** | Contrat d'interface synchronisé avec le code, documentation visuelle vivante (OpenAPI 3), essai ponctuel direct depuis le navigateur sans configuration préalable. | Découverte instantanée de la route `/api/contrats` avec le schéma du corps JSON généré automatiquement à partir de l'entité `Contrat`, et test rapide d'un `GET /api/contrats/9999` qui affiche le code `404` documenté par `@ApiResponse`. |
| **Postman** | **Tests fonctionnels, automatisation & validation** | Création de suites de tests rejouables, organisation par dossiers/environnements, variables dynamiques (`{{baseUrl}}`), assertions de code de retour et de format de réponse. | Validation du scénario de création d'un contrat avec cascade de paiements (`POST /api/contrats`), puis tentative d'ajout direct sur `/api/paiements` pour vérifier qu'il renvoie bien le code `405 Method Not Allowed`. |

---

## D. Anomalies SonarQube for IDE

1. **Règle S4684 : *Persistent entities should not be used as arguments or return types of `@RequestMapping` methods***
   - **Explication :** L'exposition directe des entités JPA (`@Entity`) dans les contrôleurs REST expose le modèle interne de la base de données, augmente le risque de sur-exposition de données sensibles (mass assignment, données d'audit) et couple fortement l'API externe au schéma relationnel.
   - **Correction apportée :** Utilisation temporaire d'annotations de sérialisation Jackson ciblées (`@JsonIgnore`, `@JsonBackReference`, `@JsonManagedReference`), avant transition ultérieure vers des records DTO dédiés (Data Transfer Objects).

2. **Règle S6813 : *Field dependency injection should be avoided***
   - **Explication :** L'injection de dépendances par attributs (`@Autowired` sur champ privé) viole l'encapsulation, rend les dépendances invisibles dans le constructeur et empêche de déclarer les champs `final` (perte d'immuabilité).
   - **Correction apportée :** Déclaration de champs `private final I...Service` et utilisation de l'annotation Lombok `@RequiredArgsConstructor` sur tous les contrôleurs REST.

3. **Règle S1128 : *Unused imports should be removed***
   - **Explication :** Les imports inutilisés encombrent le code source, augmentent le bruit visuel et peuvent masquer des conflits de nommage.
   - **Correction apportée :** Nettoyage systématique de tous les imports superflus dans l'ensemble des classes du package `tn.esprit.autoloc.web.controller`.

---

## E. Questions de compréhension

### 1. Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?
Dans l'architecture Spring MVC, c'est la **`DispatcherServlet`** (le Front Controller central) qui intercepte l'ensemble des requêtes HTTP entrantes.  
Pour identifier la méthode cible à exécuter :
1. La `DispatcherServlet` consulte le composant **`RequestMappingHandlerMapping`**.
2. Ce dernier maintient une table de correspondance construite au démarrage en scannant les beans annotés `@RestController` ou `@Controller` et leurs métadonnées de routage (`@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.).
3. Dès que l'URL, la méthode HTTP (GET, POST, etc.) et les en-têtes correspondent, il renvoie une chaîne d'exécution (`HandlerExecutionChain`) contenant le gestionnaire de méthode approprié (`HandlerMethod`).

### 2. Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?
- **Par rapport à `ContratRepository`** : Respect du principe de séparation des responsabilités (architecture en couches). Le contrôleur gère la couche Web (requêtes HTTP, codes de statut, désérialisation). La logique métier et les règles de validation (ex. interdiction de montant négatif, cascade métier) résident dans la couche Service. Court-circuiter le service vers le repository violerait la cohérence métier.
- **Par rapport à `ContratServiceImpl`** : Respect du principe d'inversion des dépendances (**DIP** de SOLID). Dépendre de l'interface `IContratService` plutôt que de l'implémentation concrète découple la couche Web de l'implémentation, facilite le mocking lors des tests unitaires et permet à Spring d'insérer des proxys dynamiques (gestion transactionnelle `@Transactional`, sécurité, interception).

### 3. Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?
- **POST → `201 Created`** : Selon la norme HTTP/1.1 (RFC 9110), un POST qui aboutit à la création d'une nouvelle ressource sur le serveur doit le notifier avec le code `201 Created` (souvent accompagné de l'en-tête `Location` ou de la ressource créée dans le corps).
- **DELETE → `204 No Content`** : Lorsqu'une suppression est effectuée avec succès, l'opération a réussi mais il n'y a plus aucun contenu à retourner dans le corps de la réponse HTTP, d'où `204 No Content`.
- **Ressource absente → `404 Not Found` vs `500 Internal Server Error`** : Le code `404` indique une erreur **côté client** (le client a demandé un URI ou un identifiant qui n'existe pas dans le système). Le code `500` désigne une défaillance **inattendue côté serveur** (bug non intercepté, crash de base de données). Une ressource inexistante est une situation normale de l'API et doit être retournée sous forme de `404 Not Found`, évitant de polluer les logs serveur avec de faux incidents techniques.

### 4. Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit un POST `/api/paiements` ?
- **Pourquoi uniquement des GET** : Un paiement est une pièce comptable immuable liée au cycle de vie d'un contrat de location. Dans les règles de gestion d'AutoLoc, les paiements ne peuvent être créés de manière isolée : ils sont créés en cascade lors de la signature ou mise à jour d'un contrat (`Contrat.paiements`). `PaiementController` est donc une ressource en lecture seule.
- **Réponse reçue sur POST `/api/paiements`** : Le serveur renvoie le statut HTTP **`405 Method Not Allowed`** (car aucune méthode avec le mapping POST n'est déclarée sur la route `/paiements`).

### 5. Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?
- **Risques de l'exposition directe des entités JPA** :
  1. **Boucles infinies et `StackOverflowError`** lors de la sérialisation JSON causées par les relations bidirectionnelles.
  2. **Sur-exposition de données sensibles ou internes** (mots de passe, numéros de sécurité, clés de jointure) ou vulnérabilité au *mass assignment* (injection de champs non désirés lors d'un PUT/POST).
  3. **Erreurs de proxy et de session (`LazyInitializationException`)** si Jackson tente de sérialiser une collection ou un objet Lazy hors session Hibernate.
  4. **Couplage fort** : Toute modification du schéma relationnel de la base de données brise automatiquement l'API publique consommée par les clients.
- **Pourquoi `@JsonManagedReference` sur `Contrat.paiements` vs `@JsonIgnore` sur `Agence.vehicules`** :
  - `Contrat` et `Paiement` forment une unité fonctionnelle étroite : un contrat a du sens lorsqu'on consulte ses lignes de paiements. On souhaite donc sérialiser la liste des paiements imbriquée dans le JSON du contrat via `@JsonManagedReference`.
  - `Agence` et `Vehicule` représentent un parc important et volumineux : sérialiser tous les véhicules à chaque consultation d'une agence entraînerait un transfert de données excessif et chargerait tout le graphe (véhicules, entretiens, réservations). On applique donc `@JsonIgnore` pour éviter cette surcharge massive et ces cycles.
