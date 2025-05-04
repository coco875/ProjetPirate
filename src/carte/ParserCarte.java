package carte;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParserCarte {
    public static Carte lireCarte(String filePath) throws Exception {
        Map<String, String> properties = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) { // Ignore empty lines and comments
                    continue;
                }
                String[] parts = line.split(":", 2); // Split by the first colon only
                if (parts.length == 2) {
                    String key = parts[0].trim().toLowerCase(); // Use lowercase keys for consistency
                    String value = parts[1].trim();
                    properties.put(key, value);
                } else {
                    System.err.println("Ligne ignorée (format invalide) dans " + filePath + ": " + line);
                }
            }
        } catch (IOException e) {
            throw new IOException("Erreur de lecture du fichier: " + filePath, e);
        }

        String type = properties.getOrDefault("type", "").toLowerCase();
        String titre = properties.getOrDefault("titre", "Sans titre");
        String description = properties.getOrDefault("description", "");

        try {
            switch (type) {
                case "attaque":
                    int degatsInfliges = Integer.parseInt(properties.getOrDefault("degats_infliges", "0"));
                    int degatsSubisAttaque = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                    int orVole = Integer.parseInt(properties.getOrDefault("or_vole", "0"));
                    if (orVole > 0) {
                         // Carte offensive de vol d'or
                        CarteOffensive carteVol = new CarteOffensive(titre, description, orVole, true);
                        carteVol.setOrVole(orVole); // Assurez-vous que cette méthode existe et fonctionne
                        return carteVol;
                    } else {
                        // Carte offensive d'attaque directe
                        return new CarteOffensive(titre, description, degatsInfliges, degatsSubisAttaque, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
                    }
                    
                case "soin":
                    int vieGagnee = Integer.parseInt(properties.getOrDefault("vie_gagnee", "0"));
                    return new CarteOffensive(titre, description, vieGagnee); // Utilise le constructeur de soin

                case "popularite":
                    int populariteGagnee = Integer.parseInt(properties.getOrDefault("popularite_gagnee", "0"));
                    int degatsSubisPop = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                    return new CarteStrategique(titre, description, populariteGagnee, degatsSubisPop); // Constructeur popularité

                case "tresor":
                    int orGagne = Integer.parseInt(properties.getOrDefault("or_gagne", "0"));
                     // Les trésors sont maintenant purement stratégiques (gain d'or)
                    return new CarteStrategique(titre, description, orGagne, 0, true); // Utilise le constructeur trésor (gainOr, perteOr=0, isTresor=true)

                case "speciale":
                     // Gérer les cartes spéciales (potentiellement offensives ou stratégiques selon les clés)
                     // Cette partie nécessite de clarifier la structure des cartes spéciales
                     // Pour l'instant, on crée une carte stratégique simple
                    System.err.println("Avertissement: Type 'speciale' non entièrement géré dans le parseur pour: " + titre);
                    return new CarteStrategique(titre, description, "Effet spécial non défini", 0); // Placeholder

                case "passive":
                     // Gérer les cartes passives
                     // Cette partie nécessite de clarifier la structure des cartes passives
                    System.err.println("Avertissement: Type 'passive' non entièrement géré dans le parseur pour: " + titre);
                    int valeurEffetPassif = Integer.parseInt(properties.getOrDefault("valeur_effet", "0"));
                    int dureePassif = Integer.parseInt(properties.getOrDefault("duree", "2"));
                    String effetPassif = properties.getOrDefault("effet", "Effet passif");
                    return new CarteStrategique(titre, description, valeurEffetPassif, dureePassif, effetPassif); // Placeholder

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
