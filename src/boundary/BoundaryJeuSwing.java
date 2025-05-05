package boundary;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import carte.Carte;
import controllers.ControlJeu;
import controllers.ControlMarche;
import joueur.Pirate;
import joueur.Joueur; // Ajout de l'import

/**
 * @brief FrontiÃ¨re gÃ©rant les interactions avec l'utilisateur via une interface graphique Swing
 * 
 * Cette classe implÃ©mente l'interface utilisateur graphique du jeu
 * conformÃ©ment au modÃ¨le ECB.
 */
public class BoundaryJeuSwing extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // ContrÃ´leurs
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
     * @param controlJeu ContrÃ´leur principal du jeu
     * @param controlMarche ContrÃ´leur du marchÃ©
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
        
        // Panneau central (plateau de jeu et contrÃ´les)
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
        
        // Panneau pour les boutons de contrÃ´le
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentral.add(panelControles, BorderLayout.NORTH);
        
        // Boutons de contrÃ´le
        btnPiocher = new JButton("Piocher une carte");
        btnPiocher.setEnabled(false);
        btnPiocher.addActionListener(e -> piocherCarte());
        panelControles.add(btnPiocher);
        
        btnMarche = new JButton("Visiter le marchÃ©");
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
     * @brief CrÃ©e la barre de menu du jeu
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
        
        JMenuItem menuItemRegles = new JMenuItem("RÃ¨gles du jeu");
        menuItemRegles.addActionListener(e -> afficherRegles());
        menuJeu.add(menuItemRegles);
        
        menuJeu.addSeparator();
        
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.addActionListener(e -> System.exit(0));
        menuJeu.add(menuItemQuitter);
    }
    
    /**
     * @brief CrÃ©e un panneau d'information pour un joueur
     * 
     * @param joueurId ID du joueur (1 ou 2)
     * @return Panneau configurÃ© pour le joueur
     */
    private JPanel createPlayerPanel(int joueurId) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 600));
        panel.setBorder(BorderFactory.createTitledBorder("Joueur " + joueurId));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Composants qui seront mis Ã  jour pendant le jeu
        JLabel lblNom = new JLabel("Nom: -");
        JLabel lblPirate = new JLabel("Capitaine: -");
        JLabel lblVie = new JLabel("Vie: 0/5");
        JLabel lblPopularite = new JLabel("PopularitÃ©: 0/5");
        JLabel lblOr = new JLabel("Or: 0");
        
        // Ã‰tiquettes pour les informations du joueur
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
        
        // Stocker les rÃ©fÃ©rences aux composants pour les mettre Ã  jour plus tard
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
     * @brief Affiche les rÃ¨gles du jeu
     */
    private void afficherRegles() {
        JOptionPane.showMessageDialog(this,
            "=== RÃ¨gles du Jeu des Pirates ===\n\n" +
            "- Deux joueurs s'affrontent en utilisant des cartes\n" +
            "- Chaque joueur commence avec 5 points de vie et 0 point de popularitÃ©\n" +
            "- Ã€ chaque tour, un joueur pioche une carte et peut jouer jusqu'Ã  2 cartes\n" +
            "- Les cartes d'attaque permettent de rÃ©duire les points de vie de l'adversaire\n" +
            "- Les cartes de popularitÃ© augmentent la popularitÃ© du joueur\n" +
            "- Les cartes spÃ©ciales ont des effets uniques liÃ©s au capitaine\n" +
            "- Les joueurs peuvent gagner de l'or et l'utiliser au marchÃ©\n" +
            "- Objectif: atteindre 5 points de popularitÃ© OU rÃ©duire les points de vie de l'adversaire Ã  0\n" +
            "- AprÃ¨s chaque tour, le joueur doit avoir exactement 4 cartes en main",
            "RÃ¨gles du jeu",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * @brief DÃ©marre une nouvelle partie
     */
    private void demarrerPartie() {
        // RÃ©initialiser l'interface si une partie est dÃ©jÃ  en cours
        if (partieEnCours) {
            int reponse = JOptionPane.showConfirmDialog(this,
                "Une partie est dÃ©jÃ  en cours. Voulez-vous l'abandonner et commencer une nouvelle partie ?",
                "Nouvelle partie",
                JOptionPane.YES_NO_OPTION);
            
            if (reponse != JOptionPane.YES_OPTION) {
                return;
            }
            
            // RÃ©initialiser l'interface
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
            logArea.append("Partie annulÃ©e.\n");
            return;
        }
        
        // Configuration du joueur 2
        String nom2 = JOptionPane.showInputDialog(this, "Entrez le nom du Joueur 2:", "Joueur 2");
        if (nom2 == null || nom2.trim().isEmpty()) {
            nom2 = "Joueur 2";
        }
        
        Pirate pirate2 = selectionnerCapitaine("Choisissez le capitaine pour " + nom2);
        if (pirate2 == null) {
            logArea.append("Partie annulÃ©e.\n");
            return;
        }
        
        // Configurer les joueurs
        controlJeu.setJoueur1(nom1, pirate1);
        controlJeu.setJoueur2(nom2, pirate2);
        
        // Initialiser les mains des joueurs
        controlJeu.initialiserMainJoueur(1);
        controlJeu.initialiserMainJoueur(2);
        
        // Mettre Ã  jour l'interface
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
     * @brief Permet au joueur de sÃ©lectionner un capitaine
     * 
     * @param titre Titre de la boÃ®te de dialogue
     * @return Le pirate sÃ©lectionnÃ©, ou null si annulÃ©
     */
    private Pirate selectionnerCapitaine(String titre) {
        Object[] options = {
            "Barbe Noire (bonus aux cartes d'attaque)",
            "Jack Sparrow (bonus Ã  l'or et aux cartes spÃ©ciales)",
            "Anne Bonny (bonus aux cartes de popularitÃ©)",
            "Barbe Rouge (Ã©quilibrÃ©)"
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
     * @brief Met Ã  jour les panneaux d'information des joueurs
     */
    private void updatePlayerPanels() {
        // Mise Ã  jour du panneau du joueur 1
        updatePlayerPanel(panelJoueur1, 1);
        
        // Mise Ã  jour du panneau du joueur 2
        updatePlayerPanel(panelJoueur2, 2);
        
        // Mettre en Ã©vidence le joueur actuel
        panelJoueur1.setBorder(BorderFactory.createTitledBorder(
            joueurActuel == 1 ? "â†’ Joueur 1 â†�" : "Joueur 1"));
        panelJoueur2.setBorder(BorderFactory.createTitledBorder(
            joueurActuel == 2 ? "â†’ Joueur 2 â†�" : "Joueur 2"));
    }
    
    /**
     * @brief Met Ã  jour un panneau d'information d'un joueur
     * 
     * @param panel Panneau Ã  mettre Ã  jour
     * @param joueurId ID du joueur
     */
    private void updatePlayerPanel(JPanel panel, int joueurId) {
        // Si le jeu n'est pas encore initialisÃ©
        if (!partieEnCours) {
            ((JLabel) panel.getClientProperty("lblNom")).setText("Nom: -");
            ((JLabel) panel.getClientProperty("lblPirate")).setText("Capitaine: -");
            ((JLabel) panel.getClientProperty("lblVie")).setText("Vie: 0/5");
            ((JLabel) panel.getClientProperty("lblPopularite")).setText("PopularitÃ©: 0/5");
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
        ((JLabel) panel.getClientProperty("lblPopularite")).setText("PopularitÃ©: " + controlJeu.getJoueur(joueurId).getPopularite() + "/5");
        ((JLabel) panel.getClientProperty("lblOr")).setText("Or: " + controlJeu.getJoueur(joueurId).getOr());
        
        // Mettre Ã  jour la main du joueur (simplement avec le nombre de cartes)
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
     * @brief CrÃ©e un bouton pour une carte
     * 
     * @param carte La carte Ã  reprÃ©senter
     * @param joueurId ID du joueur Ã  qui appartient la carte
     * @return Bouton configurÃ© pour la carte
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
     * @brief Met Ã  jour l'affichage des cartes sur le plateau
     */
    private void updateCardDisplay() {
        panelCartes.removeAll();
        
        // Ici, on pourrait afficher les cartes en jeu (cartes d'attaque, de popularitÃ©, etc.)
        // Ce serait une bonne occasion de montrer visuellement l'Ã©tat du jeu
        
        panelCartes.revalidate();
        panelCartes.repaint();
    }
    
    /**
     * @brief Active ou dÃ©sactive les boutons d'action pour un tour
     * 
     * @param actif true pour activer, false pour dÃ©sactiver
     */
    private void activerBoutonsTour(boolean actif) {
        btnPiocher.setEnabled(actif);
        btnMarche.setEnabled(actif);
        btnFinTour.setEnabled(actif);
    }
    
    /**
     * @brief GÃ¨re l'action de piocher une carte
     */
    private void piocherCarte() {
        Carte carte = controlJeu.piocherCarte();
        if (carte != null) {
            logArea.append(controlJeu.getJoueur(joueurActuel).getNom() + " a piochÃ© : " + carte.getNomCarte() + "\n");
            updatePlayerPanels();
        } else {
            logArea.append("La pioche est vide !\n");
        }
        btnPiocher.setEnabled(false);
    }

    /**
     * @brief GÃ¨re l'action de jouer une carte
     * 
     * @param carte La carte Ã  jouer
     * @param joueurId L'ID du joueur qui joue la carte
     */
    private void jouerCarte(Carte carte, int joueurId) {
        if (joueurId != controlJeu.getJoueurActif()) {
            logArea.append("Ce n'est pas votre tour !\n");
            return;
        }

        int indexCarte = controlJeu.getJoueur(joueurId).getMain().indexOf(carte);
        if (indexCarte != -1) {
            boolean succes = controlJeu.jouerCarte(indexCarte);
            if (succes) {
                logArea.append(controlJeu.getJoueur(joueurId).getNom() + " a jouÃ© la carte: " + carte.getNomCarte() + "\n");

                updatePlayerPanels();
                updateCardDisplay();

                if (controlJeu.verifierVictoire() != 0) {
                    partieTerminee();
                    return;
                }
            } else {
                logArea.append("Impossible de jouer cette carte.\n");
            }
        }
    }

    /**
     * @brief Ouvre l'interface du marchÃ©
     */
    private void ouvrirMarche() {
        JDialog dialogMarche = new JDialog(this, "MarchÃ©", true);
        dialogMarche.setSize(500, 400);
        dialogMarche.setLocationRelativeTo(this);
        dialogMarche.setLayout(new BorderLayout());
        
        JPanel panelCartesMarche = new JPanel();
        panelCartesMarche.setLayout(new BoxLayout(panelCartesMarche, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelCartesMarche);
        dialogMarche.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblOr = new JLabel("Or disponible: " + controlJeu.getJoueur(joueurActuel).getOr());
        panelInfo.add(lblOr);
        dialogMarche.add(panelInfo, BorderLayout.NORTH);
        
        JButton btnFermer = new JButton("Fermer le marchÃ©");
        btnFermer.addActionListener(e -> dialogMarche.dispose());
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnFermer);
        dialogMarche.add(panelBoutons, BorderLayout.SOUTH);
        
        List<Carte> cartesMarche = controlMarche.getCartesDisponibles();
        for (int i = 0; i < cartesMarche.size(); i++) {
            Carte carte = cartesMarche.get(i);
            JPanel panelCarte = new JPanel();
            panelCarte.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelCarte.setBorder(BorderFactory.createEtchedBorder());
            
            JLabel lblCarte = new JLabel(carte.getNomCarte() + " (" + carte.getType() + ", coÃ»t: " + carte.getCout() + ")");
            JButton btnAcheter = new JButton("Acheter");
            
            if (controlJeu.getJoueur(joueurActuel).getOr() < carte.getCout()) {
                btnAcheter.setEnabled(false);
                btnAcheter.setToolTipText("Pas assez d'or");
            }
            
            final int index = i;
            btnAcheter.addActionListener(e -> {
                boolean achatReussi = controlMarche.acheterCarte(index);
                if (achatReussi) {
                    logArea.append(controlJeu.getJoueur(joueurActuel).getNom() + " a achetÃ© la carte: " + carte.getNomCarte() + "\n");
                    dialogMarche.dispose();
                    updatePlayerPanels();
                } else {
                    JOptionPane.showMessageDialog(dialogMarche, 
                        "Achat Ã©chouÃ©. Vous n'avez peut-Ãªtre pas assez d'or.",
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
     * @brief GÃ¨re la fin du tour du joueur actuel
     */
    private void finirTour() {
        logArea.append("Fin du tour de " + controlJeu.getJoueur(joueurActuel).getNom() + ".\n");

        gererTailleMain();

        controlJeu.passerAuJoueurSuivant();
        joueurActuel = controlJeu.getJoueurActif();

        updatePlayerPanels();
        updateCardDisplay();

        activerBoutonsTour(true);

        logArea.append("C'est au tour de " + controlJeu.getJoueur(joueurActuel).getNom() + ".\n");
    }

    /**
     * @brief GÃ¨re la taille de la main (doit Ãªtre exactement 4 cartes)
     */
    private void gererTailleMain() {
        int joueurCourantIndex = controlJeu.getJoueurActif();
        int tailleCourante = controlJeu.getJoueur(joueurCourantIndex).getMain().size();

        if (tailleCourante > 4) {
            int aDefausser = tailleCourante - 4;
            logArea.append(controlJeu.getJoueur(joueurCourantIndex).getNom() + " doit dÃ©fausser " + aDefausser + " carte(s).\n");
            for (int i = 0; i < aDefausser; i++) {
                List<Carte> main = controlJeu.getJoueur(joueurCourantIndex).getMain();
                if (main.isEmpty()) break;

                String[] options = new String[main.size()];
                for (int j = 0; j < main.size(); j++) {
                    options[j] = main.get(j).getNomCarte();
                }

                int choix = JOptionPane.showOptionDialog(this,
                    "Vous devez dÃ©fausser " + (aDefausser - i) + " carte(s). Choisissez une carte Ã  dÃ©fausser :",
                    "DÃ©fausse de carte",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

                if (choix != JOptionPane.CLOSED_OPTION) {
                    boolean defausseOk = controlJeu.defausserCarte(choix);
                    if (defausseOk) {
                        logArea.append(controlJeu.getJoueur(joueurCourantIndex).getNom() + " a dÃ©faussÃ© la carte: " + options[choix] + "\n");
                    } else {
                        logArea.append("Erreur lors de la dÃ©fausse.\n");
                        i--;
                    }
                } else {
                    logArea.append("DÃ©fausse annulÃ©e, dÃ©fausse de la premiÃ¨re carte par dÃ©faut.\n");
                    controlJeu.defausserCarte(0);
                }
                updatePlayerPanels();
            }
        } else if (tailleCourante < 4) {
            int aPiocher = 4 - tailleCourante;
            logArea.append(controlJeu.getJoueur(joueurCourantIndex).getNom() + " doit piocher " + aPiocher + " carte(s).\n");
            for (int i = 0; i < aPiocher; i++) {
                Carte carte = controlJeu.piocherCarte();
                if (carte != null) {
                    logArea.append(controlJeu.getJoueur(joueurCourantIndex).getNom() + " a piochÃ© une carte: " + carte.getNomCarte() + "\n");
                } else {
                    logArea.append("La pioche est vide ! Impossible de piocher plus.\n");
                    break;
                }
                updatePlayerPanels();
            }
        }

        updatePlayerPanels();
    }

    /**
     * @brief GÃ¨re la fin de la partie
     */
    private void partieTerminee() {
        activerBoutonsTour(false);
        partieEnCours = false;

        int vainqueurIndex = controlJeu.verifierVictoire();
        String gagnant = "";
        String raisonVictoire = "";

        if (vainqueurIndex != 0) {
            gagnant = controlJeu.getJoueur(vainqueurIndex - 1).getNom();
            Joueur j1 = controlJeu.getJoueur(0).getJoueur();
            Joueur j2 = controlJeu.getJoueur(1).getJoueur();
            if (j1.getPointsDeVie() <= 0) raisonVictoire = j2.getNom() + " a rÃ©duit les points de vie de " + j1.getNom() + " Ã  0";
            else if (j2.getPointsDeVie() <= 0) raisonVictoire = j1.getNom() + " a rÃ©duit les points de vie de " + j2.getNom() + " Ã  0";
            else if (j1.getPopularite() >= 5) raisonVictoire = j1.getNom() + " a atteint 5 points de popularitÃ©";
            else if (j2.getPopularite() >= 5) raisonVictoire = j2.getNom() + " a atteint 5 points de popularitÃ©";
            else raisonVictoire = "Condition de victoire non dÃ©terminÃ©e (peut-Ãªtre pioche vide ?)";
        } else {
            gagnant = "Personne (Ã‰galitÃ© ou pioche vide)";
            raisonVictoire = "La partie s'est terminÃ©e sans vainqueur clair.";
        }

        logArea.append("\n=== Fin de la partie ===\n");
        logArea.append("Le gagnant est : " + gagnant + ". Raison: " + raisonVictoire + " !\n");
        logArea.append("FÃ©licitations !\n");

        JOptionPane.showMessageDialog(this,
            "Le gagnant est : " + gagnant + ".\nRaison: " + raisonVictoire + " !\nFÃ©licitations !",
            "Fin de la partie",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Point d'entrÃ©e pour tester l'interface graphique
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Pour le test, crÃ©er des contrÃ´leurs
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