package ProjetPirate;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

import boundary.BoundaryJeu;
import boundary.BoundaryJeuSwing;
import controllers.ControlJeu;
import controllers.ControlMarche;

/**
 * @brief Point d'entrée principal de l'application
 * 
 * Cette classe permet de lancer le jeu soit en mode console,
 * soit en mode graphique avec Swing
 */
public class MainUI {
    
    /**
     * @brief Point d'entrée principal
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Demander à l'utilisateur quelle interface il souhaite utiliser
        String[] options = {"Interface graphique", "Interface console", "Quitter"};
        
        int choix = JOptionPane.showOptionDialog(
            null,
            "Choisissez votre interface préférée pour le Jeu des Pirates :",
            "Jeu des Pirates",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        // Traiter le choix de l'utilisateur
        switch (choix) {
            case 0: // Interface graphique
                lancerInterfaceGraphique();
                break;
                
            case 1: // Interface console
                lancerInterfaceConsole();
                break;
                
            default: // Quitter ou fermeture de la boîte de dialogue
                System.out.println("Application fermée.");
                System.exit(0);
        }
    }
    
    /**
     * @brief Lance l'interface graphique basée sur Swing
     */
    private static void lancerInterfaceGraphique() {
        // Essayer d'utiliser le look and feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Impossible de définir le look and feel du système.");
        }
        
        // Lancer l'interface graphique dans l'EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Créer les contrôleurs
                ControlJeu controlJeu = new ControlJeu();
                ControlMarche controlMarche = controlJeu.getControlMarche();
                
                // Créer et afficher la fenêtre principale
                BoundaryJeuSwing frame = new BoundaryJeuSwing(controlJeu, controlMarche);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Erreur lors du lancement de l'interface graphique : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    
    /**
     * @brief Lance l'interface console traditionnelle
     */
    private static void lancerInterfaceConsole() {
        try {
            // Créer les contrôleurs
            ControlJeu controlJeu = new ControlJeu();
            ControlMarche controlMarche = controlJeu.getControlMarche();
            
            // Créer et lancer l'interface console
            BoundaryJeu boundaryJeu = new BoundaryJeu(controlJeu, controlMarche);
            boundaryJeu.lancerJeu();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du lancement de l'interface console : " + e.getMessage());
        }
    }
}