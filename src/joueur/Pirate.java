package joueur;

import carte.Carte;

/**
 * Classe représentant un personnage pirate dans le jeu
 */
public class Pirate {
	private String nom;
	private String description;
	private int popularite, vie;
	private String cheminImage; // Attribut pour le chemin d'accès à l'image du pirate
	private Carte carteCoupSpeciale;

	/**
	 * Constructeur complet d'un pirate
	 */
	public Pirate(String nom, String description, int popularite, Carte carteCoupSpeciale, int vie) {
		this.nom = nom;
		this.description = description;
		this.popularite = popularite;
		this.vie = vie;
		this.carteCoupSpeciale = carteCoupSpeciale;
		this.cheminImage = "images/" + nom.toLowerCase().replaceAll("\\s+", "_") + ".jpg"; // Chemin d'accès par défaut basé sur le nom
	}
	
	/**
	 * Constructeur simplifié d'un pirate
	 */
	public Pirate(String nom) {
		this.nom = nom;
		this.description = ""; // Description par défaut
		this.popularite = 0; // Popularité initiale
		this.vie = 5; // Points de vie initiaux
		this.cheminImage = "images/" + nom.toLowerCase().replaceAll("\\s+", "_") + ".jpg"; // Chemin d'accès par défaut basé sur le nom
	}

	// Getters et setters
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public Carte getCarteSpecial() {
		return carteCoupSpeciale;
	}
	
	public int getPopularite() {
		return popularite;
	}
	
	public void setPopularite(int popularite) {
		this.popularite = popularite;
	}
	
	public int getVie() {
		return vie;
	}
	
	public void setVie(int vie) {
		this.vie = vie;
	}
	
	public String getCheminImage() {
		return cheminImage;
	}
	
	public void setCheminImage(String cheminImage) {
		this.cheminImage = cheminImage;
	}
}
