package carte;

public class CarteAttaque extends Carte {
	private Integer degats; //Degats infligés à l'adversaire
	private Integer vie; //Degats auto-infligés
	public CarteAttaque(String nomCarte, String description, Integer degats, Integer vie) {
		super(TypeCarte.ATTAQUE, nomCarte, description);
		this.degats = degats;
		this.vie = vie;
	}
	public Integer getDegats() {
		return degats;
	}
	public void setDegats(Integer degats) {
		this.degats = degats;
	}
	public Integer getVie() {
		return vie;
	}
	public void setVie(Integer vie) {
		this.vie = vie;
	}
	
}
