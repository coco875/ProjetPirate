package carte;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @brief Classe utilitaire pour manipuler le JSON
 * 
 * Cette classe fournit des méthodes pour convertir des objets du jeu 
 * vers et depuis le format JSON.
 */
public class JsonUtils {
    // ObjectMapper est thread-safe après sa configuration
    private static final ObjectMapper objectMapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT); // Pour un JSON formaté
    
    /**
     * @brief Convertit un objet Java en chaîne JSON
     * 
     * @param object L'objet à convertir
     * @return La chaîne JSON ou null en cas d'erreur
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            System.err.println("Erreur lors de la conversion en JSON: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * @brief Convertit une chaîne JSON en objet Java
     * 
     * @param <T> Type d'objet à créer
     * @param json La chaîne JSON à convertir
     * @param valueType La classe de l'objet à créer
     * @return L'objet créé ou null en cas d'erreur
     */
    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            System.err.println("Erreur lors de la conversion depuis JSON: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * @brief Écrit un objet Java dans un fichier JSON
     * 
     * @param object L'objet à écrire
     * @param filePath Le chemin du fichier
     * @return true si l'écriture a réussi, false sinon
     */
    public static boolean writeToJsonFile(Object object, String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), object);
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier JSON: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * @brief Lit un objet depuis un fichier JSON
     * 
     * @param <T> Type d'objet à créer
     * @param filePath Le chemin du fichier
     * @param valueType La classe de l'objet à créer
     * @return L'objet créé ou null en cas d'erreur
     */
    public static <T> T readFromJsonFile(String filePath, Class<T> valueType) {
        try {
            return objectMapper.readValue(new File(filePath), valueType);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * @brief Lit une liste d'objets depuis un fichier JSON
     * 
     * @param <T> Type d'objets dans la liste
     * @param filePath Le chemin du fichier
     * @param typeReference Référence de type pour la liste
     * @return La liste d'objets ou null en cas d'erreur
     */
    public static <T> List<T> readListFromJsonFile(String filePath, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(new File(filePath), typeReference);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de la liste JSON: " + e.getMessage());
            return null;
        }
    }
}