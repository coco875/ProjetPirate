package boundary;
import java.util.Scanner;

import controllers.ControlJeu;
import joueur.Pirate;

public class BoundaryJeu {
	public static void main(String[] args) {
		ControlJeu controlJeu = new ControlJeu();
		Pirate pirates[] = new Pirate[10];
		Pirate pirate1;
		Pirate pirate2;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Création du pirate 1\nEntrez votre nom : ");
        String nomPirate1 = scanner.nextLine();
        System.out.println("Choisissez votre pirate : \n1 : Pirate 1\n2 : Pirate 2\n");
        int idPirate1 = scanner.nextInt();
        while(idPirate1 < 1 || idPirate1 > 10) {
        	System.out.println("Entrez à nouveau le numéro de votre pirate");
        	idPirate1 = scanner.nextInt();
        }
        
        
        System.out.println("Création du pirate 2\nEntrez votre nom : ");
        String nomPirate2 = scanner.nextLine();
        System.out.println("Choisissez votre pirate : \n1 : Pirate 1\n2 : Pirate 2\n");
        int idPirate2 = scanner.nextInt();
        while((idPirate2 < 1 || idPirate2 > 10) && idPirate2 == idPirate2) {
        	if(idPirate2 < 1 || idPirate2 > 10) {
        		System.out.println("Entrez à nouveau le numéro de votre pirate");
        	}else if(idPirate1 == idPirate2) {
        		System.out.println("Veuilez choisir un pirate différent");
        	}
        	
        	idPirate2 = scanner.nextInt();
        }
        
        pirate1 = pirates[idPirate1 - 1];
        pirate2 = pirates[idPirate2 -1];
        
        controlJeu.setJoueur1(nomPirate1, pirate1);
        controlJeu.setJoueur2(nomPirate2, pirate2);
	}
}
