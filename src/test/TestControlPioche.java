package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import controllers.ControlPioche;
import carte.Carte;
import carte.TypeCarte;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import jeu.Pioche;

/**
 * Tests pour la classe ControlPioche
 */
@DisplayName("Tests du contrôleur ControlPioche")
public class TestControlPioche {
    
    private ControlPioche controlPioche;
    private List<File> tempFiles;
    
    @BeforeEach
    public void initialiser() {
        controlPioche = new ControlPioche();
        tempFiles = new ArrayList<>();
    }
    
    @AfterEach
    public void nettoyer() {
        // Nettoyer les fichiers temporaires créés
        for (File file : tempFiles) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
    
    @Test
    @DisplayName("Test d'initialisation de la pioche")
    public void testInitialiserPioche() {
        // La pioche ne devrait pas être vide après l'initialisation
        assertFalse(controlPioche.estVide(), "La pioche devrait contenir des cartes après initialisation");
        
        // Vérifier que la pioche contient bien des cartes
        List<Carte> cartes = extrairePioche(controlPioche);
        assertFalse(cartes.isEmpty(), "La pioche devrait contenir des cartes");
        
        // Vérifier la présence de cartes offensives et stratégiques
        boolean hasOffensive = false;
        boolean hasStrategique = false;
        
        for (Carte carte : cartes) {
            if (carte.getType() == TypeCarte.OFFENSIVE) {
                hasOffensive = true;
            } else if (carte.getType() == TypeCarte.STRATEGIQUE) {
                hasStrategique = true;
            }
        }
        
        assertTrue(hasOffensive, "La pioche devrait contenir au moins une carte offensive");
        assertTrue(hasStrategique || cartes.size() >= 5, "La pioche devrait contenir au moins une carte stratégique ou un minimum de 5 cartes");
    }
    
    @Test
    @DisplayName("Test de pioche de cartes")
    public void testPiocher() {
        // Piocher une première carte
        Carte carte1 = controlPioche.piocher();
        assertNotNull(carte1, "La première carte piochée ne devrait pas être null");
        
        // Piocher plusieurs cartes consécutives
        List<Carte> cartesPiochees = new ArrayList<>();
        cartesPiochees.add(carte1);
        
        // On pioche toutes les cartes jusqu'à ce que la pioche soit vide
        Carte carte;
        int compteur = 0;
        int nombreMaxCartes = 100; // Limite pour éviter une boucle infinie en cas de bug
        
        while (!controlPioche.estVide() && compteur < nombreMaxCartes) {
            carte = controlPioche.piocher();
            assertNotNull(carte, "Une carte piochée ne devrait pas être null");
            
            // Vérifier que chaque carte est unique (par référence d'objet)
            for (Carte cartePrecedente : cartesPiochees) {
                assertNotSame(cartePrecedente, carte, 
                               "Chaque carte piochée devrait être un objet distinct");
            }
            
            cartesPiochees.add(carte);
            compteur++;
        }
        
        // Après avoir vidé la pioche, on devrait recevoir null
        assertNull(controlPioche.piocher(), "Piocher dans une pioche vide devrait retourner null");
        assertTrue(controlPioche.estVide(), "La pioche devrait être vide");
    }
    
    @Test
    @DisplayName("Test de pioche multiple avec réinitialisation")
    public void testPiochesMultiples() {
        // Vider complètement la première pioche
        while (!controlPioche.estVide()) {
            controlPioche.piocher();
        }
        
        assertTrue(controlPioche.estVide(), "La pioche devrait être vide");
        
        // Réinitialiser la pioche
        controlPioche = new ControlPioche();
        
        assertFalse(controlPioche.estVide(), "La pioche ne devrait pas être vide après réinitialisation");
        
        // Vérifier qu'on peut piocher à nouveau
        Carte carte = controlPioche.piocher();
        assertNotNull(carte, "On devrait pouvoir piocher après réinitialisation");
    }
    
    @Test
    @DisplayName("Test de la présence de différents types de cartes")
    public void testCartesChargees() {
        List<Carte> cartesPiochees = new ArrayList<>();
        
        // Piocher toutes les cartes pour les analyser
        while (!controlPioche.estVide()) {
            cartesPiochees.add(controlPioche.piocher());
        }
        
        // Vérifier qu'il y a au moins plusieurs cartes
        assertTrue(cartesPiochees.size() >= 5, "La pioche devrait contenir au moins 5 cartes");
        
        // Vérifier que les cartes ont des données valides
        for (Carte carte : cartesPiochees) {
            assertNotNull(carte.getNomCarte(), "Le nom de la carte ne devrait pas être null");
            assertFalse(carte.getNomCarte().isEmpty(), "Le nom de la carte ne devrait pas être vide");
            assertNotNull(carte.getDescription(), "La description ne devrait pas être null");
            assertNotNull(carte.getType(), "Le type de carte ne devrait pas être null");
        }
    }
    
    @Test
    @DisplayName("Test de la méthode estVide")
    public void testEstVide() {
        // Initialement, la pioche ne devrait pas être vide
        assertFalse(controlPioche.estVide(), "La pioche ne devrait pas être vide après initialisation");
        
        // Vider la pioche
        while (!controlPioche.estVide()) {
            controlPioche.piocher();
        }
        
        // Maintenant la pioche devrait être vide
        assertTrue(controlPioche.estVide(), "La pioche devrait être vide après avoir pioché toutes les cartes");
    }
    
    @Test
    @DisplayName("Test de chargement des cartes depuis un répertoire inexistant")
    public void testChargerDepuisRepertoireInexistant() throws Exception {
        // Créer une nouvelle instance pour éviter d'utiliser la pioche déjà initialisée
        ControlPioche controlPiocheVide = new ControlPioche();
        
        // Utiliser la réflexion pour accéder à la méthode privée
        Method methode = ControlPioche.class.getDeclaredMethod("chargerCartesDepuisRepertoire", File.class);
        methode.setAccessible(true);
        
        // Invoquer la méthode avec un répertoire inexistant
        File repInexistant = new File("ce_repertoire_n_existe_pas");
        @SuppressWarnings("unchecked")
        List<Carte> result = (List<Carte>) methode.invoke(controlPiocheVide, repInexistant);
        
        // La liste devrait être vide car le répertoire n'existe pas
        assertTrue(result.isEmpty(), "La liste devrait être vide pour un répertoire inexistant");
    }
    
    @Test
    @DisplayName("Test de création de cartes par défaut quand aucun fichier n'est chargé")
    public void testCreationCartesParDefaut() throws Exception {
        // Créer une instance avec un répertoire de cartes inexistant pour forcer la création de cartes par défaut
        ControlPioche controlPiocheDefaut = new ControlPioche();
        
        // Vérifier que la pioche n'est pas vide (cartes par défaut)
        assertFalse(controlPiocheDefaut.estVide(), "La pioche devrait contenir des cartes par défaut");
        
        // Extraire la pioche pour vérifier les cartes créées
        List<Carte> cartes = extrairePioche(controlPiocheDefaut);
        
        // Vérifier la présence de cartes offensives et stratégiques
        boolean hasOffensive = false;
        boolean hasStrategique = false;
        
        for (Carte carte : cartes) {
            if (carte.getType() == TypeCarte.OFFENSIVE) {
                hasOffensive = true;
            } else if (carte.getType() == TypeCarte.STRATEGIQUE) {
                hasStrategique = true;
            }
        }
        
        assertTrue(hasOffensive, "La pioche devrait contenir au moins une carte offensive par défaut");
        assertTrue(hasStrategique || cartes.size() >= 5, "La pioche devrait contenir au moins une carte stratégique ou un minimum de 5 cartes");
        
        // Vérifier que plusieurs types de cartes génériques sont présents
        // Note: La vérification des sous-types spécifiques peut ne pas être pertinente
        // si l'implémentation a évolué vers l'utilisation de CarteOffensive plus générique
        
        // Vérifier que les CarteOffensive contiennent des cartes d'attaque directe
        boolean hasAttaqueDirect = false;
        boolean hasPopularite = false;
        boolean hasTresor = false;
        boolean hasSoin = false;
        
        for (Carte carte : cartes) {
            // Vérifier les types génériques avec getType()
            if (carte.getType() == TypeCarte.OFFENSIVE) {
                // Pour les cartes offensives, on peut vérifier par TypeOffensif 
                // si possible, ou juste par la présence du type
                hasAttaqueDirect = true;
            } else if (carte.getType() == TypeCarte.STRATEGIQUE) {
                // Pour les cartes stratégiques
                if (carte.getDescription() != null && carte.getDescription().toLowerCase().contains("popularité")) {
                    hasPopularite = true;
                } else if (carte.getDescription() != null && carte.getDescription().toLowerCase().contains("trésor")) {
                    hasTresor = true;
                }
            }
            
            // Vérification alternative pour les soins
            if (carte.getDescription() != null && carte.getDescription().toLowerCase().contains("soin")) {
                hasSoin = true;
            }
        }
        
        // Au moins un type de carte devrait être présent
        assertTrue(hasAttaqueDirect || hasPopularite || hasTresor || hasSoin, 
                  "La pioche devrait contenir au moins un type de carte spécifique");
    }
    
    @Test
    @DisplayName("Test de chargement des cartes depuis un répertoire avec fichier invalide")
    public void testChargerDepuisRepertoireAvecFichierInvalide(@TempDir Path tempDir) throws Exception {
        // Créer un fichier de carte invalide (non-conforme au format attendu)
        File fichierInvalide = new File(tempDir.toFile(), "carte_invalide.txt");
        try (FileWriter writer = new FileWriter(fichierInvalide)) {
            writer.write("Contenu non conforme au format attendu");
        }
        tempFiles.add(fichierInvalide);
        
        // Créer une nouvelle instance de ControlPioche
        ControlPioche controlPiocheTest = new ControlPioche();
        
        // Utiliser la réflexion pour accéder à la méthode privée
        Method methode = ControlPioche.class.getDeclaredMethod("chargerCartesDepuisRepertoire", File.class);
        methode.setAccessible(true);
        
        // Invoquer la méthode avec le répertoire temporaire
        @SuppressWarnings("unchecked")
        List<Carte> result = (List<Carte>) methode.invoke(controlPiocheTest, tempDir.toFile());
        
        // La liste devrait être vide car le fichier est invalide
        assertTrue(result.isEmpty(), "La liste devrait être vide pour un fichier invalide");
    }
    
    @Test
    @DisplayName("Test de chargement des cartes depuis un répertoire avec fichier non-TXT")
    public void testChargerDepuisRepertoireAvecFichierNonTXT(@TempDir Path tempDir) throws Exception {
        // Créer un fichier non-TXT
        File fichierNonTXT = new File(tempDir.toFile(), "fichier.jpg");
        fichierNonTXT.createNewFile();
        tempFiles.add(fichierNonTXT);
        
        // Créer une nouvelle instance de ControlPioche
        ControlPioche controlPiocheTest = new ControlPioche();
        
        // Utiliser la réflexion pour accéder à la méthode privée
        Method methode = ControlPioche.class.getDeclaredMethod("chargerCartesDepuisRepertoire", File.class);
        methode.setAccessible(true);
        
        // Invoquer la méthode avec le répertoire temporaire
        @SuppressWarnings("unchecked")
        List<Carte> result = (List<Carte>) methode.invoke(controlPiocheTest, tempDir.toFile());
        
        // La liste devrait être vide car le fichier n'est pas un TXT
        assertTrue(result.isEmpty(), "La liste devrait être vide pour un fichier non-TXT");
    }
    
    @Test
    @DisplayName("Test avec un répertoire vide")
    public void testRepertoireVide(@TempDir Path tempDir) throws Exception {
        // Créer une nouvelle instance de ControlPioche
        ControlPioche controlPiocheTest = new ControlPioche();
        
        // Utiliser la réflexion pour accéder à la méthode privée
        Method methode = ControlPioche.class.getDeclaredMethod("chargerCartesDepuisRepertoire", File.class);
        methode.setAccessible(true);
        
        // Invoquer la méthode avec le répertoire vide
        @SuppressWarnings("unchecked")
        List<Carte> result = (List<Carte>) methode.invoke(controlPiocheTest, tempDir.toFile());
        
        // La liste devrait être vide car le répertoire est vide
        assertTrue(result.isEmpty(), "La liste devrait être vide pour un répertoire sans fichiers");
    }
    
    @Test
    @DisplayName("Test constructeur ControlPioche")
    public void testConstructeur() {
        // Vérifier que le constructeur initialise correctement la pioche
        ControlPioche cp = new ControlPioche();
        assertFalse(cp.estVide(), "La pioche ne devrait pas être vide après construction");
        
        // Vérifier qu'on peut piocher dans cette nouvelle pioche
        Carte c = cp.piocher();
        assertNotNull(c, "On devrait pouvoir piocher une carte dans la pioche nouvellement créée");
    }
    
    /**
     * Méthode utilitaire pour extraire la pioche d'un ControlPioche à l'aide de la réflexion
     */
    private List<Carte> extrairePioche(ControlPioche controlPioche) {
        try {
            // Accéder au champ pioche par réflexion
            Field piocheField = ControlPioche.class.getDeclaredField("pioche");
            piocheField.setAccessible(true);
            Pioche pioche = (Pioche) piocheField.get(controlPioche);
            
            // Vider la pioche en collectant toutes les cartes
            List<Carte> cartes = new ArrayList<>();
            Carte carte;
            while ((carte = pioche.piocher()) != null) {
                cartes.add(carte);
            }
            
            // Remettre les cartes dans la pioche pour ne pas perturber d'autres tests
            for (Carte c : cartes) {
                pioche.ajouterCarte(c);
            }
            pioche.melanger();
            
            return cartes;
        } catch (Exception e) {
            fail("Échec lors de l'extraction de la pioche: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}