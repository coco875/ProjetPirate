# Template Diagramme UML de séquence pour utilisation d'une carte Attaque
Rappel: deux diagrammes de séquence à faire, représentant le fonctionnement de 2 cartes différentes

Note: ControlCartePlateau::appliquerEffetsCartes() appelle des méthodes des Joueurs directement au lieu de passer par leur controleur,
 j'ai représenté ça autrement sur le diagramme (en appelant recevoirEffets des controleurs) le temps qu'on change ça


### Diagramme de séquence Mermaid:
```mermaid
sequenceDiagram
    actor Joueur1
    participant BoundaryJeu

    participant ControlJeu
    participant ControlCartePlateau
    participant ControlJoueur1
    participant ControlJoueur2
    participant Joueur2
    participant Carte
    participant Effet 
    participant CarteOffensive
    participant ZoneOffensive
    
	Joueur1->>BoundaryJeu: jouer carte (index)
    BoundaryJeu->>BoundaryJeu: jouerCarte()
    activate BoundaryJeu
    BoundaryJeu->>Carte: effetCarte()
    activate Carte
    Carte-->>BoundaryJeu: effet
    deactivate Carte
    Note right of BoundaryJeu: BoundaryJeu accède aux <br/>attributs de effet

    BoundaryJeu-->>Joueur1: Vous attaquez ! Dégâts infligés : 2
    BoundaryJeu-->>Joueur1: Vous subissez 1 points de dégâts en retour.

    BoundaryJeu->>ControlJeu: ajouterCarteOffensive()

    activate ControlJeu
    ControlJeu->>ControlCartePlateau: ajouterCarteOffensiveJ1()

    activate ControlCartePlateau
    ControlCartePlateau->>ZoneOffensive: ajouterCarte()

    activate ZoneOffensive
    ZoneOffensive-->>ControlCartePlateau: 
    deactivate ZoneOffensive

    ControlCartePlateau-->>ControlJeu: 
    deactivate ControlCartePlateau

    ControlJeu-->>BoundaryJeu: 
    deactivate ControlJeu

    deactivate BoundaryJeu

    activate BoundaryJeu
    BoundaryJeu->>ControlJeu: appliquerEffetsCartes()
    
    activate ControlJeu
    ControlJeu->>ControlCartePlateau: appliquerEffetsCartesOffensives()

    activate ControlCartePlateau

    loop foreach carte

        ControlCartePlateau->>ZoneOffensive: getCartesOffensives()
        activate ZoneOffensive
        ZoneOffensive-->>ControlCartePlateau: carte
        deactivate ZoneOffensive

        ControlCartePlateau->>Carte: effetCarte()
        activate Carte
        Carte-->>ControlCartePlateau: effet
        deactivate Carte

        ControlCartePlateau->>Carte: getTypeOffensif()
        activate Carte
        Carte-->>ControlCartePlateau: ATTAQUE_DIRECTE
        deactivate Carte

        Note right of ControlCartePlateau: CCartePlateau accède <br/>aux attributs de effet
    end

    ControlCartePlateau->>ControlJoueur2: recevoirEffets()
    activate ControlJoueur2
    ControlJoueur2->>ControlJoueur2: perdrePointsDeVie()
    ControlJoueur2->>Joueur2: perdrePointsDeVie()
    activate Joueur2
    Joueur2-->>ControlJoueur2: 
    deactivate Joueur2
    ControlJoueur2-->>ControlCartePlateau: 
    deactivate ControlJoueur2

    ControlCartePlateau-->>ControlJeu: 
    deactivate ControlCartePlateau

    ControlJeu-->>BoundaryJeu: 
    deactivate ControlJeu

    deactivate BoundaryJeu
```