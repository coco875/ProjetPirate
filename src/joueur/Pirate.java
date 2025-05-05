package joueur;

import carte.CarteCoupSpecial;

/**
 * Classe représentant un personnage pirate dans le jeu
 */
public class Pirate {
	private String nom;
	private String description;
	private int popularite, vie;
	private CarteCoupSpecial carteCoupSpeciale;

	/**
	 * Constructeur complet d'un pirate
	 */
	public Pirate(String nom, String description, int popularite, int vie) {
		this.nom = nom;
		this.description = description;
		this.popularite = popularite;
		this.vie = vie;
	}
	
	/**
	 * Constructeur simplifié d'un pirate
	 */
	public Pirate(String nom) {
		this.nom = nom;
		this.description = ""; // Description par défaut
		this.popularite = 0; // Popularité initiale
		this.vie = 5; // Points de vie initiaux
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

	public CarteCoupSpecial getCarteCoupSpeciale() {
		return carteCoupSpeciale;
	}
	
	public void setCarteCoupSpeciale(CarteCoupSpecial carteCoupSpeciale) {
		this.carteCoupSpeciale = carteCoupSpeciale;
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
}
