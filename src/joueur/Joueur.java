package joueur;

import java.util.ArrayList;
import java.util.List;
import carte.Carte;

/**
 * Classe représentant un joueur dans le jeu des Pirates
 */
public class Joueur {
	private Pirate pirate;
	private Integer vie;         // max 5
	private Integer popularite;  // max 5
	private Integer or;
	private Integer nbCartes;
	private List<Carte> main;
	
	/**
	 * Constructeur d'un joueur
	 */
	public Joueur(Pirate pirate) {
		this.pirate = pirate;
		
		this.vie = 5;
		this.popularite = 0;
		this.or = 3;  // Modifié de 0 à 3
		this.nbCartes = 0;
		this.main = new ArrayList<Carte>(4);
	}

	public Carte getCarteSpecial() {
		return pirate.getCarteSpecial();
	}

	// Getters et setters de base
	
	public int getPointsDeVie() {
		return vie;
	}

	public void setVie(Integer vie) {
		this.vie = vie;
	}

	public int getPopularite() {
		return popularite;
	}

	public void setPopularite(Integer popularite) {
		this.popularite = popularite;
	}

	public Integer getOr() {
		return or;
	}

	public void setOr(Integer or) {
		this.or = or;
	}

	public Integer getNbCartes() {
		return nbCartes;
	}

	public void setNbCartes(Integer nbCartes) {
		this.nbCartes = nbCartes;
	}

	public String getNom() {
		return pirate.getNom();
	}

	public Pirate getPirate() {
		return pirate;
	}
	
	/**
	 * Le joueur perd des points de vie
	 */
	public void perdrePointsDeVie(int points) {
		this.vie -= points;
		if (this.vie < 0) {
			this.vie = 0;
		}
	}
	
	/**
	 * Le joueur gagne des points de vie
	 */
	public void gagnerPointsDeVie(int points) {
		this.vie += points;
		if (this.vie > 5) {
			this.vie = 5;
		}
	}
	
	/**
	 * Le joueur perd des points de popularité
	 */
	public void perdrePopularite(int points) {
		this.popularite -= points;
		if (this.popularite < 0) {
			this.popularite = 0;
		}
	}
	
	/**
	 * Le joueur gagne des points de popularité
	 */
	public void gagnerPopularite(int points) {
		this.popularite += points;
		if (this.popularite > 5) {
			this.popularite = 5;
		}
	}
	
	/**
	 * Le joueur perd de l'or
	 * @return true si le joueur avait assez d'or, false sinon
	 */
	public boolean perdreOr(int montant) {
		if (this.or >= montant) {
			this.or -= montant;
			return true;
		} else {
			// S'il n'y a pas assez d'or, on met l'or à 0 et on retourne false
			this.or = 0;
			return false;
		}
	}
	
	/**
	 * Le joueur gagne de l'or
	 */
	public void gagnerOr(int montant) {
		this.or += montant;
	}
	
	
	// Méthodes de gestion de la main
	
	/**
	 * Ajoute une carte à la main du joueur
	 */
	public void ajouterCarte(Carte carte) {
		main.add(carte);
		nbCartes = main.size();
	}
	
	/**
	 * Retire une carte de la main du joueur
	 * @return true si la carte a été retirée, false sinon
	 */
	public boolean retirerCarte(Carte carte) {
		if (main.contains(carte)) {
			main.remove(carte);
			nbCartes = main.size();
			return true;
		}
		return false;
	}
	
	/**
	 * Récupère la liste des cartes en main
	 */
	public List<Carte> getMain() {
		return main;
	}
}
