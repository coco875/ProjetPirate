# Template Diagramme UML de séquence pour utilisation d'une carte Attaque
Rappel: deux diagrammes de séquence à faire, représentant le fonctionnement de 2 cartes différentes

Note: jouerCarte() devrait mieux utiliser l'héritage, regrouper jouerCarteAttaque(), jouerCartePop(), etc... en une seule fonction jouerCarte()


### Diagramme de séquence Mermaid:
```mermaid
sequenceDiagram
    actor Joueur1
    participant ControlJoueur1
    participant ControlCartePlateau
    participant ControlJoueur2
    participant Joueur2
    participant Carte
    
	Joueur1->>ControlJoueur1: jouer carte attaque 
    ControlJoueur1->>ControlJoueur1: jouerCarte()
    activate ControlJoueur1
    ControlJoueur1->>ControlCartePlateau: jouerCarteAttaque()
    activate ControlCartePlateau

    ControlCartePlateau->>Carte: getDegats()
    activate Carte
    Carte-->>ControlCartePlateau: degats
    deactivate Carte

    
    ControlCartePlateau->>ControlJoueur2: perdrePointsDeVie()


 

    activate ControlJoueur2
    ControlJoueur2->>Joueur2: getPointsdeVie()

    activate Joueur2
    Joueur2-->>ControlJoueur2: vie 
    deactivate Joueur2

    
    ControlJoueur2->>Joueur2: setVie()
    activate Joueur2
    Joueur2-->>ControlJoueur2: 
    deactivate Joueur2

    ControlJoueur2-->>ControlCartePlateau: 
    deactivate ControlJoueur2

    ControlCartePlateau-->>ControlJoueur1: 
    deactivate ControlCartePlateau


    deactivate ControlJoueur1
```