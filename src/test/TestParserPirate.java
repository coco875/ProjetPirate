package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import joueur.Pirate;
import joueur.ParserPirate;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestParserPirate {
    
    private File repertoire;
    private File fichierJack;
    
    @BeforeEach
    public void initialisation() {
        // Chemin vers le répertoire contenant les fichiers pirates
        repertoire = new File("src/ressources/pirates");
        fichierJack = new File("src/ressources/pirates/JackSparrow.txt");
    }
    
    @Test
    @DisplayName("Test du chargement de tous les pirates")
    public void testChargerPirates() {
        // Vérifier que le répertoire existe
        assertTrue(repertoire.exists(), "Le répertoire des pirates devrait exister");
        assertTrue(repertoire.isDirectory(), "Le chemin devrait pointer vers un répertoire");
        
        // Charger tous les pirates
        List<Pirate> pirates = ParserPirate.chargerPirates(repertoire);
        
        // Vérifier que des pirates ont été chargés
        assertNotNull(pirates, "La liste de pirates ne devrait pas être null");
        assertFalse(pirates.isEmpty(), "La liste de pirates ne devrait pas être vide");
        
        // Vérifier les données de chaque pirate
        for (Pirate pirate : pirates) {
            assertNotNull(pirate.getNom(), "Le nom du pirate ne devrait pas être null");
            assertFalse(pirate.getNom().isEmpty(), "Le nom du pirate ne devrait pas être vide");
            assertNotNull(pirate.getDescription(), "La description du pirate ne devrait pas être null");
            assertTrue(pirate.getVie() > 0, "Les points de vie devraient être positifs");
            assertTrue(pirate.getPopularite() >= 0, "La popularité devrait être positive ou nulle");
        }
    }
    
    @Test
    @DisplayName("Test du chargement d'un pirate spécifique")
    public void testChargerPirateIndividuel() throws IOException {
        // Vérifier que le fichier existe
        assertTrue(fichierJack.exists(), "Le fichier JackSparrow.txt devrait exister");
        
        // Charger le pirate
        Pirate jackSparrow = ParserPirate.chargerPirate(fichierJack);
        
        // Vérifier les données du pirate
        assertNotNull(jackSparrow, "Le pirate ne devrait pas être null");
        assertEquals("JackSparrow", jackSparrow.getNom(), "Le nom devrait être 'Jack Sparrow'");
        assertNotNull(jackSparrow.getDescription(), "La description ne devrait pas être null");
        assertTrue(jackSparrow.getVie() > 0, "Les points de vie devraient être positifs");
        assertTrue(jackSparrow.getPopularite() >= 0, "La popularité devrait être positive ou nulle");
    }
    
    @Test
    @DisplayName("Test de chargement avec un fichier inexistant")
    public void testChargerPirateInexistant() {
        File fichierInexistant = new File("src/ressources/pirates/PirateInexistant.txt");
        
        // Vérifier que le fichier n'existe pas
        assertFalse(fichierInexistant.exists(), "Le fichier de test ne devrait pas exister");
        
        // Vérifier que l'exception est bien levée
        assertThrows(IOException.class, () -> {
            ParserPirate.chargerPirate(fichierInexistant);
        }, "Une exception IOException devrait être levée pour un fichier inexistant");
    }
}
