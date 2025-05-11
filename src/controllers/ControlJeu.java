package controllers;

import carte.CarteOffensive;
import carte.CarteStrategique;
import jeu.Jeu;
import jeu.ZoneOffensive; // Renamed from ZoneAttaque
import jeu.ZoneStrategique;
import joueur.Joueur;
import joueur.Pirate;

/**
 * Contrôleur principal du jeu
 * Coordonne les différents aspects du jeu des Pirates
 */
public class ControlJeu {
    private Jeu jeu;
    private ControlPioche controlPioche;
    private ControlCartePlateau controlCartePlateau;
    private ControlMarche controlMarche;
    private ControlJoueur[] controlJoueurs; // Tableau des contrôleurs de joueurs
    private int joueurActif; // 0 pour joueur 1, 1 pour joueur 2

    /**
     * Constructeur du contrôleur
     */
    public ControlJeu() {
        this.jeu = new Jeu();
        this.controlPioche = new ControlPioche();
        this.controlJoueurs = new ControlJoueur[2]; // Initialisation du tableau
        this.joueurActif = 0; // Le joueur 1 commence
    }
    
    /**
     * Initialise le jeu
     */
    public void initialiserJeu() {
        // Initialisation de la pioche
        controlPioche.initialiserPioche();

        controlCartePlateau = new ControlCartePlateau(controlJoueurs[0], controlJoueurs[1]);
        controlJoueurs[0].setControlCartePlateau(controlCartePlateau);
        controlJoueurs[1].setControlCartePlateau(controlCartePlateau);
        
        controlMarche = new ControlMarche(controlJoueurs[0], controlJoueurs[1], controlPioche, this);
        distribuerCartesInitiales();
        // Les contrôleurs de joueurs seront créés lors de la création des joueurs
    }
    
    /**
     * Crée le joueur 1 (pour tests)
     */
    public void setJoueur1(Pirate pirate) {
        Joueur joueur = new Joueur(pirate);
        controlJoueurs[0] = new ControlJoueur(joueur, this, controlPioche);
    }
    
    /**
     * Crée le joueur 2 (pour tests)
     */
    public void setJoueur2(Pirate pirate) {
        Joueur joueur = new Joueur(pirate);
        controlJoueurs[1] = new ControlJoueur(joueur, this, controlPioche);
    }
    
    /**
     * Crée un joueur
     * @param nomJoueur Nom du joueur
     * @param nomPirate Nom du pirate
     * @return Le joueur créé
     */
    public void creerJoueur(Pirate pirate) {
        // Créer le joueur
        Joueur joueur = new Joueur(pirate);
        
        // Déterminer l'index du joueur (0 pour le premier, 1 pour le second)
        int indexJoueur = (controlJoueurs[0] == null) ? 0 : 1;
        
        // Créer le contrôleur de joueur
        controlJoueurs[indexJoueur] = new ControlJoueur(joueur, this, controlPioche);
    }
    
