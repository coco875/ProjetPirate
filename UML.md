Acteurs :
- Jeu
- Marché
- Main
- Pioche
- Zone Attaque
- Zone Pop
- Défausse
- Zone Carte Spéciale
- Carte
- Carte Probabilité
- Carte Active
- Carte Passive
- Carte Spéciale
- Parser Carte
- txt
- Joueur

Contrôleurs :
- Control Jeu
- Control Marché
- Control Joueur
- Control Pioche
- Control Carte Plateau
- Control Carte Spéciale

Relations :
- Jeu interagit avec Control Jeu
- Marché interagit avec Control Marché
- Main interagit avec Control Joueur
- Pioche interagit avec Control Pioche
- Zone Attaque interagit avec Control Carte Plateau
- Zone Pop interagit avec Control Carte Plateau
- Défausse interagit avec Control Carte Plateau
- Zone Carte Spéciale interagit avec Control Carte Spéciale
- Carte interagit avec Control Carte Plateau
- Carte interagit avec Carte Probabilité
- Carte interagit avec Pioche
- Carte interagit avec Joueur
- Carte Probabilité interagit avec Parser Carte
- Parser Carte interagit avec txt
- Carte Active interagit avec Control Carte Plateau
- Carte Passive interagit avec Control Carte Plateau
- Carte Spéciale interagit avec Control Carte Spéciale

