package carte;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import carte.Carte.TypeCarte;
public class ParserCarte {
	public static Carte lireCarte(String s) throws Exception {
		TypeCarte t=TypeCarte.ATTAQUE;
		String titre,description;
		Integer param1,param2;
		Carte c = null;
		BufferedReader br = new BufferedReader(new FileReader(s));
		try {
		    String line = br.readLine();
		    if (line=="popularite") t= TypeCarte.POPULAIRE;
		    else if (line=="attaque")t=TypeCarte.ATTAQUE;
		    else if (line=="speciale")t=TypeCarte.SPECIALE;
		    else if (line=="passive") t=TypeCarte.PASSIVE;
		    else ;//throw error
		    titre= br.readLine();
		    description=br.readLine();
		    param1=Integer.parseInt(br.readLine());
		    param2=Integer.parseInt(br.readLine());
		    if(t==TypeCarte.ATTAQUE) c=new CarteAttaque(titre,description,param1,param2);
		} 
		catch (Exception e){
			throw new IOException("erreur lors de la creation de carte");
		}
		finally {
		    br.close();
		}
		return c;
		}
}
