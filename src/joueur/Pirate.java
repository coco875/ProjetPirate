package joueur;

import carte.CarteCoupSpecial;

public class Pirate {
	private String nom;
	private String description;
	
	private CarteCoupSpecial carteCoupSpeciale;

	public Pirate(String nom, String description, CarteCoupSpecial carteCoupSpeciale) {
		this.nom = nom;
		this.description = description;
		this.carteCoupSpeciale = carteCoupSpeciale;
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