### Diagramme de classe (Mermaid)
```mermaid
classDiagram
    %% Entry Points %%
    class Main {
        + main(String[]) void
    }
    class MainUI {
        + main(String[]) void
        - lancerInterfaceGraphique() void
        - lancerInterfaceConsole() void
    }

    %% Boundaries %%
    class BoundaryJeu {
        - controlJeu: ControlJeu
        - controlMarche: ControlMarche
        - scan: Scanner
        + lancerJeu() void
        + jouerPartie() void
        + afficherMessage() void
        + afficherRegles() void
        + demarrerJeu() void
        + fermer() void
        # demanderNomJoueur(int) String
        # demanderNomPirate(int) String
        - jouerCarte(Carte, ControlJoueur) void
        - demanderContinuerIteration() boolean
        - afficherResultatFinal() void
    }
    class BoundaryJeuSwing {
        <<JFrame>>
        - controlJeu: ControlJeu
        - controlMarche: ControlMarche
        - joueurActuel: int
        - partieEnCours: boolean
        + BoundaryJeuSwing(ControlJeu, ControlMarche)
        - initUI() void
        - createMenuBar() void
        - createPlayerPanel(int) JPanel
        - afficherRegles() void
        - demarrerPartie() void
        - selectionnerCapitaine(String) Pirate
        - updatePlayerPanels() void
        - updatePlayerPanel(JPanel, int) void
        - createCardButton(Carte, int) JButton
        - updateCardDisplay() void
        - activerBoutonsTour(boolean) void
        - piocherCarte() void
        - jouerCarte(Carte, int) void
        - ouvrirMarche() void
        - finirTour() void
        - gererTailleMain() void
        - partieTerminee() void
        + main(String[]) void
    }

    %% Controllers %%
    class ControlJeu {
        - jeu: Jeu
        - controlPioche: ControlPioche
        - controlCartePlateau: ControlCartePlateau
        - controlCarteSpeciale: ControlCarteSpeciale
        - controlMarche: ControlMarche
        - controlJoueurs: ControlJoueur[]
        - joueurActif: int
        + initialiserJeu() void
        + setJoueur1(String, Pirate) void
        + setJoueur2(String, Pirate) void
        + creerJoueur(String, String) Joueur
        + distribuerCartesInitiales() void
        + ajouterCarteOffensive(CarteOffensive) void
        + ajouterCarteStrategique(CarteStrategique) void
        + appliquerEffetsCartes() void
        + defausserCartesPlateau() void
        + verifierFinPartie() boolean
        + determinerVainqueur() Joueur
        + getJoueur(int) ControlJoueur
        + passerAuJoueurSuivant() void
        + getJoueurActif() int
        + setJoueurActif(int) void
        + getControlMarche() ControlMarche
        + getControlPioche() ControlPioche
        + getControlCartePlateau() ControlCartePlateau
        + initialiserMainJoueur(int) void
        + piocherCarte() Carte
        + jouerCarte(int) boolean
        + verifierVictoire() int
        + defausserCarte(int) boolean
    }
    class ControlJoueur {
        - joueur: Joueur
        - controlJeu: ControlJeu
        - controlPioche: ControlPioche
        - controlCartePlateau: ControlCartePlateau
        - controlCarteSpeciale: ControlCarteSpeciale
        + ControlJoueur(Joueur, ControlJeu, ControlPioche)
        + setControlCartePlateau(ControlCartePlateau) void
        + setControlCarteSpeciale(ControlCarteSpeciale) void
        + getJoueur() Joueur
        + setJoueur(Joueur) void
        + initialiserMain() void
        + piocher() Carte
        + afficherMain() List~Carte~
        + retirerCarte(Carte) boolean
        + jouerCarte(int) boolean
        + jouerCarte(Carte) boolean
        + defausserCarte(int) boolean
        + perdrePointsDeVie(int) void
        + gagnerPointsDeVie(int) void
        + perdrePopularite(int) void
        + gagnerPopularite(int) void
        + perdreOr(int) boolean
        + gagnerOr(int) void
        + recevoirEffets(List~Carte~) void
        + recevoirEffets(int, int) void
        + jouerTour() void
        + getNom() String
        + getPointsDeVie() int
        + getPopularite() int
        + getOr() int
        + getPirate() Pirate
        + getMain() List~Carte~
    }
    class ControlPioche {
        - pioche: Pioche
        + ControlPioche()
        + initialiserPioche() void
        + estVide() boolean
        + piocher() Carte
        - chargerCartesDepuisRepertoire(File, List~Carte~) void
    }
    class ControlCartePlateau {
        - zoneOffensiveJ1: ZoneOffensive
        - zoneStrategiqueJ1: ZoneStrategique
        - zoneOffensiveJ2: ZoneOffensive
        - zoneStrategiqueJ2: ZoneStrategique
        - controlJoueur1: ControlJoueur
        - controlJoueur2: ControlJoueur
        - defausse: Defausse
        + ControlCartePlateau(ControlJoueur, ControlJoueur)
        + setJoueurs(ControlJoueur, ControlJoueur) void
        + ajouterCarteOffensiveJ1(CarteOffensive) void
        + ajouterCarteOffensiveJ2(CarteOffensive) void
        + ajouterCarteStrategiqueJ1(CarteStrategique) void
        + ajouterCarteStrategiqueJ2(CarteStrategique) void
        + appliquerEffetsCartesOffensives() void
        + appliquerEffetsCartesStrategiques() void
        + defausserCartesPlateau() void
        + getCartesOffensivesJ1() List~CarteOffensive~
        + getCartesOffensivesJ2() List~CarteOffensive~
        + getCartesStrategiquesJ1() List~CarteStrategique~
        + getCartesStrategiquesJ2() List~CarteStrategique~
        + getZoneOffensiveJ1() ZoneOffensive
        + getZoneOffensiveJ2() ZoneOffensive
        + getZoneStrategiqueJ1() ZoneStrategique
        + getZoneStrategiqueJ2() ZoneStrategique
        + getDefausse() Defausse
        - defausserCartes(List) void
    }
    class ControlCarteSpeciale {
        - joueur1: ControlJoueur
        - joueur2: ControlJoueur
        + ControlCarteSpeciale(ControlJoueur, ControlJoueur)
        + activerCarteSpeciale(CarteCoupSpecial) void
        + activerEffetSpecial(CarteCoupSpecial, ControlJoueur, ControlJoueur) void
        + appliquerEffetsCartes() void
        + defausserCartes() void
    }
    class ControlMarche {
        - marche: Marche
        - controlJoueur1: ControlJoueur
        - controlJoueur2: ControlJoueur
        - controlPioche: ControlPioche
        - controlJeu: ControlJeu
        + ControlMarche(ControlJoueur, ControlJoueur, ControlPioche, ControlJeu)
        - remplirMarcheInitial() void
        + rafraichirMarche() void
        + getCartesDisponibles() List~Carte~
        + acheterCarte(int) boolean
        + vendreCarte(int, int) boolean
    }

    %% Entities %%
    class Jeu {
        ' Représente l'état global du jeu
    }
    class Joueur {
        - nom: String
        - pirate: Pirate
        - vie: int
        - popularite: int
        - or: int
        - nbCartes: int
        - main: List~Carte~
        + Joueur(String, Pirate)
        + getPointsDeVie() int
        + setVie(Integer) void
        + getPopularite() int
        + setPopularite(Integer) void
        + getOr() int
        + setOr(Integer) void
        + getNbCartes() int
        + setNbCartes(Integer) void
        + getNom() String
        + getPirate() Pirate
        + perdrePointsDeVie(int) void
        + gagnerPointsDeVie(int) void
        + perdrePopularite(int) void
        + gagnerPopularite(int) void
        + perdreOr(int) boolean
        + gagnerOr(int) void
        + getPersonnage() Pirate
        + ajouterCarte(Carte) void
        + retirerCarte(Carte) boolean
        + getMain() List~Carte~
    }
    class Pirate {
        - nom: String
        - description: String
        - popularite: int
        - vie: int
        - carteCoupSpeciale: CarteCoupSpecial
        + Pirate(String, String, int, int)
        + Pirate(String)
        + getDescription() String
        + setDescription(String) void
        + getNom() String
        + getCarteCoupSpeciale() CarteCoupSpecial
        + setCarteCoupSpeciale(CarteCoupSpecial) void
        + getPopularite() int
        + setPopularite(int) void
        + getVie() int
    }
    class Pioche {
        - cartes: List~Carte~
        + Pioche()
        + ajouterCarte(Carte) void
        + melanger() void
        + piocher() Carte
        + estVide() boolean
        + getNombreCartes() int
    }
    class Defausse {
        - cartes: List~Carte~
        + Defausse()
        + ajouterCarte(Carte) void
        + getCartes() List~Carte~
        + vider() void
    }
    class Marche {
        - cartesDisponibles: List~Carte~
        + Marche()
        + ajouterCarte(Carte) void
        + getCartesDisponibles() List~Carte~
        + afficherMarché() void
    }
    class ZoneOffensive {
        - cartes: List~CarteOffensive~
        + ajouterCarte(CarteOffensive) void
        + viderZone() void
        + getCartesOffensives() List~CarteOffensive~
    }
    class ZoneStrategique {
        - cartes: List~CarteStrategique~
        + ajouterCarte(CarteStrategique) void
        + viderZone() void
        + getCartesStrategiques() List~CarteStrategique~
    }
    class ZoneCarteSpeciale {
        - cartes: List~CarteSpeciale~
        + ajouterCarte(CarteSpeciale) void
        + viderZone() void
        + getCartesSpeciales() List~CarteSpeciale~
    }

    %% Ressources et parsers %%
    class Ressource {
        - nom: String
        - valeur: int
        + Ressource(String, int)
        + getNom() String
        + getValeur() int
    }
    class ParserCarte {
        + lireCarte(String) Carte
    }
    class ParserPirate {
        + lirePirate(String) Pirate
    }

    %% Cartes %%
    class Carte {
        - type: TypeCarte
        - nomCarte: String
        - Description: String
        - valeur: int
        - valeurSecondaire: int
        - id: int
        - cout: int
        - orGagne: int
        - orPerdu: int
        - orVole: int
        - vieGagne: int
        - static compteurId: int
        + Carte(TypeCarte, String, String, int, int, int)
        + Carte(TypeCarte, String, String, int, int)
        + Carte(TypeCarte, String, String)
        + Carte(String, int, TypeCarte)
        + effetCarte() EffetCarte
        + getValeur() int
        + setValeur(int) void
        + getValeurSecondaire() int
        + setValeurSecondaire(int) void
        + getCout() int
        + setCout(int) void
        + getId() int
        + toString() String
        + setDescription(String) void
        + getDescription() String
        + getType() TypeCarte
        + setNomCarte(String) void
        + getNomCarte() String
        + getDegats() int
        + getDegatsInfliges() int
        + getDegatsSubis() int
        + getPopularite() int
        + getDegatsSubisPopularite() int
        + getOrGagne() int
        + setOrGagne(int) void
        + getOrPerdu() int
        + setOrPerdu(int) void
        + getOrVole() int
        + setOrVole(int) void
        + getVieGagne() int
        + setVieGagne(int) void
    }
    class EffetCarte {
        + degatsInfliges: int
        + degatsSubis: int
        + populariteGagnee: int
        + vieGagnee: int
        + orGagne: int
        + orPerdu: int
        + orVole: int
        + effetSpecial: String
        + dureeEffet: int
        + estAttaque: boolean
        + estPopularite: boolean
        + estSpeciale: boolean
        + estPassive: boolean
        + estTresor: boolean
        + estSoin: boolean
    }
    class TypeCarte {
        <<enumeration>>
        OFFENSIVE
        STRATEGIQUE
        SPECIALE
        ACTIVE
        PASSIVE
        PROBABILITE
        TRESOR
    }
    class CarteOffensive {
        - typeOffensif: TypeOffensif
        - estJouee: boolean
        - coutSpecial: int
        + CarteOffensive(String, String, int, int, TypeOffensif)
        + CarteOffensive(String, String, int, int, TypeOffensif, int)
        + CarteOffensive(String, String, int)
        + CarteOffensive(String, String, int, int)
        + CarteOffensive(String, String, int, boolean)
        + fromCarteAttaque(CarteAttaque) CarteOffensive
        + fromCarteSoin(CarteSoin) CarteOffensive
        + fromCarteCoupSpecial(CarteCoupSpecial) CarteOffensive
        + getTypeOffensif() TypeOffensif
        + setTypeOffensif(TypeOffensif) void
        + estJouee() boolean
        + setEstJouee(boolean) void
        + getCoutSpecial() int
        + setCoutSpecial(int) void
        + estAttaqueDirecte() boolean
        + estCoupSpecial() boolean
        + estSoin() boolean
        + estTresorOffensif() boolean
        + getDegatsInfliges() int
        + getDegatsSubis() int
        + getVieGagnee() int
        + getOrVole() int
        + toString() String
    }
    class TypeOffensif {
        <<enumeration>>
        ATTAQUE_DIRECTE
        COUP_SPECIAL
        SOIN
        TRESOR_OFFENSIF
    }
    class CarteStrategique {
        - typeStrategique: TypeStrategique
        - effetSpecial: String
        - typeEffet: String
        - duree: int
        + estPopularite() boolean
        + estTresor() boolean
        + estSpeciale() boolean
        + estPassive() boolean
        + getPopulariteGagnee() int
        + getDegatsSubis() int
        + getOrGagne() int
        + getOrPerdu() int
        + getTypeEffet() String
        + getDuree() int
        + getEffetSpecial() String
        + getTypeStrategique() TypeStrategique
        + setTypeStrategique(TypeStrategique) void
    }
    class TypeStrategique {
        <<enumeration>>
        POPULARITE
        TRESOR
        SPECIALE
        PASSIVE
    }
    class CarteAttaque {
        + CarteAttaque(String, String, int)
        + CarteAttaque(String, String, int, int)
        + CarteAttaque(String, String, int, int, int)
    }
    class CarteSoin {
        + CarteSoin(String, String, int)
    }
    class CarteTresor {
        + CarteTresor(String, String, int, int, int)
    }
    class CartePopularite {
        + CartePopularite(String, String, int, int)
        + CartePopularite(String, String, int, int, int)
    }
    class CarteSpeciale {
        - effetSpecial: String
        - estReutilisable: boolean
        + CarteSpeciale(String, String, String, int)
        + CarteSpeciale(String, String, String, int, int)
        + estReutilisable() boolean
        + setReutilisable(boolean) void
    }
    class CarteCoupSpecial {
        + CarteCoupSpecial(String, String, int, int)
    }
    class CarteActive {
        - estJouee: boolean
        + CarteActive(TypeCarte, String, String, int, int)
        + CarteActive(TypeCarte, String, String, int)
        + CarteActive(TypeCarte, String, String)
        + estJouee() boolean
        + setEstJouee(boolean) void
    }
    class CartePassive {
        + duree: int
    }
    class CarteProbabilite {
        + probabilite: double
    }

    %% Relationships %%
    Main --> BoundaryJeu : crée
    Main --> ControlJeu : crée
    MainUI --> BoundaryJeu : crée
    MainUI --> BoundaryJeuSwing : crée
    MainUI --> ControlJeu : crée

    BoundaryJeu --> ControlJeu : utilise
    BoundaryJeu --> ControlMarche : utilise
    BoundaryJeuSwing --> ControlJeu : utilise
    BoundaryJeuSwing --> ControlMarche : utilise

    ControlJeu "1" *--> "2" ControlJoueur : contrôle
    ControlJeu --> ControlPioche : utilise
    ControlJeu --> ControlCartePlateau : crée et utilise
    ControlJeu --> ControlCarteSpeciale : crée et utilise
    ControlJeu --> ControlMarche : crée et utilise
    ControlJeu --> Jeu : gère

    ControlJoueur --> Joueur : gère
    ControlJoueur --> ControlJeu : référence
    ControlJoueur --> ControlPioche : utilise pour piocher
    ControlJoueur --> ControlCartePlateau : accède aux zones
    ControlJoueur --> ControlCarteSpeciale : gère les effets spéciaux

    ControlPioche --> Pioche : gère
    ControlPioche ..> ParserCarte : utilise

    ControlCartePlateau --> ZoneOffensive : gère
    ControlCartePlateau --> ZoneStrategique : gère
    ControlCartePlateau --> Defausse : gère
    ControlCartePlateau --> ControlJoueur : applique les effets

    ControlCarteSpeciale --> ControlJoueur : applique les effets
    ControlCarteSpeciale ..> CarteCoupSpecial : active

    ControlMarche --> Marche : gère
    ControlMarche --> ControlJoueur : interagit avec
    ControlMarche --> ControlPioche : utilise
    ControlMarche --> ControlJeu : référence

    Joueur --> Pirate : possède
    Joueur --> "*" Carte : main

    Pirate --> CarteCoupSpecial : peut avoir

    Pioche --> "*" Carte : contient
    Defausse --> "*" Carte : contient
    Marche --> "*" Carte : propose

    ZoneOffensive --> "*" CarteOffensive : contient
    ZoneStrategique --> "*" CarteStrategique : contient
    ZoneCarteSpeciale --> "*" CarteSpeciale : contient

    Carte --> TypeCarte : est de type
    Carte ..> EffetCarte : crée
    Carte --> "*" Ressource : peut utiliser

    CarteOffensive --|> Carte : hérite
    CarteOffensive --> TypeOffensif : est de type

    CarteStrategique --|> Carte : hérite
    CarteStrategique --> TypeStrategique : est de type

    CarteAttaque --|> CarteOffensive : hérite
    CarteSoin --|> CarteOffensive : hérite
    CarteTresor --|> Carte : hérite
    CartePopularite --|> CarteStrategique : hérite
    CarteSpeciale --|> CarteStrategique : hérite
    CarteCoupSpecial --|> CarteOffensive : hérite
    CarteActive --|> Carte : hérite
    CartePassive --|> CarteStrategique : hérite
    CarteProbabilite --|> Carte : hérite

    ParserCarte ..> Carte : crée
    ParserPirate ..> Pirate : crée
```