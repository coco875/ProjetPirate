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
    participant controlJoueur1
    participant controlJoueur2
    participant joueur1
    participant joueur2
    participant ControlZoneJoueur
    participant Carte
    participant ZoneOffensive
    
	Joueur1-->>BoundaryJeu: jouer carte (Carte Choisie)
    BoundaryJeu->>BoundaryJeu: jouerCarte(carteChoisie: Carte)
    activate BoundaryJeu
    BoundaryJeu->>Carte: effetCarte()
    activate Carte
    Carte-->>BoundaryJeu: effet: Carte.EffetCarte
    deactivate Carte
    Note right of BoundaryJeu: BoundaryJeu accède aux <br/>attributs de effet

    BoundaryJeu-->>Joueur1: Vous attaquez ! Dégâts infligés : 2
    BoundaryJeu-->>Joueur1: Vous subissez 1 points de dégâts en retour.

    BoundaryJeu->>ControlJeu: jouerCarte(carte: Carte)

    activate ControlJeu
    ControlJeu->>controlJoueur1: joueurCarte(carte: Carte)

    activate controlJoueur1
    controlJoueur1->>joueur1: getMain()
    activate joueur1
    joueur1-->>controlJoueur1: main: List<Carte>
    deactivate joueur1

    controlJoueur1->>controlJoueur1: main.indexOf(carte: Carte)
    controlJoueur1->>Carte: getType()

    activate Carte
    Carte-->>controlJoueur1: TypeCarte.OFFENSIVE
    deactivate Carte

    controlJoueur1->>ControlZoneJoueur: ajouterCarteOffensive((CarteOffensive)carte : CarteOffensive)
    
    activate ControlZoneJoueur
    ControlZoneJoueur->>ZoneOffensive: ajouterCarte(carte: CarteOffensive)
    

    activate ZoneOffensive
    ZoneOffensive->>ZoneOffensive: cartes.add(carte: CarteOffensive)
    ZoneOffensive-->>ControlZoneJoueur: 
    deactivate ZoneOffensive

    ControlZoneJoueur-->>controlJoueur1: 
    deactivate ControlZoneJoueur

    controlJoueur1->>joueur1: retirerCarte(carte: Carte)

    activate joueur1
    joueur1->>joueur1: main.remove(carte: Carte)
    joueur1-->>controlJoueur1: true
    deactivate joueur1

    controlJoueur1-->>ControlJeu: true
    deactivate controlJoueur1


    ControlJeu-->>BoundaryJeu: true
    deactivate ControlJeu
    
    

    BoundaryJeu->>ControlJeu: appliquerEffetsCartes()

    activate ControlJeu
    ControlJeu->>ControlCartePlateau: appliquerEffetCarte()

    activate ControlCartePlateau
    ControlCartePlateau->>ControlZoneJoueur: getZoneOffensive()
    
    activate ControlZoneJoueur
    ControlZoneJoueur-->>ControlCartePlateau: zoneOffensiveJ1: ZoneOffensive
    deactivate ControlZoneJoueur
    
    ControlCartePlateau->>ZoneOffensive: getCartesOffensives()
    activate ZoneOffensive
    ZoneOffensive-->>ControlCartePlateau: cartesStrategique: List<Cartes>
    deactivate ZoneOffensive
    
	loop for carte in cartesOffensives
		Note right of ControlJeu: Ici une seul itération car une seule carte jouée
		ControlCartePlateau->>Carte: effetCarte()
		activate Carte
		Carte-->>ControlCartePlateau: effet: Carte.EffetCarte
		deactivate Carte

		ControlCartePlateau->>ControlCartePlateau: appliquerEffetCarte(effet: EffetCarte, <br/>controlJoueur1: ControlJoueur, controlJoueur2: ControlJoueur)
		ControlCartePlateau->>controlJoueur1: perdrePointsDeVie(effet.degatsSubis)

		activate controlJoueur1
		controlJoueur1->>joueur1: perdrePointsDeVie(degats: Int)

		activate joueur1
		joueur1-->>controlJoueur1: 
		deactivate joueur1

		controlJoueur1-->>ControlCartePlateau: 
		deactivate controlJoueur1
		
		ControlCartePlateau->>controlJoueur2: perdrePointsDeVie(effet.degatsSubis: Int)

		activate controlJoueur2
		controlJoueur2->>joueur2: perdrePointsDeVie(degats: Int)

		activate joueur2
		joueur2-->>controlJoueur2: 
		deactivate joueur2

		controlJoueur2-->>ControlCartePlateau: 
		deactivate controlJoueur2
	end


    ControlCartePlateau-->>ControlJeu: 
    deactivate ControlCartePlateau

    ControlJeu-->>BoundaryJeu: 
    deactivate ControlJeu

    deactivate BoundaryJeu
    
    
