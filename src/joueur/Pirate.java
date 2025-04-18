package joueur;

import carte.CarteCoupSpecial;

public class Pirate {
	private String nom;
	private String description;
	private int popularite, vie;
	private CarteCoupSpecial carteCoupSpeciale;

	public Pirate(String nom, String description, int popularite, int vie) {
		this.nom = nom;
		this.description = description;
		this.popularite = popularite;
		this.vie = vie;
	}

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
	
	
}
