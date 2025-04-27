package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import carte.Carte;
import carte.CarteAttaque;
import carte.CartePopularite;
import carte.CarteSpeciale;
import joueur.Joueur;

/**
 * @brief Contrôleur gérant les interactions avec le marché
 * 
 * Ce contrôleur gère l'achat et la vente de cartes au marché,
 * conformément au modèle ECB.
 */
public class ControlMarche {
    private List<Carte> cartesDisponibles;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private ControlPioche controlPioche;
    private Random random;
    
    public ControlMarche(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2, ControlPioche controlPioche) {
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
        this.controlPioche = controlPioche;
        this.cartesDisponibles = new ArrayList<>();
        this.random = new Random();
        
        // Initialiser le marché avec quelques cartes
        rafraichirMarche();
    }

    /**
     * @brief Rafraîchit les cartes disponibles au marché
     */
    public void rafraichirMarche() {
        cartesDisponibles.clear();
        
        // Ajouter 3 cartes aléatoires au marché
        for (int i = 0; i < 3; i++) {
            int type = random.nextInt(3); // 0: attaque, 1: popularité, 2: spéciale
            Carte carte;
            
            switch (type) {
                case 0:
                    carte = new CarteAttaque("Épée du corsaire", "Une épée puissante qui inflige des dégâts importants", 2, 3);
                    break;
                case 1:
                    carte = new CartePopularite("Trésor caché", "Un trésor qui augmente grandement votre popularité", 3, 2);
                    break;
                case 2:
                    carte = new CarteSpeciale("Carte spéciale du capitaine", "Une carte avec des pouvoirs spéciaux", "Effet spécial unique", 2);
                    break;
                default:
                    carte = new CarteAttaque("Canon", "Un canon pour attaquer l'adversaire", 1, 2);
            }
            
            cartesDisponibles.add(carte);
        }
    }

    /**
     * @brief Obtient la liste des cartes disponibles au marché
     */
    public List<Carte> getCartesDisponibles() {
        if (cartesDisponibles.isEmpty()) {
            rafraichirMarche();
        }
        return cartesDisponibles;
    }

    /**
     * @brief Achète une carte du marché pour un joueur
     * @param joueurId L'ID du joueur qui achète la carte
     * @param indexCarte L'index de la carte à acheter dans la liste des cartes disponibles
     * @return true si l'achat a réussi, false sinon
     */
    public boolean acheterCarte(int joueurId, int indexCarte) {
        if (indexCarte < 0 || indexCarte >= cartesDisponibles.size()) {
            return false;
        }
        
        Carte carteAcheter = cartesDisponibles.get(indexCarte);
        Joueur joueur;
        
        if (joueurId == 1) {
            joueur = controlJoueur1.getJoueur();
        } else if (joueurId == 2) {
            joueur = controlJoueur2.getJoueur();
        } else {
            return false;
        }
        
        int cout = carteAcheter.getCout();
        
        // Vérifier si le joueur a assez d'or
        if (joueur.getOr() >= cout) {
            // Déduire le coût et ajouter la carte à la main du joueur
            joueur.setOr(joueur.getOr() - cout);
            joueur.ajouterCarte(carteAcheter);
            
            // Retirer la carte des cartes disponibles
            cartesDisponibles.remove(indexCarte);
            
            // Si le marché est vide, le rafraîchir
            if (cartesDisponibles.isEmpty()) {
                rafraichirMarche();
            }
            
            return true;
        }
        
        return false;
    }

    /**
     * @brief Vend une carte d'un joueur au marché
     * @param joueurId L'ID du joueur qui vend la carte
     * @param indexCarte L'index de la carte à vendre dans la main du joueur
     * @return true si la vente a réussi, false sinon
     */
    public boolean vendreCarte(int joueurId, int indexCarte) {
        Joueur joueur;
        
        if (joueurId == 1) {
            joueur = controlJoueur1.getJoueur();
        } else if (joueurId == 2) {
            joueur = controlJoueur2.getJoueur();
        } else {
            return false;
        }
        
        if (indexCarte < 0 || indexCarte >= joueur.getMain().size()) {
            return false;
        }
        
        Carte carteVendre = joueur.getMain().get(indexCarte);
        
        // Retirer la carte de la main du joueur et lui donner de l'or
        joueur.retirerCarte(carteVendre);
        int valeurVente = carteVendre.getValeur() / 2; // La valeur de vente est la moitié de la valeur de la carte
        if (valeurVente <= 0) valeurVente = 1; // Minimum 1 or
        
        joueur.setOr(joueur.getOr() + valeurVente);
        
        return true;
    }
}