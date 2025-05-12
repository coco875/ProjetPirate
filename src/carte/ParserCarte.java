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
	
	private static Map<String, FabriqueCarte> registryCartes; //type:constructeur
	
	@FunctionalInterface
	public interface FabriqueCarte {
	    Carte creer(String nomCarte, String description, String cheminImage, int cout, int... valeurs);
	}
	
	public static void initialiserRegistry() {
	    registryCartes = new HashMap<>();

	    // Pour CarteAttaque : valeurs[0] = degâts infligés, valeurs[1] = subis
	    registryCartes.put("attaque",
	        (nom, desc, img, cout, valeurs) ->
	            new CarteAttaque(nom, desc, img, cout, valeurs[0], valeurs[1])
	    );

	    // Pour CarteSoin : valeurs[0] = points de soin
	    registryCartes.put("soin",
	        (nom, desc, img, cout, valeurs) ->
	            new CarteSoin(nom, desc, img, cout, valeurs[0])
	    );
	    
	    // Pour CarteTresor : valeurs[0] = or gagné
	    registryCartes.put("tresor",
	        (nom, desc, img, cout, valeurs) ->
	            new CarteTresor(nom, desc, img, cout, valeurs[0])
	    );
	    
	    // Pour CartePopularite : valeurs[0] = pop gagnée, valeurs[1] = potentiels dégats subis
	    registryCartes.put("popularite",
	        (nom, desc, img, cout, valeurs) ->
	            new CartePopularite(nom, desc, img, cout, valeurs[0], valeurs[1])
	    );
	       
	}
	
	
	public static Carte creerCarte(String type, String nom, String desc, String cheminImage, int cout, int... valeurs) {
	    FabriqueCarte fabrique = registryCartes.get(type);
	    if (fabrique == null) {
	        throw new IllegalArgumentException("Type inconnu : " + type);
	    }
	    return fabrique.creer(nom, desc, cheminImage, cout, valeurs);
	}
	
	
	
    /**
     * Lit une carte depuis un fichier texte formaté
     */
    public static Carte lireCarte(String filePath) throws Exception {
        Map<String, String> properties = new HashMap<>();
        
        initialiserRegistry();
        
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
        String cheminImage = properties.getOrDefault("image", "images/cartes/" + titre.replaceAll("\\s+", "_").toLowerCase() + ".jpg");
        int cout = Integer.parseInt(properties.getOrDefault("cout", "10"));

        try {
            // Création de la carte selon son type
            Carte carte = null;
            
            
            switch (type) {
                case "attaque":
                    int degatsInfliges = Integer.parseInt(properties.getOrDefault("degats_infliges", "0"));
                    int degatsSubisAttaque = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                    
                    // Carte offensive d'attaque directe
                    carte = creerCarte(type, titre, description, cheminImage, cout, degatsInfliges, degatsSubisAttaque);
                    break;
                    
                case "soin":
                    int vieGagnee = Integer.parseInt(properties.getOrDefault("vie_gagnee", "0"));
                    carte = creerCarte(type, titre, description, cheminImage, cout, vieGagnee);
                    break;

                case "popularite":
                    int populariteGagnee = Integer.parseInt(properties.getOrDefault("popularite_gagnee", "0"));
                    int degatsSubisPop = Integer.parseInt(properties.getOrDefault("degats_subis", "0"));
                    carte = creerCarte(type, titre, description, cheminImage, cout, populariteGagnee, degatsSubisPop);
                    break;

                case "tresor":
                    int orGagne = Integer.parseInt(properties.getOrDefault("or_gagne", "0"));
                    carte = creerCarte(type, titre, description, cheminImage, cout, orGagne);
                    break;
                default:
                    throw new IllegalArgumentException("Type de carte inconnu: " + type + " dans " + filePath);
            }
            
            // Définir le chemin d'image pour la carte créée
            if (carte != null) {
                carte.setCheminImage(cheminImage);
            }
            
            return carte;
            
        } catch (NumberFormatException e) {
            throw new Exception("Erreur de format numérique dans le fichier: " + filePath, e);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la création de la carte depuis: " + filePath, e);
        }
    }
}
