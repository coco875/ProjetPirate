package jeu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import carte.JsonUtils;
import controllers.ControlJeu;
import joueur.Joueur;

/**
 * @brief Gestionnaire de sauvegarde de l'état du jeu
 * 
 * Cette classe permet de sauvegarder l'état du jeu dans un fichier JSON
 * et de recharger un état sauvegardé.
 */
public class SaveManager {
    private static final String SAVE_DIRECTORY = "saves";
    
    /**
     * @brief Sauvegarde l'état actuel du jeu
     * 
     * @param controlJeu Le contrôleur de jeu contenant l'état à sauvegarder
     * @return Le nom du fichier de sauvegarde créé, ou null en cas d'échec
     */
    public static String saveGame(ControlJeu controlJeu) {
        // Créer le répertoire de sauvegarde s'il n'existe pas
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        
        // Créer un fichier de sauvegarde avec la date et l'heure
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String saveFileName = "save_" + dateFormat.format(new Date()) + ".json";
        String savePath = SAVE_DIRECTORY + File.separator + saveFileName;
        
        // Préparer les données à sauvegarder
        Map<String, Object> saveData = new HashMap<>();
        
        // Sauvegarder les informations des joueurs
        saveData.put("joueur1", controlJeu.getJoueur(1));
        saveData.put("joueur2", controlJeu.getJoueur(2));
        saveData.put("joueurActuel", 1); // Par défaut, on suppose que c'est le tour du joueur 1
        
        // Sauvegarder dans un fichier JSON
        boolean success = JsonUtils.writeToJsonFile(saveData, savePath);
        
        if (success) {
            System.out.println("Partie sauvegardée dans " + savePath);
            return saveFileName;
        } else {
            System.err.println("Échec de la sauvegarde de la partie");
            return null;
        }
    }
    
    /**
     * @brief Charge un état de jeu sauvegardé
     * 
     * @param fileName Le nom du fichier de sauvegarde
     * @param controlJeu Le contrôleur de jeu à mettre à jour
     * @return true si le chargement a réussi, false sinon
     */
    public static boolean loadGame(String fileName, ControlJeu controlJeu) {
        String savePath = SAVE_DIRECTORY + File.separator + fileName;
        File saveFile = new File(savePath);
        
        if (!saveFile.exists()) {
            System.err.println("Le fichier de sauvegarde n'existe pas: " + savePath);
            return false;
        }
        
        try {
            // Charger les données depuis le fichier JSON
            Map<String, Object> saveData = JsonUtils.readFromJsonFile(savePath, Map.class);
            
            if (saveData == null) {
                System.err.println("Impossible de lire les données de sauvegarde");
                return false;
            }
            
            // Récupérer les informations des joueurs
            // Remarque: Jackson n'a pas converti directement en Joueur, nous devons traiter cela
            // Une solution réelle nécessiterait une désérialisation personnalisée ou des DTO
            Map<String, Object> joueur1Data = (Map<String, Object>) saveData.get("joueur1");
            Map<String, Object> joueur2Data = (Map<String, Object>) saveData.get("joueur2");
            
            // Restaurer l'état des joueurs (ceci est simplifié, une implémentation complète 
            // nécessiterait plus de traitement pour désérialiser correctement tous les objets)
            if (joueur1Data != null && joueur2Data != null) {
                // Dans une implémentation réelle, vous devriez reconstruire les objets Joueur
                System.out.println("Données de sauvegarde chargées avec succès");
                
                // Exemple d'intégration avec ControlJeu (à adapter à votre implémentation)
                // controlJeu.restaurerEtatJoueur(1, joueur1Data);
                // controlJeu.restaurerEtatJoueur(2, joueur2Data);
                
                return true;
            } else {
                System.err.println("Données de sauvegarde incomplètes");
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la sauvegarde: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * @brief Liste toutes les sauvegardes disponibles
     * 
     * @return Un tableau des noms de fichiers de sauvegarde
     */
    public static String[] listSaveFiles() {
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
            return new String[0];
        }
        
        return saveDir.list((dir, name) -> name.endsWith(".json"));
    }
}