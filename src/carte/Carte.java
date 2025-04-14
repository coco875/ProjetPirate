package carte;

public class Carte {

	public enum TypeCarte {
		POPULAIRE,ATTAQUE,SPECIALE,PASSIVE;
	}
	private TypeCarte type;
	private String nomCarte;
	private String Description;
	@Override
	public String toString() {
		return super.toString();
	}public void setDescription(String description) {
		Description = description;
	}
	public String getDescription() {
		return Description;
	}
	public void setType(TypeCarte type) {
		this.type = type;
	}
	public TypeCarte getType() {
		return type;
	}
	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}
	public String getNomCarte() {
		return nomCarte;
	}
	public Carte(TypeCarte type, String nomCarte, String description) {
		super();
		this.type = type;
		this.nomCarte = nomCarte;
		Description = description;
	}



}
