# Atelier 4 : Couche Service, IoC et Injection de Dépendances

## A. Modes d'injection comparés

| Critère | Injection par constructeur (`@RequiredArgsConstructor` + `final`) | Injection par attribut (`@Autowired` sur champ) |
| :--- | :--- | :--- |
| **Champ `final` possible ?** | **Oui** (garantit l'immuabilité de l'état du bean) | **Non** (le champ ne peut pas être final car injecté après instanciation par réflexion) |
| **Dépendance visible dans la signature de la classe ?** | **Oui** (explicitement déclarée dans le constructeur) | **Non** (dépendance cachée / invisible de l'extérieur) |
| **Classe utilisable avec `new` hors conteneur sans dépendance `null` ?** | **Oui** (le compilateur oblige à fournir les dépendances lors des tests unitaires) | **Non** (`new Service()` laisse les champs à `null`, nécessitant réflexion ou conteneur) |
| **Signalé par SonarQube ?** | **Non** (recommandé par Spring et SonarQube) | **Oui** (règle **S6813** : *"Field dependency injection should be avoided"*) |

---

## B. Services

| Service | Dépendances injectées | Mode | Justification |
| :--- | :--- | :--- | :--- |
| **`AgenceServiceImpl`** | `IAgenceRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, tests unitaires facilités, découplage via l'interface `IAgenceRepository`. |
| **`ClientServiceImpl`** | `IClientRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, tests unitaires sans conteneur, découplage via l'interface `IClientRepository`. |
| **`ContratServiceImpl`** | `IContratRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, injection garantie non nulle à l'instanciation, découplage via `IContratRepository`. |
| **`EmployeServiceImpl`** | `IEmployeRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, injection saine sans couplage fort, découplage via `IEmployeRepository`. |
| **`EquipementServiceImpl`** | `IEquipementRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, injection saine sans couplage fort, découplage via `IEquipementRepository`. |
| **`MaintenanceServiceImpl`** | `IMaintenanceRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, injection saine sans couplage fort, découplage via `IMaintenanceRepository`. |
| **`PaiementServiceImpl`** | `IPaiementRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, service en lecture seule, découplage via `IPaiementRepository`. |
| **`ReservationServiceImpl`** | `IReservationRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, injection garantie non nulle, découplage via `IReservationRepository`. |
| **`VehiculeServiceImpl`** | `IVehiculeRepository` | Constructeur | Immuabilité via champ `final`, dépendance explicite, injection garantie non nulle, découplage via `IVehiculeRepository`. |

---

## C. Messages d'erreur

### Message 1
```
APPLICATION FAILED TO START
Description:
Parameter 0 of constructor in tn.esprit.autoloc.web.controller.ContratController
required a bean of type 'tn.esprit.autoloc.service.IContratService' that could not be found.
Action:
Consider defining a bean of type 'tn.esprit.autoloc.service.IContratService' in your configuration.
```
* **Causes les plus probables côté `ContratServiceImpl` :**
  1. **Oubli de l'annotation `@Service`** (ou `@Component`) sur la classe `ContratServiceImpl` : Spring ne détecte pas la classe lors du scan des composants et aucun bean de type `IContratService` n'est créé.
  2. **Problème de package / scan de composants** : `ContratServiceImpl` est placé dans un package non couvert par le `@ComponentScan` (c'est-à-dire en dehors de l'arborescence du package de `AutolocApiApplication`).
* **Correction :**
  Ajouter l'annotation `@Service` sur `ContratServiceImpl` et veiller à ce que la classe soit dans le sous-package `tn.esprit.autoloc.service...`.

### Message 2
```
Description:
Parameter 0 of constructor in tn.esprit.autoloc.web.controller.NotificationController
required a single bean, but 2 were found:
- emailNotificateur
- smsNotificateur
Action:
Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```
* **Deux corrections possibles :**
  1. **Utiliser `@Primary`** : Placer l'annotation `@Primary` sur l'une des classes d'implémentation (ex. `@Primary @Service public class EmailNotificateur implements INotificateur`). Cela indique à Spring de choisir cette implémentation par défaut en cas d'ambiguïté.
  2. **Utiliser `@Qualifier`** : Placer l'annotation `@Qualifier("emailNotificateur")` au niveau du point d'injection (sur le paramètre du constructeur de `NotificationController` ou sur le champ injecté). Cela lève explicitement l'ambiguïté en ciblant le bean par son nom.

### Message 3
```
The dependencies of some of the beans in the application context form a cycle:
clientServiceImpl
  |   ^
  v   |
reservationServiceImpl
```
* **Pourquoi l'injection par constructeur rend ce cycle impossible à contourner ?**
  Avec l'injection par constructeur, pour instancier `ClientServiceImpl`, Spring doit d'abord instancier `ReservationServiceImpl`. Or, pour instancier `ReservationServiceImpl`, Spring doit d'abord disposer de l'instance de `ClientServiceImpl`. Aucun des constructeurs ne peut être exécuté en premier, créant un interblocage d'instanciation strict.
* **Piste de conception pour le supprimer :**
  - **Extraire un service coordinateur / médiateur** : Créer un service tiers (ex: `ReservationWorkflowService`) qui dépend à la fois de `ClientService` et de `ReservationService`, évitant ainsi que les deux services ne s'appellent mutuellement.
  - **Utiliser l'événementiel applicatif (Event-Driven)** : Utiliser `ApplicationEventPublisher` pour notifier les changements sans dépendance directe entre les deux services.

---

## D. Anomalies SonarQube for IDE

1. **Règle S6813 : *Field dependency injection should be avoided***
   * **Explication :** L'injection directe sur champ (`@Autowired private IContratRepository contratRepository;`) viole le principe d'encapsulation, empêche de déclarer les champs `final` (perte d'immuabilité) et rend les tests unitaires impossibles sans recourir à un framework de réflexion ou à Spring.
   * **Correction apportée :** Utilisation systématique de l'injection par constructeur avec Lombok (`@RequiredArgsConstructor`) et des champs `final`.

2. **Règle S112 : *Generic exceptions should never be thrown***
   * **Explication :** Lancer des exceptions génériques comme `RuntimeException` ou `Exception` ne fournit aucune information sémantique sur la cause de l'erreur et empêche les couches supérieures (contrôleurs, gestionnaires `@ControllerAdvice`) d'effectuer un traitement adapté (ex: renvoyer un statut HTTP 404 au lieu d'une 500).
   * **Correction apportée :** Définition et utilisation de l'exception métier dédiée `ResourceNotFoundException`.

3. **Règle S1068 / Code mort : *Commented-out code and unused imports should be removed***
   * **Explication :** Laisser des blocs de code commentés (comme les TODOs de mapping d'attributs) ou des imports superflus alourdit le code, dégrade sa maintenabilité et risque d'induire en erreur les développeurs ultérieurs.
   * **Correction apportée :** Remplacement de tout le code commenté par l'implémentation effective des mappings d'attributs dans chaque méthode `update()` et suppression des imports inutilisés.

---

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?**
   Il n'y a aucun appel manuel à `new ContratServiceImpl(...)` dans le code source de l'application. C'est le conteneur IoC de Spring (BeanFactory / ApplicationContext) qui instancie la classe par réflexion au démarrage lors de la découverte du bean annoté `@Service`.

2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?**
   En application du principe d'Inversion des Dépendances (DIP des principes SOLID), les modules de haut niveau doivent dépendre d'abstractions et non de détails d'implémentation. Cela découple le contrôleur de l'implémentation concrète, facilite le mocking pour les tests unitaires et permet à Spring d'injecter des proxies dynamiques (transactions, sécurité, AOP).

3. **Pourquoi un service singleton doit-il rester sans état ? Donner un exemple de bug.**
   Par défaut, les beans Spring sont des singletons partagés par toutes les requêtes et tous les threads concurrents. S'il possédait un état modifiable (attribut d'instance mutable), des requêtes concurrentes modifieraient cet état de manière non synchronisée, provoquant des conditions de course (*race conditions*).
   *Exemple de bug :* Si `ClientServiceImpl` conservait un champ `private Client clientEnCours;`, deux utilisateurs consultant simultanément leur profil écraseraient mutuellement cette variable, conduisant l'utilisateur A à voir ou modifier les données personnelles de l'utilisateur B.

4. **Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` au lieu de lui passer directement l'objet reçu ?**
   Passer directement l'objet reçu à `save()` écraserait toutes les colonnes en base de données avec les valeurs transmises dans la requête. Si certains champs ne sont pas envoyés (ou laissés à `null`), les données existantes seraient perdues. De plus, sur des collections associées (ex: `paiements` d'un contrat) configurées avec `orphanRemoval = true`, écraser la collection par `null` ou une liste vide supprimerait immédiatement tous les enregistrements enfants en base de données.

5. **Quelle différence entre `@Component` et `@Bean` ? Entre `@Primary` et `@Qualifier` ? Pourquoi `IPaiementService` n'expose-t-il ni création, ni modification, ni suppression ?**
   * **`@Component` vs `@Bean` :** `@Component` s'applique sur une classe pour une détection automatique via le classpath scanning de Spring. `@Bean` s'applique sur une méthode dans une classe `@Configuration` pour enregistrer explicitement une instance dans le contexte (indispensable pour instancier des classes issues de bibliothèques tierces non modifiables).
   * **`@Primary` vs `@Qualifier` :** `@Primary` est placé sur une classe pour définir le bean prioritaire par défaut en cas de multiplicité. `@Qualifier` est placé au point d'injection pour sélectionner précisément un bean par son nom.
   * **`IPaiementService` en lecture seule :** Dans les règles métier du domaine, un paiement n'a pas d'existence autonome indépendante d'un contrat de location. Sa création, son cycle de vie et sa suppression éventuelle sont strictement pilotés par l'agrégat `Contrat` (en cascade) pour garantir la cohérence financière du système.
