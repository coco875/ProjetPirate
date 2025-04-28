package boundary;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import carte.Carte;
import controllers.ControlJeu;
import controllers.ControlMarche;
import joueur.Pirate;

/**
 * @brief Frontière gérant les interactions avec l'utilisateur via une interface graphique Swing
 * 
 * Cette classe implémente l'interface utilisateur graphique du jeu
 * conformément au modèle ECB.
 */
public class BoundaryJeuSwing extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // Contrôleurs
    private ControlJeu controlJeu;
    private ControlMarche controlMarche;
    
    // Composants de l'interface
    private JPanel contentPane;
    private JPanel panelJoueur1;
    private JPanel panelJoueur2;
    private JPanel panelCentral;
    private JPanel panelCartes;
    private JTextArea logArea;
    private JButton btnPiocher;
    private JButton btnMarche;
    private JButton btnFinTour;
    
    // Variables de jeu
    private int joueurActuel = 1;
    private boolean partieEnCours = false;
    
    /**
     * @brief Constructeur de l'interface graphique
     * 
     * @param controlJeu Contrôleur principal du jeu
     * @param controlMarche Contrôleur du marché
     */
    public BoundaryJeuSwing(ControlJeu controlJeu, ControlMarche controlMarche) {
        this.controlJeu = controlJeu;
        this.controlMarche = controlMarche;
        
        initUI();
    }
    
    /**
     * @brief Initialise l'interface utilisateur
     */
    private void initUI() {
        setTitle("Jeu des Pirates");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        // Panneau d'information du joueur 1 (gauche)
        panelJoueur1 = createPlayerPanel(1);
        contentPane.add(panelJoueur1, BorderLayout.WEST);
        
        // Panneau d'information du joueur 2 (droite)
        panelJoueur2 = createPlayerPanel(2);
        contentPane.add(panelJoueur2, BorderLayout.EAST);
        
        // Panneau central (plateau de jeu et contrôles)
        panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(0, 0));
        contentPane.add(panelCentral, BorderLayout.CENTER);
        
        // Panneau pour les cartes
        panelCartes = new JPanel();
        panelCartes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentral.add(panelCartes, BorderLayout.CENTER);
        
        // Panneau pour les logs et informations
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(980, 150));
        panelCentral.add(scrollPane, BorderLayout.SOUTH);
        
        // Panneau pour les boutons de contrôle
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentral.add(panelControles, BorderLayout.NORTH);
        
        // Boutons de contrôle
        btnPiocher = new JButton("Piocher une carte");
        btnPiocher.setEnabled(false);
        btnPiocher.addActionListener(e -> piocherCarte());
        panelControles.add(btnPiocher);
        
        btnMarche = new JButton("Visiter le marché");
        btnMarche.setEnabled(false);
        btnMarche.addActionListener(e -> ouvrirMarche());
        panelControles.add(btnMarche);
        
        btnFinTour = new JButton("Fin du tour");
        btnFinTour.setEnabled(false);
        btnFinTour.addActionListener(e -> finirTour());
        panelControles.add(btnFinTour);
        
        // Menu du jeu
        createMenuBar();
        
        // Afficher un message de bienvenue
        logArea.append("Bienvenue au Jeu des Pirates !\n");
        logArea.append("Allez dans le menu Jeu > Nouvelle partie pour commencer.\n");
    }
    
    /**
     * @brief Crée la barre de menu du jeu
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu menuJeu = new JMenu("Jeu");
        menuBar.add(menuJeu);
        
        JMenuItem menuItemNouveau = new JMenuItem("Nouvelle partie");
        menuItemNouveau.addActionListener(e -> demarrerPartie());
        menuJeu.add(menuItemNouveau);
        
        menuJeu.addSeparator();
        
        JMenuItem menuItemRegles = new JMenuItem("Règles du jeu");
        menuItemRegles.addActionListener(e -> afficherRegles());
        menuJeu.add(menuItemRegles);
        
        menuJeu.addSeparator();
        
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.addActionListener(e -> System.exit(0));
        menuJeu.add(menuItemQuitter);
    }
    
    /**
     * @brief Crée un panneau d'information pour un joueur
     * 
     * @param joueurId ID du joueur (1 ou 2)
     * @return Panneau configuré pour le joueur
     */
    private JPanel createPlayerPanel(int joueurId) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 600));
        panel.setBorder(BorderFactory.createTitledBorder("Joueur " + joueurId));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Composants qui seront mis à jour pendant le jeu
        JLabel lblNom = new JLabel("Nom: -");
        JLabel lblPirate = new JLabel("Capitaine: -");
        JLabel lblVie = new JLabel("Vie: 0/5");
        JLabel lblPopularite = new JLabel("Popularité: 0/5");
        JLabel lblOr = new JLabel("Or: 0");
        
        // Étiquettes pour les informations du joueur
        lblNom.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPirate.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblVie.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPopularite.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblOr.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Ajouter des espaces entre les composants
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(lblNom);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblPirate);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblVie);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblPopularite);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblOr);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Stocker les références aux composants pour les mettre à jour plus tard
        panel.putClientProperty("lblNom", lblNom);
        panel.putClientProperty("lblPirate", lblPirate);
        panel.putClientProperty("lblVie", lblVie);
        panel.putClientProperty("lblPopularite", lblPopularite);
        panel.putClientProperty("lblOr", lblOr);
        
        // Section pour afficher les cartes en main
        JPanel panelMain = new JPanel();
        panelMain.setBorder(BorderFactory.createTitledBorder("Main"));
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.setPreferredSize(new Dimension(180, 300));
        panel.add(panelMain);
        panel.putClientProperty("panelMain", panelMain);
        
        return panel;
    }
    
    /**
     * @brief Affiche les règles du jeu
     */
    private void afficherRegles() {
        JOptionPane.showMessageDialog(this,
            "=== Règles du Jeu des Pirates ===\n\n" +
            "- Deux joueurs s'affrontent en utilisant des cartes\n" +
            "- Chaque joueur commence avec 5 points de vie et 0 point de popularité\n" +
            "- À chaque tour, un joueur pioche une carte et peut jouer jusqu'à 2 cartes\n" +
            "- Les cartes d'attaque permettent de réduire les points de vie de l'adversaire\n" +
            "- Les cartes de popularité augmentent la popularité du joueur\n" +
            "- Les cartes spéciales ont des effets uniques liés au capitaine\n" +
            "- Les joueurs peuvent gagner de l'or et l'utiliser au marché\n" +
            "- Objectif: atteindre 5 points de popularité OU réduire les points de vie de l'adversaire à 0\n" +
            "- Après chaque tour, le joueur doit avoir exactement 4 cartes en main",
            "Règles du jeu",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * @brief Démarre une nouvelle partie
     */
    private void demarrerPartie() {
        // Réinitialiser l'interface si une partie est déjà en cours
        if (partieEnCours) {
            int reponse = JOptionPane.showConfirmDialog(this,
                "Une partie est déjà en cours. Voulez-vous l'abandonner et commencer une nouvelle partie ?",
                "Nouvelle partie",
                JOptionPane.YES_NO_OPTION);
            
            if (reponse != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Réinitialiser l'interface
            logArea.setText("");
            panelCartes.removeAll();
            updatePlayerPanels();
        }
        
        logArea.append("=== Nouvelle partie ===\n");
        
        // Configuration du joueur 1
        String nom1 = JOptionPane.showInputDialog(this, "Entrez le nom du Joueur 1:", "Joueur 1");
        if (nom1 == null || nom1.trim().isEmpty()) {
            nom1 = "Joueur 1";
        }
        
        Pirate pirate1 = selectionnerCapitaine("Choisissez le capitaine pour " + nom1);
        if (pirate1 == null) {
            logArea.append("Partie annulée.\n");
            return;
        }
        
        // Configuration du joueur 2
        String nom2 = JOptionPane.showInputDialog(this, "Entrez le nom du Joueur 2:", "Joueur 2");
        if (nom2 == null || nom2.trim().isEmpty()) {
            nom2 = "Joueur 2";
        }
        
        Pirate pirate2 = selectionnerCapitaine("Choisissez le capitaine pour " + nom2);
        if (pirate2 == null) {
            logArea.append("Partie annulée.\n");
            return;
        }
        
        // Configurer les joueurs
        controlJeu.setJoueur1(nom1, pirate1);
        controlJeu.setJoueur2(nom2, pirate2);
        
        // Initialiser les mains des joueurs
        controlJeu.initialiserMainJoueur(1);
        controlJeu.initialiserMainJoueur(2);
        
        // Mettre à jour l'interface
        partieEnCours = true;
        joueurActuel = 1;
        updatePlayerPanels();
        updateCardDisplay();
        
        // Activer les boutons pour le joueur 1
        activerBoutonsTour(true);
        
        logArea.append("La partie commence !\n");
        logArea.append("C'est au tour de " + controlJeu.getJoueur(joueurActuel).getNom() + ".\n");
    }
    
    /**
     * @brief Permet au joueur de sélectionner un capitaine
     * 
     * @param titre Titre de la boîte de dialogue
     * @return Le pirate sélectionné, ou null si annulé
     */
    private Pirate selectionnerCapitaine(String titre) {
        Object[] options = {
            "Barbe Noire (bonus aux cartes d'attaque)",
            "Jack Sparrow (bonus à l'or et aux cartes spéciales)",
            "Anne Bonny (bonus aux cartes de popularité)",
            "Barbe Rouge (équilibré)"
        };
        
        int choix = JOptionPane.showOptionDialog(this,
            "Choisissez votre capitaine:",
            titre,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        switch (choix) {
            case 0: return new Pirate("Barbe Noire");
            case 1: return new Pirate("Jack Sparrow");
            case 2: return new Pirate("Anne Bonny");
            case 3: return new Pirate("Barbe Rouge");
            default: return null;
        }
    }
    
    /**
     * @brief Met à jour les panneaux d'information des joueurs
     */
    private void updatePlayerPanels() {
        // Mise à jour du panneau du joueur 1
        updatePlayerPanel(panelJoueur1, 1);
        
        // Mise à jour du panneau du joueur 2
        updatePlayerPanel(panelJoueur2, 2);
        
        // Mettre en évidence le joueur actuel
        panelJoueur1.setBorder(BorderFactory.createTitledBorder(
            joueurActuel == 1 ? "→ Joueur 1 ←" : "Joueur 1"));
        panelJoueur2.setBorder(BorderFactory.createTitledBorder(
            joueurActuel == 2 ? "→ Joueur 2 ←" : "Joueur 2"));
    }
    
    /**
     * @brief Met à jour un panneau d'information d'un joueur
     * 
     * @param panel Panneau à mettre à jour
     * @param joueurId ID du joueur
     */
    private void updatePlayerPanel(JPanel panel, int joueurId) {
        // Si le jeu n'est pas encore initialisé
        if (!partieEnCours) {
            ((JLabel) panel.getClientProperty("lblNom")).setText("Nom: -");
            ((JLabel) panel.getClientProperty("lblPirate")).setText("Capitaine: -");
            ((JLabel) panel.getClientProperty("lblVie")).setText("Vie: 0/5");
            ((JLabel) panel.getClientProperty("lblPopularite")).setText("Popularité: 0/5");
            ((JLabel) panel.getClientProperty("lblOr")).setText("Or: 0");
            
            // Vider la main
            JPanel panelMain = (JPanel) panel.getClientProperty("panelMain");
            panelMain.removeAll();
            panelMain.revalidate();
            panelMain.repaint();
            return;
        }
        
        // Obtenir les informations du joueur
        ((JLabel) panel.getClientProperty("lblNom")).setText("Nom: " + controlJeu.getJoueur(joueurId).getNom());
        ((JLabel) panel.getClientProperty("lblPirate")).setText("Capitaine: " + controlJeu.getJoueur(joueurId).getPirate().getNom());
        ((JLabel) panel.getClientProperty("lblVie")).setText("Vie: " + controlJeu.getJoueur(joueurId).getPointsDeVie() + "/5");
        ((JLabel) panel.getClientProperty("lblPopularite")).setText("Popularité: " + controlJeu.getJoueur(joueurId).getPopularite() + "/5");
        ((JLabel) panel.getClientProperty("lblOr")).setText("Or: " + controlJeu.getJoueur(joueurId).getOr());
        
        // Mettre à jour la main du joueur (simplement avec le nombre de cartes)
        JPanel panelMain = (JPanel) panel.getClientProperty("panelMain");
        panelMain.removeAll();
        
        // Afficher les cartes en main du joueur actuel uniquement
        if (joueurId == joueurActuel) {
            List<Carte> main = controlJeu.getJoueur(joueurId).getMain();
            for (Carte carte : main) {
                JButton btnCarte = createCardButton(carte, joueurId);
                panelMain.add(btnCarte);
                panelMain.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        } else {
            // Pour l'adversaire, n'afficher que le nombre de cartes
            int nbCartes = controlJeu.getJoueur(joueurId).getMain().size();
            JLabel lblNbCartes = new JLabel(nbCartes + " carte(s) en main");
            lblNbCartes.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelMain.add(lblNbCartes);
        }
        
        panelMain.revalidate();
        panelMain.repaint();
    }
    
    /**
     * @brief Crée un bouton pour une carte
     * 
     * @param carte La carte à représenter
     * @param joueurId ID du joueur à qui appartient la carte
     * @return Bouton configuré pour la carte
     */
    private JButton createCardButton(Carte carte, int joueurId) {
        JButton btn = new JButton(carte.getNomCarte());
        btn.setToolTipText("<html>" + carte.getNomCarte() + "<br>Type: " + carte.getType() + 
                           "<br>Valeur: " + carte.getValeur() + 
                           "<br>" + carte.getDescription() + "</html>");
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setMaximumSize(new Dimension(160, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Ajouter une action pour jouer la carte
        btn.addActionListener(e -> jouerCarte(carte, joueurId));
        
        return btn;
    }
    
    /**
     * @brief Met à jour l'affichage des cartes sur le plateau
     */
    private void updateCardDisplay() {
        panelCartes.removeAll();
        
        // Ici, on pourrait afficher les cartes en jeu (cartes d'attaque, de popularité, etc.)
        // Ce serait une bonne occasion de montrer visuellement l'état du jeu
        
        panelCartes.revalidate();
        panelCartes.repaint();
    }
    
    /**
     * @brief Active ou désactive les boutons d'action pour un tour
     * 
     * @param actif true pour activer, false pour désactiver
     */
    private void activerBoutonsTour(boolean actif) {
        btnPiocher.setEnabled(actif);
        btnMarche.setEnabled(actif);
        btnFinTour.setEnabled(actif);
    }
    
    /**
     * @brief Gère l'action de piocher une carte
     */
    private void piocherCarte() {
        Carte carte = controlJeu.piocherCarte(joueurActuel);
        logArea.append(controlJeu.getJoueur(joueurActuel).getNom() + " a pioché une carte: " + carte.getNomCarte() + "\n");
        
        updatePlayerPanels();
        
        // Désactiver le bouton de pioche une fois qu'une carte a été piochée
        btnPiocher.setEnabled(false);
    }
    
    /**
     * @brief Gère l'action de jouer une carte
     * 
     * @param carte La carte à jouer
     * @param joueurId L'ID du joueur qui joue la carte
     */
    private void jouerCarte(Carte carte, int joueurId) {
        if (joueurId != joueurActuel) {
            return; // Ne devrait pas arriver, mais au cas où
        }
        
        // Jouer la carte
        int indexCarte = controlJeu.getJoueur(joueurId).getMain().indexOf(carte);
        if (indexCarte != -1) {
            controlJeu.jouerCarte(joueurId, indexCarte);
            logArea.append(controlJeu.getJoueur(joueurId).getNom() + " a joué la carte: " + carte.getNomCarte() + "\n");
            
            // Mettre à jour l'interface
            updatePlayerPanels();
            updateCardDisplay();
            
            // Vérifier si la partie est terminée
            if (controlJeu.verifierVictoire()) {
                partieTerminee();
                return;
            }
        }
    }
    
    /**
     * @brief Ouvre l'interface du marché
     */
    private void ouvrirMarche() {
        JDialog dialogMarche = new JDialog(this, "Marché", true);
        dialogMarche.setSize(500, 400);
        dialogMarche.setLocationRelativeTo(this);
        dialogMarche.setLayout(new BorderLayout());
        
        // Panneau pour afficher les cartes disponibles au marché
        JPanel panelCartesMarche = new JPanel();
        panelCartesMarche.setLayout(new BoxLayout(panelCartesMarche, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelCartesMarche);
        dialogMarche.add(scrollPane, BorderLayout.CENTER);
        
        // Panneau d'information
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblOr = new JLabel("Or disponible: " + controlJeu.getJoueur(joueurActuel).getOr());
        panelInfo.add(lblOr);
        dialogMarche.add(panelInfo, BorderLayout.NORTH);
        
        // Bouton pour fermer le marché
        JButton btnFermer = new JButton("Fermer le marché");
        btnFermer.addActionListener(e -> dialogMarche.dispose());
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnFermer);
        dialogMarche.add(panelBoutons, BorderLayout.SOUTH);
        
        // Ajouter les cartes disponibles au marché
        List<Carte> cartesMarche = controlMarche.getCartesDisponibles();
        for (int i = 0; i < cartesMarche.size(); i++) {
            Carte carte = cartesMarche.get(i);
            JPanel panelCarte = new JPanel();
            panelCarte.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelCarte.setBorder(BorderFactory.createEtchedBorder());
            
            JLabel lblCarte = new JLabel(carte.getNomCarte() + " (" + carte.getType() + ", coût: " + carte.getCout() + ")");
            JButton btnAcheter = new JButton("Acheter");
            
            // Désactiver le bouton si le joueur n'a pas assez d'or
            if (controlJeu.getJoueur(joueurActuel).getOr() < carte.getCout()) {
                btnAcheter.setEnabled(false);
                btnAcheter.setToolTipText("Pas assez d'or");
            }
            
            final int index = i;
            btnAcheter.addActionListener(e -> {
                boolean achatReussi = controlMarche.acheterCarte(joueurActuel, index);
                if (achatReussi) {
                    logArea.append(controlJeu.getJoueur(joueurActuel).getNom() + " a acheté la carte: " + carte.getNomCarte() + "\n");
                    dialogMarche.dispose();
                    updatePlayerPanels();
                } else {
                    JOptionPane.showMessageDialog(dialogMarche, 
                        "Achat échoué. Vous n'avez peut-être pas assez d'or.",
                        "Erreur d'achat", 
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            
            panelCarte.add(lblCarte);
            panelCarte.add(btnAcheter);
            panelCartesMarche.add(panelCarte);
        }
        
        dialogMarche.setVisible(true);
    }
    
    /**
     * @brief Gère la fin du tour du joueur actuel
     */
    private void finirTour() {
        logArea.append("Fin du tour de " + controlJeu.getJoueur(joueurActuel).getNom() + ".\n");
        
        // Gérer la taille de la main (doit être exactement 4 cartes)
        gererTailleMain();
        
        // Changer de joueur
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
        
        // Mettre à jour l'interface
        updatePlayerPanels();
        updateCardDisplay();
        
        // Réactiver les boutons pour le nouveau joueur
        btnPiocher.setEnabled(true);
        
        logArea.append("C'est au tour de " + controlJeu.getJoueur(joueurActuel).getNom() + ".\n");
    }
    
    /**
     * @brief Gère la taille de la main (doit être exactement 4 cartes)
     */
    private void gererTailleMain() {
        int tailleCourante = controlJeu.getJoueur(joueurActuel).getMain().size();
        
        // Si le joueur a plus de 4 cartes, il doit en défausser
        if (tailleCourante > 4) {
            int aDéfausser = tailleCourante - 4;
            for (int i = 0; i < aDéfausser; i++) {
                List<Carte> main = controlJeu.getJoueur(joueurActuel).getMain();
                String[] options = new String[main.size()];
                for (int j = 0; j < main.size(); j++) {
                    options[j] = main.get(j).getNomCarte();
                }
                
                int choix = JOptionPane.showOptionDialog(this,
                    "Vous devez défausser " + (aDéfausser - i) + " carte(s). Choisissez une carte à défausser :",
                    "Défausse de carte",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
                
                if (choix != JOptionPane.CLOSED_OPTION) {
                    controlJeu.defausserCarte(joueurActuel, choix);
                    logArea.append(controlJeu.getJoueur(joueurActuel).getNom() + " a défaussé la carte: " + options[choix] + "\n");
                }
            }
        }
        // Si le joueur a moins de 4 cartes, il pioche jusqu'à en avoir 4
        else if (tailleCourante < 4) {
            for (int i = 0; i < 4 - tailleCourante; i++) {
                Carte carte = controlJeu.piocherCarte(joueurActuel);
                logArea.append(controlJeu.getJoueur(joueurActuel).getNom() + " a pioché une carte: " + carte.getNomCarte() + "\n");
            }
        }
        
        updatePlayerPanels();
    }
    
    /**
     * @brief Gère la fin de la partie
     */
    private void partieTerminee() {
        activerBoutonsTour(false);
        partieEnCours = false;
        
        // Déterminer le gagnant
        String gagnant = "";
        String raisonVictoire = "";
        
        if (controlJeu.getJoueur(2).getPointsDeVie() <= 0 || controlJeu.getJoueur(1).getPopularite() >= 5) {
            gagnant = controlJeu.getJoueur(1).getNom();
            if (controlJeu.getJoueur(2).getPointsDeVie() <= 0) {
                raisonVictoire = "a réduit les points de vie de son adversaire à 0";
            } else {
                raisonVictoire = "a atteint 5 points de popularité";
            }
        } else if (controlJeu.getJoueur(1).getPointsDeVie() <= 0 || controlJeu.getJoueur(2).getPopularite() >= 5) {
            gagnant = controlJeu.getJoueur(2).getNom();
            if (controlJeu.getJoueur(1).getPointsDeVie() <= 0) {
                raisonVictoire = "a réduit les points de vie de son adversaire à 0";
            } else {
                raisonVictoire = "a atteint 5 points de popularité";
            }
        }
        
        logArea.append("\n=== Fin de la partie ===\n");
        logArea.append("Le gagnant est : " + gagnant + " qui " + raisonVictoire + " !\n");
        logArea.append("Félicitations !\n");
        
        JOptionPane.showMessageDialog(this,
            "Le gagnant est : " + gagnant + " qui " + raisonVictoire + " !\nFélicitations !",
            "Fin de la partie",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Point d'entrée pour tester l'interface graphique
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Pour le test, créer des contrôleurs
                ControlJeu controlJeu = new ControlJeu();
                ControlMarche controlMarche = controlJeu.getControlMarche();
                
                BoundaryJeuSwing frame = new BoundaryJeuSwing(controlJeu, controlMarche);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}