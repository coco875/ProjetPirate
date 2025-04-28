package joueur;

import java.util.ArrayList;
import java.util.List;
import carte.Carte;
import controllers.ControlJoueur;

public class Joueur {

	private String nom;
	private Pirate pirate;
	private Integer vie;
	private Integer popularite;
	private Integer or;
	private Integer nbCartes;
	private List<Carte> main;
	
	private ControlJoueur cJ;
	
	
	public Joueur(String  nom, Pirate pirate) {
		this.nom = nom;
		this.pirate = pirate;
		
		this.vie = 5;
		this.popularite = 0;
		this.or = 0;
		this.nbCartes = 0;
		this.main = new ArrayList<Carte>(4);
	}


	public Integer getVie() {
		return vie;
	}


	public void setVie(Integer vie) {
		this.vie = vie;
	}


	public Integer getPopularite() {
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
		return nom;
	}


	public Pirate getPirate() {
		return pirate;
	}
	
	public void ajouterCarte(Carte carte) {
		//verifier nombre de cartes en main
		main.add(carte);
	}
	
	public void retirerCarte(Carte carte) throws IllegalStateException {
		//verifier que carte est bien dans la main
		if (main.contains(carte)) {
			main.remove(carte);
		} else {
			throw new IllegalStateException("Carte absente dans la main");
		}
	}
	
	public void jouerCarte(Carte carte) throws IllegalStateException {
		//verifier que carte est bien dans la main
		if (main.contains(carte)) {
			//jouer sur plateau (controlPlateau)
			retirerCarte(carte);
		} else {
			throw new IllegalStateException("Carte absente dans la main");
		}
		
	}
	
	
	
	public ControlJoueur getcJ() {
		return cJ;
	}


	public void setcJ(ControlJoueur cJ) {
		this.cJ = cJ;
	}


	public List<Carte> getMain() {
		return main;
	}

	public void recevoirEffets(int vie, int pop) {
		
	}
	
	
	
}