    /**
     * Distribue les cartes initiales aux joueurs
     */
    public void distribuerCartesInitiales() {
        // Chaque joueur reçoit 3 cartes au début
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                controlJoueurs[i].piocher();
            }
        }
    }
    
    /**
     * Ajoute une carte offensive au plateau pour le joueur actif
     */
    public void ajouterCarteOffensive(CarteOffensive carte) {
        if (joueurActif == 0) {
            controlCartePlateau.ajouterCarteOffensiveJ1(carte);
        } else {
            controlCartePlateau.ajouterCarteOffensiveJ2(carte);
        }
    }
    
    /**
     * Ajoute une carte stratégique au plateau pour le joueur actif
     */
    public void ajouterCarteStrategique(CarteStrategique carte) {
        if (joueurActif == 0) {
            controlCartePlateau.ajouterCarteStrategiqueJ1(carte);
        } else {
            controlCartePlateau.ajouterCarteStrategiqueJ2(carte);
        }
    }
    
    /**
     * Applique les effets de toutes les cartes sur le plateau
     */
    public void appliquerEffetsCartes() {
        // Appliquer les effets des cartes d'attaque
        controlCartePlateau.appliquerEffetsCartesOffensives();
        
        // Appliquer les effets des cartes de popularité
        controlCartePlateau.appliquerEffetsCartesStrategiques();
    }
    
    /**
     * Défausse toutes les cartes du plateau
     */
    public void defausserCartesPlateau() {
        controlCartePlateau.defausserCartesPlateau();
    }
    
    /**
     * Vérifie si la partie est terminée
     * @return true si la partie est terminée, false sinon
     */
    public boolean verifierFinPartie() {
        // Vérifier si un des joueurs est mort (0 point de vie)
        for (int i = 0; i < 2; i++) {
            if (controlJoueurs[i].getJoueur().getPointsDeVie() <= 0) {
                return true;
            }
        }
        
        // Vérifier si un des joueurs a atteint le maximum de popularité
        for (int i = 0; i < 2; i++) {
            if (controlJoueurs[i].getJoueur().getPopularite() >= 5) {
                return true;
            }
        }
        
        // Vérifier si la pioche est vide
        return controlPioche.estVide();
    }
    
    /**
     * Détermine le vainqueur de la partie
     * @return Le joueur vainqueur ou null en cas d'égalité
     */
    public Joueur determinerVainqueur() {
        Joueur joueur1 = controlJoueurs[0].getJoueur();
        Joueur joueur2 = controlJoueurs[1].getJoueur();
        
        // Si un joueur est mort, l'autre gagne
        if (joueur1.getPointsDeVie() <= 0) {
            return joueur2;
        } else if (joueur2.getPointsDeVie() <= 0) {
            return joueur1;
        }
        
        // Si un joueur a atteint le maximum de popularité, il gagne
        if (joueur1.getPopularite() >= 5) {
            return joueur1;
        } else if (joueur2.getPopularite() >= 5) {
            return joueur2;
        }
        
        // Sinon, le joueur avec le plus de points combinés gagne
        int score1 = joueur1.getPointsDeVie() + joueur1.getPopularite() + (joueur1.getOr() / 2);
        int score2 = joueur2.getPointsDeVie() + joueur2.getPopularite() + (joueur2.getOr() / 2);
        
        if (score1 > score2) {
            return joueur1;
        } else if (score2 > score1) {
            return joueur2;
        } else {
            // En cas d'égalité parfaite
            return null;
        }
    }
    
    // Getters et méthodes de gestion du jeu
    
    public ControlJoueur getJoueur(int index) {
        return controlJoueurs[index];
    }
    
    public ControlCartePlateau getControlCartePlateau() {
        return controlCartePlateau;
    }
    
    /**
     * Passe au joueur suivant
     */
    public void passerAuJoueurSuivant() {
        joueurActif = (joueurActif + 1) % 2;
    }
    
    public int getJoueurActif() {
        return joueurActif;
    }
    
    /**
     * Définit le joueur actif
     * @param index Index du joueur actif (0 ou 1)
     */
    public void setJoueurActif(int index) {
        if (index == 0 || index == 1) {
            this.joueurActif = index;
        }
    }
    
    public ControlMarche getControlMarche() {
        return controlMarche;
    }
    
    public ControlPioche getControlPioche() {
        return controlPioche;
    }
    
    /**
     * Termine l'itération actuelle et vérifie si le jeu continue
     * @param continuer Indique si les joueurs souhaitent continuer le jeu
     * @return true si le jeu doit continuer, false s'il doit se terminer
     */
    public boolean terminerIteration(boolean continuer) {
        // Si les joueurs ne souhaitent pas continuer, on arrête le jeu
        if (!continuer) {
            return false;
        }
        
        // Vérifier si la partie est terminée selon les règles du jeu
        if (verifierFinPartie()) {
            return false;
        }
        
        // Défausser les cartes du plateau pour la prochaine itération
        defausserCartesPlateau();
        
        // Faire piocher une carte à chaque joueur pour la prochaine itération
        for (int i = 0; i < 2; i++) {
            controlJoueurs[i].piocher();
        }
        
        // Réinitialiser le joueur actif au premier joueur
        joueurActif = 0;
        
        return true;
    }
    
    /**
     * Initialise la main du joueur
     */
    public void initialiserMainJoueur(int indexJoueur) {
        // Vérifier que l'index du joueur est valide
        if (indexJoueur < 0 || indexJoueur >= controlJoueurs.length || controlJoueurs[indexJoueur] == null) {
            return;
        }
        
        // Initialiser la main du joueur avec 3 cartes
        for (int i = 0; i < 3; i++) {
            controlJoueurs[indexJoueur].piocher();
        }
    }
    
    /**
     * Fait piocher une carte au joueur actif
     * @return La carte piochée ou null si la pioche est vide
     */
    public carte.Carte piocherCarte() {
        if (joueurActif >= 0 && joueurActif < controlJoueurs.length && controlJoueurs[joueurActif] != null) {
            return controlJoueurs[joueurActif].piocher();
        }
        return null;
    }
    
    /**
     * Fait jouer une carte au joueur actif
     * @param indexCarte Index de la carte à jouer dans la main du joueur
     * @return true si la carte a été jouée avec succès, false sinon
     */
    public boolean jouerCarte(int indexCarte) {
        if (joueurActif >= 0 && joueurActif < controlJoueurs.length && controlJoueurs[joueurActif] != null) {
            return controlJoueurs[joueurActif].jouerCarte(indexCarte);
        }
        return false;
    }
    
    /**
     * Vérifie si un joueur a gagné la partie
     * @return Numéro du joueur gagnant (1 ou 2) ou 0 s'il n'y a pas de gagnant
     */
    public int verifierVictoire() {
        if (verifierFinPartie()) {
            Joueur vainqueur = determinerVainqueur();
            
            if (vainqueur == controlJoueurs[0].getJoueur()) {
                return 1;
            } else if (vainqueur == controlJoueurs[1].getJoueur()) {
                return 2;
            }
        }
        return 0; // Pas de gagnant pour l'instant
    }
    
    /**
     * Défausse une carte de la main du joueur actif
     * @param indexCarte Index de la carte à défausser
     * @return true si la carte a été défaussée avec succès, false sinon
     */
    public boolean defausserCarte(int indexCarte) {
        if (joueurActif >= 0 && joueurActif < controlJoueurs.length && controlJoueurs[joueurActif] != null) {
            return controlJoueurs[joueurActif].defausserCarte(indexCarte);
        }
        return false;
    }
}
