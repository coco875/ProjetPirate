package boundary;

import controllers.ControlJoueur;
import controllers.ControlJeu;

public class BoundaryJoueur {
	ControlJoueur cJoueur;
	ControlJeu cJeu;
	BoundaryJeu bJeu;
	
	public BoundaryJoueur(ControlJoueur cJoueur, ControlJeu cJeu, BoundaryJeu bJeu) {
		this.cJoueur = cJoueur;
		this.cJeu = cJeu;
		this.bJeu = bJeu;
	}
	
	public ControlJoueur getcJoueur() {
		return cJoueur;
	}
	
	public void setcJoueur(ControlJoueur cJoueur) {
		this.cJoueur = cJoueur;
	}
	
	public ControlJeu getcJeu() {
		return cJeu;
	}
	
	public void setcJeu(ControlJeu cJeu) {
		this.cJeu = cJeu;
	}
	
	public BoundaryJeu getbJeu() {
		return bJeu;
	}
	
	public void setbJeu(BoundaryJeu bJeu) {
		this.bJeu = bJeu;
	}

	
}
