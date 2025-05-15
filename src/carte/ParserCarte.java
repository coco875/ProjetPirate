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
	
	private static Map<String, FabriqueCarte> registryCartes; //type:constructeur
	
	@FunctionalInterface
	public interface FabriqueCarte {
	    Carte creer(String nomCarte, String description, int cout, Map<String, String> properties);
	}
	
	public static void initialiserRegistry() {
	    registryCartes = new HashMap<>();

	    // Pour CarteAttaque : valeurs[0] = degâts infligés, valeurs[1] = subis
	    registryCartes.put("attaque",
	        (nom, desc, cout, properties) ->
	            new CarteAttaque(nom, desc, cout, Integer.parseInt(properties.getOrDefault("degats_infliges", "0")), Integer.parseInt(properties.getOrDefault("degats_subis", "0")))
	    );

	    // Pour CarteSoin : valeurs[0] = points de soin
	    registryCartes.put("soin",
	        (nom, desc, cout, properties) ->
	            new CarteSoin(nom, desc, cout, Integer.parseInt(properties.getOrDefault("vie_gagnee", "0")))
	    );
	    
	    // Pour CarteTresor : valeurs[0] = or gagné
	    registryCartes.put("tresor",
	        (nom, desc, cout, properties) ->
	            new CarteTresor(nom, desc, cout, Integer.parseInt(properties.getOrDefault("or_gagne", "0")))
	    );
	    
	    // Pour CartePopularite : valeurs[0] = pop gagnée, valeurs[1] = potentiels dégats subis
	    registryCartes.put("popularite",
	        (nom, desc, cout, properties) ->
	            new CartePopularite(nom, desc, cout, Integer.parseInt(properties.getOrDefault("popularite_gagnee", "0")), Integer.parseInt(properties.getOrDefault("degats_subis", "0")))
	    );
	       
	}
	
	
	public static Carte creerCarte(String type, String nom, String desc, int cout, Map<String, String> properties) {
	    FabriqueCarte fabrique = registryCartes.get(type);
	    if (fabrique == null) {
	        throw new IllegalArgumentException("Type inconnu : " + type);
	    }
	    return fabrique.creer(nom, desc, cout, properties);
	}
	
	
	
    /**
     * Lit une carte depuis un fichier texte formaté
     */
    public static Optional<Carte> lireCarte(String filePath) {
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
            System.err.println("Erreur de lecture du fichier: " + filePath+" "+ e);
            return Optional.empty();
        }

        // Récupération des propriétés de base
        String type = properties.getOrDefault("type", "").toLowerCase();
        String titre = properties.getOrDefault("titre", "Sans titre");
        String description = properties.getOrDefault("description", "");
        int cout = Integer.parseInt(properties.getOrDefault("cout", "10"));
        // Création de la carte selon son type
        Carte carte = creerCarte(type, titre, description, cout, properties);
        
        return Optional.ofNullable(carte);
    }
}
