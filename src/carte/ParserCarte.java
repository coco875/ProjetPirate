package carte;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Classe pour charger les cartes depuis des fichiers texte
 */
public class ParserCarte {
    /**
     * Lit une carte depuis un fichier texte formaté
     */
    public static Optional<Carte> lireCarte(String filePath) {
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
            System.err.println("Erreur de lecture du fichier: " + filePath+" "+ e);
            return Optional.empty();
        }

        // Récupération des propriétés de base
        String type = properties.getOrDefault("type", "").toLowerCase();
        String titre = properties.getOrDefault("titre", "Sans titre");
        String description = properties.getOrDefault("description", "");
        int cout = Integer.parseInt(properties.getOrDefault("cout", "10"));
        // Création de la carte selon son type
        Carte carte = null;
        
        switch (type) {
            case "attaque":
                int degatsInfliges = Integer.parseInt(properties.getOrDefault("degats_infliges", "0"));
                int degatsSubisAttaque = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                
                // Carte offensive d'attaque directe
                carte = new CarteAttaque(titre, description, cout, degatsInfliges, degatsSubisAttaque);
                break;
                
            case "soin":
                int vieGagnee = Integer.parseInt(properties.getOrDefault("vie_gagnee", "0"));
                carte = new CarteSoin(titre, description, cout, vieGagnee);
                break;

            case "popularite":
                int populariteGagnee = Integer.parseInt(properties.getOrDefault("popularite_gagnee", "0"));
                int degatsSubisPop = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                carte = new CartePopularite(titre, description, cout, populariteGagnee, degatsSubisPop);
                break;

            case "tresor":
                int orGagne = Integer.parseInt(properties.getOrDefault("or_gagne", "0"));
                carte = new CarteTresor(titre, description, cout, orGagne);
                break;
            default:
                System.err.println("Type de carte inconnu: " + type + " dans " + filePath);
        }
        
        return Optional.ofNullable(carte);
    }
}
