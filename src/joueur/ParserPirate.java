package joueur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public static Pirate chargerPirate(File fichier) throws IOException {
        String nom = fichier.getName().replace(".txt", "");
        String description = "";
        int popularite = 0;
        int vie = 5; // Valeur par défaut
        
        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            StringBuilder descBuilder = new StringBuilder();
            boolean premiereLigne = true;
            
            while ((ligne = lecteur.readLine()) != null) {
                ligne = ligne.trim();
                
                // Ignorer les lignes vides
                if (ligne.isEmpty()) {
                    continue;
                }
                
                // Traiter les propriétés spécifiques
                if (ligne.startsWith("popularite:")) {
                    popularite = Integer.parseInt(ligne.substring("popularite:".length()).trim());
                } else if (ligne.startsWith("vie:")) {
                    vie = Integer.parseInt(ligne.substring("vie:".length()).trim());
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
            
            description = descBuilder.toString().trim();
        }
        
        return new Pirate(nom, description, popularite, vie);
    }
    
    /**
     * Charge tous les pirates à partir des fichiers d'un répertoire
     * 
     * @param repertoire Le répertoire contenant les fichiers de pirates
     * @return Une liste de pirates chargés
     */
    public static List<Pirate> chargerPirates(File repertoire) {
        List<Pirate> pirates = new ArrayList<>();
        
        if (repertoire.isDirectory()) {
            File[] fichiers = repertoire.listFiles((dir, nom) -> nom.endsWith(".txt"));
            
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    try {
                        pirates.add(chargerPirate(fichier));
                    } catch (IOException e) {
                        System.err.println("Erreur lors du chargement du pirate " + fichier.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
        
        return pirates;
    }
}