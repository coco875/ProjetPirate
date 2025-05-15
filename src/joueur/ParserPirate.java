package joueur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import carte.Carte;
import carte.ParserCarte;

/**
 * Classe permettant de charger des pirates à partir de fichiers texte
 */
public class ParserPirate {

    /**
     * Charge un pirate à partir d'un fichier texte
     * 
     * @param fichier Le fichier contenant les informations du pirate
     * @return Un objet Pirate créé à partir des informations du fichier
     * @throws IOException Si une erreur survient lors de la lecture du fichier
     */
    public static Optional<Pirate> chargerPirate(File fichier) {
        String nom = fichier.getName().replace(".txt", "");
        String description = "";
        int popularite = 0;
        Optional<Carte> carteCoupSpeciale = null;
        String cheminImage = "images/" + nom + ".jpg"; // Chemin par défaut
        BufferedReader lecteur;
        try {
            lecteur = new BufferedReader(new FileReader(fichier));
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier " + fichier.getName() + ": " + e.getMessage());
            return Optional.empty();
        }
        StringBuilder descBuilder = new StringBuilder();
        boolean premiereLigne = true;
        
        Supplier<String> lireLigne = () -> {
            try {
                return lecteur.readLine();
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier " + fichier.getName() + ": " + e.getMessage());
                return null;
            }
        };

        for (String ligne = lireLigne.get(); ligne != null; ligne = lireLigne.get()) {
            ligne = ligne.trim();
            
            // Ignorer les lignes vides
            if (ligne.isEmpty()) {
                continue;
            }
            
            // Traiter les propriétés spécifiques
            if (ligne.startsWith("popularite:")) {
                popularite = Integer.parseInt(ligne.substring("popularite:".length()).trim());
            } else if (ligne.startsWith("carte:")) {
                carteCoupSpeciale = ParserCarte.lireCarte(ligne.substring("carte:".length()).trim());
            } else if (ligne.startsWith("image:")) {
                cheminImage = ligne.substring("image:".length()).trim();
            } else {
                // Si ce n'est pas une propriété spécifique, c'est la description
                if (premiereLigne) {
                    descBuilder.append(ligne);
                    premiereLigne = false;
                } else {
                    descBuilder.append("\n").append(ligne);
                }
            }
        }
        if (carteCoupSpeciale == null) {
            System.err.println("Aucune carte spéciale trouvée pour le pirate " + nom);
        }
        
        description = descBuilder.toString().trim();
        description = description.substring(1);
        // Création du pirate avec le constructeur disponible
        Pirate pirate = new Pirate(nom, description, popularite, carteCoupSpeciale.orElseThrow(),0 );
        
        // On définit le chemin d'image après la création
        pirate.setCheminImage(cheminImage);
        
        return Optional.of(pirate);
    }
        
    public static List<Pirate> chargerPirates(File repertoire) {

    	if (repertoire.isDirectory()) {
    		File[] fichiers = repertoire.listFiles((dir, nom) -> nom.endsWith(".txt"));
    		
    		if (fichiers != null) {
    			return Arrays.stream(fichiers)
    									.filter(File::isFile)
    									.map(file -> chargerPirate(file))
                                        .filter(Optional::isPresent)
                                        .map(Optional::get)
    									.toList();				    									
    		}
    	}
    	return new ArrayList<Pirate>();
    }
}