package carte;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour charger les cartes depuis des fichiers texte
 */
public class ParserCarte {
    /**
     * Lit une carte depuis un fichier texte formaté
     */
    public static Carte lireCarte(String filePath) throws Exception {
        Map<String, String> properties = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) { // Ignore les lignes vides et commentaires
                    continue;
                }
                String[] parts = line.split(":", 2); // Sépare au premier deux-points uniquement
                if (parts.length == 2) {
                    String key = parts[0].trim().toLowerCase(); // Clés en minuscules pour cohérence
                    String value = parts[1].trim();
                    properties.put(key, value);
                } else {
                    System.err.println("Ligne ignorée (format invalide) dans " + filePath + ": " + line);
                }
            }
        } catch (IOException e) {
            throw new IOException("Erreur de lecture du fichier: " + filePath, e);
        }

        // Récupération des propriétés de base
        String type = properties.getOrDefault("type", "").toLowerCase();
        String titre = properties.getOrDefault("titre", "Sans titre");
        String description = properties.getOrDefault("description", "");

        try {
            // Création de la carte selon son type
            switch (type) {
                case "attaque":
                    int degatsInfliges = Integer.parseInt(properties.getOrDefault("degats_infliges", "0"));
                    int degatsSubisAttaque = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                    
                    // Vérification de l'attribut or_vole (maintenant ignoré)
                    if (properties.containsKey("or_vole")) {
                        System.out.println("Avertissement: L'attribut 'or_vole' est ignoré car la fonctionnalité de vol d'or a été supprimée. Carte: " + titre);
                    }
                    
                    // Carte offensive d'attaque directe
                    return new CarteOffensive(titre, description, degatsInfliges, degatsSubisAttaque, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
                    
                case "soin":
                    int vieGagnee = Integer.parseInt(properties.getOrDefault("vie_gagnee", "0"));
                    return new CarteOffensive(titre, description, vieGagnee);

                case "popularite":
                    int populariteGagnee = Integer.parseInt(properties.getOrDefault("popularite_gagnee", "0"));
                    int degatsSubisPop = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                    return new CarteStrategique(titre, description, populariteGagnee, degatsSubisPop);

                case "tresor":
                    int orGagne = Integer.parseInt(properties.getOrDefault("or_gagne", "0"));
                    return new CarteStrategique(titre, description, orGagne, 0, true);

                case "speciale":
                    // Gestion simplifiée des cartes spéciales
                    System.err.println("Avertissement: Type 'speciale' non entièrement géré dans le parseur pour: " + titre);
                    return new CarteStrategique(titre, description, "Effet spécial non défini", 0);

                case "passive":
                    // Gestion des cartes passives
                    System.err.println("Avertissement: Type 'passive' non entièrement géré dans le parseur pour: " + titre);
                    int valeurEffetPassif = Integer.parseInt(properties.getOrDefault("valeur_effet", "0"));
                    int dureePassif = Integer.parseInt(properties.getOrDefault("duree", "2"));
                    String effetPassif = properties.getOrDefault("effet", "Effet passif");
                    return new CarteStrategique(titre, description, valeurEffetPassif, dureePassif, effetPassif);

                default:
                    throw new IllegalArgumentException("Type de carte inconnu: " + type + " dans " + filePath);
            }
        } catch (NumberFormatException e) {
            throw new Exception("Erreur de format numérique dans le fichier: " + filePath, e);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la création de la carte depuis: " + filePath, e);
        }
    }
}
