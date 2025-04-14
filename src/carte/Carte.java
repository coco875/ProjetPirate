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
		return this.nomCarte+"\n"+this.Description;
	}public void setDescription(String description) {
		Description = description;
	}
	public String getDescription() {
		return Description;
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
	protected Carte(TypeCarte type, String nomCarte, String description) {
		super();
		this.type = type;
		this.nomCarte = nomCarte;
		Description = description;
	}



}