```


```mermaid
sequenceDiagram
    actor Joueur1
    participant BoundaryJeu
    participant ControlJeu
    participant ControlCartePlateau
    participant controlJoueur1
    participant controlJoueur2
    participant joueur1
    participant joueur2
    participant ControlZoneJoueur
    participant Carte
    participant ZoneStrategique
    
	Joueur1-->>BoundaryJeu: jouer carte (Carte Choisie)
    BoundaryJeu->>BoundaryJeu: jouerCarte(carteChoisie: Carte)
    activate BoundaryJeu
    BoundaryJeu->>Carte: effetCarte()
    activate Carte
    Carte-->>BoundaryJeu: effet: Carte.EffetCarte
    deactivate Carte
    Note right of BoundaryJeu: BoundaryJeu accède aux <br/>attributs de effet

    BoundaryJeu-->>Joueur1: Vous gagnez en popularité ! Points gagnés : 2
    BoundaryJeu-->>Joueur1: Vous subissez 1 points de dégâts en retour.

    BoundaryJeu->>ControlJeu: jouerCarte(carte: Carte)

    activate ControlJeu
    ControlJeu->>controlJoueur1: joueurCarte(carte: Carte)

    activate controlJoueur1
    controlJoueur1->>joueur1: getMain()
    activate joueur1
    joueur1-->>controlJoueur1: main: List<Carte>
    deactivate joueur1

    controlJoueur1->>controlJoueur1: main.indexOf(carte: Carte)
    controlJoueur1->>Carte: getType()

    activate Carte
    Carte-->>controlJoueur1: TypeCarte.STRATEGIQUE
    deactivate Carte

    controlJoueur1->>ControlZoneJoueur: ajouterCarteStrategique((CarteStrategique)carte : CarteStrategique)
    
    activate ControlZoneJoueur
    ControlZoneJoueur->>ZoneStrategique: ajouterCarte(carte: CarteStrategique)
    

    activate ZoneStrategique
    ZoneStrategique->>ZoneStrategique: cartes.add(carte: CarteStrategique)
    ZoneStrategique-->>ControlZoneJoueur: 
    deactivate ZoneStrategique

    ControlZoneJoueur-->>controlJoueur1: 
    deactivate ControlZoneJoueur

    controlJoueur1->>joueur1: retirerCarte(carte: Carte)

    activate joueur1
    joueur1->>joueur1: main.remove(carte: Carte)
    joueur1-->>controlJoueur1: true
    deactivate joueur1

    controlJoueur1-->>ControlJeu: true
    deactivate controlJoueur1


    ControlJeu-->>BoundaryJeu: true
    deactivate ControlJeu
    
    

    BoundaryJeu->>ControlJeu: appliquerEffetsCartes()

    activate ControlJeu
    ControlJeu->>ControlCartePlateau: appliquerEffetCarte()

    activate ControlCartePlateau
    ControlCartePlateau->>ControlZoneJoueur: getZoneStrategique()
    
    activate ControlZoneJoueur
    ControlZoneJoueur-->>ControlCartePlateau: zoneStrategiqueJ1: ZoneStrategique
    deactivate ControlZoneJoueur
    
    ControlCartePlateau->>ZoneStrategique: getCartesStrategiques()
    activate ZoneStrategique
    ZoneStrategique-->>ControlCartePlateau: cartesStrategique: List<Cartes>
    deactivate ZoneStrategique
    
	loop for carte in cartesStrategiques
		Note right of ControlJeu: Ici une seul itération car une seule carte jouée
		ControlCartePlateau->>Carte: effetCarte()
		activate Carte
		Carte-->>ControlCartePlateau: effet: Carte.EffetCarte
		deactivate Carte

		ControlCartePlateau->>ControlCartePlateau: appliquerEffetCarte(effet: EffetCarte, <br/>controlJoueur1: ControlJoueur, controlJoueur2: ControlJoueur)
		ControlCartePlateau->>controlJoueur1: perdrePointsDeVie(effet.degatsSubis)

		activate controlJoueur1
		controlJoueur1->>joueur1: perdrePointsDeVie(degats: Int)

		activate joueur1
		joueur1-->>controlJoueur1: 
		deactivate joueur1

		controlJoueur1-->>ControlCartePlateau: 
		deactivate controlJoueur1
		
		ControlCartePlateau->>controlJoueur1: gagnerPopularite(effet.populariteGagnee: Int)
		
		activate controlJoueur1
		controlJoueur1->>joueur1: gagnerPopularite(pointsAGagner: Int)

		activate joueur1
		joueur1-->>controlJoueur1: 
		deactivate joueur1

		controlJoueur1-->>ControlCartePlateau: 
		deactivate controlJoueur1
	end


    ControlCartePlateau-->>ControlJeu: 
    deactivate ControlCartePlateau

    ControlJeu-->>BoundaryJeu: 
    deactivate ControlJeu

    deactivate BoundaryJeu
```
