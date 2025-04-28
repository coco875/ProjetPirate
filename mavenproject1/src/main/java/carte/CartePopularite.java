package carte;

public class CartePopularite extends Carte {
	private Integer popularite;
	private Integer vie;
	public CartePopularite( String nomCarte, String description, Integer popularite, Integer vie) {
		super(TypeCarte.POPULAIRE, nomCarte, description);
		this.popularite = popularite;
		this.vie = vie;
	}
	public Integer getPopularite() {
		return popularite;
	}
	public void setPopularite(Integer popularite) {
		this.popularite = popularite;
	}
	public Integer getVie() {
		return vie;
	}
	public void setVie(Integer vie) {
		this.vie = vie;
	}
	

	public String toString() {
		return super.toString()+"\n"+this.popularite+" "+this.vie;
		
	}

}
