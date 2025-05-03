package carte;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import carte.Carte.TypeCarte;
public class ParserCarte {
	public static Carte lireCarte(String s) throws Exception {
		TypeCarte t = TypeCarte.ATTAQUE;
		String titre, description;
		Integer valeurPrincipale = 0, valeurSecondaire = 0;
		Integer orGagne = 0, orPerdu = 0, orVole = 0, vieGagne = 0;
		Carte c = null;
		BufferedReader br = new BufferedReader(new FileReader(s));
		try {
		    String line = br.readLine();
		    if (line.equals("popularite")) t = TypeCarte.POPULAIRE;
		    else if (line.equals("attaque")) t = TypeCarte.ATTAQUE;
		    else if (line.equals("speciale")) t = TypeCarte.SPECIALE;
		    else if (line.equals("passive")) t = TypeCarte.PASSIVE;
		    else if (line.equals("tresor")) t = TypeCarte.TRESOR;
		    else if (line.equals("soin")) t = TypeCarte.SOIN;
		    else { /* Gérer les types non reconnus */ }
		    
		    titre = br.readLine();
		    description = br.readLine();
		    
		    // Lecture des valeurs selon le type de carte
		    if (t == TypeCarte.TRESOR) {
		        orGagne = Integer.parseInt(br.readLine());
		        orPerdu = Integer.parseInt(br.readLine());
		        orVole = Integer.parseInt(br.readLine());
		    } else if (t == TypeCarte.SOIN) {
		        vieGagne = Integer.parseInt(br.readLine());
		    } else {
		        // Pour les cartes standards (attaque, popularité, etc.)
		        valeurPrincipale = Integer.parseInt(br.readLine());
		        valeurSecondaire = Integer.parseInt(br.readLine());
		    }
		    
		    // Créer le bon type de carte selon le type lu
		    if (t == TypeCarte.ATTAQUE) {
		        c = new CarteAttaque(titre, description, valeurPrincipale, valeurSecondaire);
		    } else if (t == TypeCarte.POPULAIRE) {
		        c = new CartePopularite(titre, description, valeurPrincipale, valeurSecondaire);
		    } else if (t == TypeCarte.TRESOR) {
		        c = new CarteTresor(titre, description, orGagne, orPerdu, orVole);
		    } else if (t == TypeCarte.SOIN) {
		        c = new CarteSoin(titre, description, vieGagne);
		    } else {
		        // Pour les autres types, utiliser la classe de base
		        c = new Carte(t, titre, description, valeurPrincipale, valeurSecondaire);
		    }
		} 
		catch (Exception e) {
			throw new IOException("Erreur lors de la création de carte: " + e.getMessage());
		}
		finally {
		    br.close();
		}
		return c;
	}
}
