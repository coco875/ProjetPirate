/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ihm;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.Dimension;
import joueur.*;
import boundary.*;
import carte.Carte;
import java.util.*;
import controllers.*;

/**
 *
 * @author FNX4294A
 */
public class FramePlateau extends javax.swing.JFrame {
    private Pirate pirateJoueur1;
    private Pirate pirateJoueur2;
    private ControlJeu controlJeu;
    java.util.List<Carte> listeCartesJoueur1;
    java.util.List<Carte> listeCartesJoueur2;
    /**
     * Creates new form FramePlateau
     */
    public FramePlateau() {
    initComponents();
    PanelPirate.setSelectionCallback(selected -> {
        boutonValider.setEnabled(selected);
    });
    
    PanelPirate.setupLabelsDescription(labelDescriptionPirateGauche, labelDescriptionPirateDroite);
    
    setupFrame();
    setupPanelsTransparents();
    setupPanelPositions();
    setupFonts();
    setupPlateauChoix();
    setupPanelsPirate();
    setupBoutonValider();
    setupDescriptionLabels();
    refresh();
    
}
    
    private void setupFrame() {
        setSize(1920, 1080);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void setupPanelsTransparents() {
        setPanelOpaque(panelPiratesJoueur1, false);
        setPanelOpaque(panelPiratesJoueur2, false);
        setPanelOpaque(panelChoixDesPirates, false);
        setPanelOpaque(panelJoueur1, false);
        setPanelOpaque(panelJoueur2, false);
    }
    
    private void setPanelOpaque(JPanel panel, boolean opaque) {
        panel.setOpaque(opaque);
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                ((JPanel) comp).setOpaque(opaque);
            }
        }
    }
    
    private void setupPanelPositions() {
        panelChoixDesPirates.setBounds(500, 0, 1000, 200);
        panelJoueur1.setBounds(225, panelChoixDesPirates.getHeight() + 50, 400, 100);
        panelJoueur2.setBounds(1325, panelChoixDesPirates.getHeight() + 50, 400, 100);
        
        panelPiratesJoueur1.setBounds(275, panelJoueur1.getY() + panelJoueur1.getHeight() + 75, 400, 400);
        panelPiratesJoueur2.setBounds(1225, panelJoueur2.getY() + panelJoueur2.getHeight() + 75, 400, 400);
        labelDescriptionPirateGauche.setBounds(panelPiratesJoueur1.getX(), panelPiratesJoueur1.getY() + panelPiratesJoueur1.getHeight(), panelPiratesJoueur1.getWidth(), 150);
        labelDescriptionPirateDroite.setBounds(panelPiratesJoueur2.getX(), panelPiratesJoueur2.getY() + panelPiratesJoueur2.getHeight(), panelPiratesJoueur2.getWidth(), 150);
    }
    
    
    private void setupFonts() {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/ressources/fonts/xbones.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            labelChoixDesPirates.setFont(customFont.deriveFont(80f));
            labelJoueur1.setFont(customFont.deriveFont(40f));
            labelJoueur2.setFont(customFont.deriveFont(40f));

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            //En cas d'erreur utiliser une police par défaut
            labelChoixDesPirates.setFont(new Font("Serif", Font.PLAIN, 100));
            labelJoueur1.setFont(new Font("Serif", Font.PLAIN, 24));
            labelJoueur2.setFont(new Font("Serif", Font.PLAIN, 24));
        }
    }
    
    private void setupPlateauChoix() {
        PanelPlateau plateauChoix = new PanelPlateau();
        plateauChoix.setOpaque(false);
        panelChoix.setLayout(new BorderLayout());
        panelChoix.add(plateauChoix, BorderLayout.CENTER);
    }
    
    private void setupPanelsPirate() {
        //Groupe gauche
        setupPirateGroup(panelPiratesJoueur1, new JPanel[]{jPanel3, jPanel4, jPanel5, jPanel6}, true);
        
        //Groupe droit
        setupPirateGroup(panelPiratesJoueur2, new JPanel[]{jPanel11, jPanel12, jPanel13, jPanel14}, false);
    }
    
    
    
    
    
    private void setupBoutonValider() {
        boutonValider.setBounds(890, 900, 120, 40);
        boutonValider.setVisible(true);
        boutonValider.setEnabled(false);
        
        PanelPirate.setSelectionCallback(enable -> {
            boutonValider.setEnabled(enable);
        });
    }
    private void setupPirateGroup(JPanel parent, JPanel[] fils, boolean isLeftGroup) {
        java.util.List<Pirate> pirates = BoundaryJeu.getPiratesDisponibles();
        parent.setLayout(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(2, 2, 5, 5));
        grid.setOpaque(false);
        
        for (int i = 0; i < 4; i++) {
            fils[i].setLayout(new BorderLayout());
            fils[i].removeAll();
            fils[i].add(new PanelPirate(pirates.get(i), isLeftGroup), BorderLayout.CENTER);
            grid.add(fils[i]);
        }
        
        parent.add(grid, BorderLayout.CENTER);
    }
    private void refresh() {
        panelChoix.revalidate();
        panelChoix.repaint();
    }
    
    private void setupDescriptionLabels() {
        try{
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/ressources/fonts/xbones.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            labelDescriptionPirateGauche.setFont(customFont.deriveFont(25f));
            labelDescriptionPirateDroite.setFont(customFont.deriveFont(25f));
        }catch (FontFormatException | IOException e) {
            e.printStackTrace(); // ou gérer autrement
        }
        
        
        labelDescriptionPirateGauche = new JLabel("<html> </html>", JLabel.CENTER);
        labelDescriptionPirateDroite = new JLabel("<html> </html>", JLabel.CENTER);
        
        labelDescriptionPirateGauche.setForeground(Color.BLACK);
        labelDescriptionPirateDroite.setForeground(Color.BLACK);
        
      
      
        panelPiratesJoueur1.add(labelDescriptionPirateGauche, BorderLayout.SOUTH);
        panelPiratesJoueur2.add(labelDescriptionPirateDroite, BorderLayout.SOUTH);
    }
    
    private void setupImagesPirates() {
        //Création des panels
        panelImagePirate1 = new PanelImage(pirateJoueur1.getCheminImage());
        panelImagePirate2 = new PanelImage(pirateJoueur2.getCheminImage());

        Dimension size = new Dimension(75, 75);
        panelImagePirate1.setPreferredSize(size); 
        panelImagePirate2.setPreferredSize(size);
        panelImagePirate1.setSize(size); 
        panelImagePirate2.setSize(size);
        
        panelJeu.setLayout(null);
        
        panelImagePirate1.setBounds(915, 755, size.width, size.height);
        panelImagePirate2.setBounds(915, 250, size.width, size.height);
        
        panelJeu.add(panelImagePirate1);
        panelJeu.add(panelImagePirate2);
        
        panelJeu.revalidate();
        panelJeu.repaint();
        
        
        
    }
    
    private void setupMain(){
        Dimension sizeMain = new Dimension(1000, 200);
        panelMainJoueur1.setBounds(460, 830, sizeMain.width, sizeMain.height);
        panelMainJoueur2.setBounds(460, 0, sizeMain.width, sizeMain.height);
        
        panelMainJoueur1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelMainJoueur2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        panelMainJoueur1.setOpaque(false);
        panelMainJoueur2.setOpaque(false);
        
        panelJeu.add(panelMainJoueur1);
        panelJeu.add(panelMainJoueur2);
        
        setupCartesMain(panelMainJoueur1, panelMainJoueur2);
        
        panelJeu.revalidate();
        panelJeu.repaint();
        
    }
    
    private void setupCartesMain(JPanel main1, JPanel main2){   
        
        
        for(Carte carte : listeCartesJoueur1){
            panelMainJoueur1.add(new PanelCarte(carte));
        }
        
        for(Carte carte : listeCartesJoueur2){
            panelMainJoueur2.add(new PanelCarte(carte));
        }
       
        
        panelJeu.revalidate();
        panelJeu.repaint();
    
    }
    
    private void setupZones(){
        Dimension size = new Dimension(200,150);
        
        panelZoneStrategiqueJoueur1.setBounds(740, 370, size.width, size.height);
        panelZoneStrategiqueJoueur2.setBounds(980, 560, size.width, size.height);
        
        panelZoneOffensiveJoueur1.setBounds(980, 370, size.width, size.height);
        panelZoneOffensiveJoueur2.setBounds(740, 560, size.width, size.height);
        
        panelJeu.add(panelZoneStrategiqueJoueur1);
        panelJeu.add(panelZoneStrategiqueJoueur2);
        panelJeu.add(panelZoneOffensiveJoueur1);
        panelJeu.add(panelZoneOffensiveJoueur2);
        
        panelJeu.revalidate();
        panelJeu.repaint();
    }
    
    
    
    
    
    
    private void setupPlateauJeu(){
        panelJeu = new PanelPlateau();
        panelJeu.setOpaque(true); 
        panelJeu.setLayout(new BorderLayout());
        panelConteneur.add(panelJeu, "panelJeu");
        CardLayout c1 = (CardLayout) panelConteneur.getLayout();
        c1.show(panelConteneur, "panelJeu");
        panelJeu.revalidate();
        panelJeu.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelConteneur = new javax.swing.JPanel();
        panelChoix = new javax.swing.JPanel();
        panelPiratesJoueur1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        panelChoixDesPirates = new javax.swing.JPanel();
        labelChoixDesPirates = new javax.swing.JLabel();
        panelJoueur1 = new javax.swing.JPanel();
        labelJoueur1 = new javax.swing.JLabel();
        panelJoueur2 = new javax.swing.JPanel();
        labelJoueur2 = new javax.swing.JLabel();
        boutonValider = new javax.swing.JButton();
        panelPiratesJoueur2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        labelDescriptionPirateDroite = new javax.swing.JLabel();
        labelDescriptionPirateGauche = new javax.swing.JLabel();
        panelJeu = new javax.swing.JPanel();
        panelImagePirate1 = new javax.swing.JPanel();
        panelImagePirate2 = new javax.swing.JPanel();
        panelMainJoueur2 = new javax.swing.JPanel();
        panelMainJoueur1 = new javax.swing.JPanel();
        panelZoneStrategiqueJoueur1 = new javax.swing.JPanel();
        panelZoneOffensiveJoueur1 = new javax.swing.JPanel();
        panelZoneStrategiqueJoueur2 = new javax.swing.JPanel();
        panelZoneOffensiveJoueur2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelConteneur.setLayout(new java.awt.CardLayout());

        panelPiratesJoueur1.setPreferredSize(new java.awt.Dimension(160, 160));

        jPanel3.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel4.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel5.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel6.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelPiratesJoueur1Layout = new javax.swing.GroupLayout(panelPiratesJoueur1);
        panelPiratesJoueur1.setLayout(panelPiratesJoueur1Layout);
        panelPiratesJoueur1Layout.setHorizontalGroup(
            panelPiratesJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPiratesJoueur1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPiratesJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPiratesJoueur1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPiratesJoueur1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPiratesJoueur1Layout.setVerticalGroup(
            panelPiratesJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPiratesJoueur1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPiratesJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPiratesJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        labelChoixDesPirates.setText("CHOIX DES PIRATES");

        javax.swing.GroupLayout panelChoixDesPiratesLayout = new javax.swing.GroupLayout(panelChoixDesPirates);
        panelChoixDesPirates.setLayout(panelChoixDesPiratesLayout);
        panelChoixDesPiratesLayout.setHorizontalGroup(
            panelChoixDesPiratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChoixDesPiratesLayout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addComponent(labelChoixDesPirates)
                .addGap(146, 146, 146))
        );
        panelChoixDesPiratesLayout.setVerticalGroup(
            panelChoixDesPiratesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChoixDesPiratesLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(labelChoixDesPirates)
                .addContainerGap())
        );

        labelJoueur1.setText("Joueur 1");

        javax.swing.GroupLayout panelJoueur1Layout = new javax.swing.GroupLayout(panelJoueur1);
        panelJoueur1.setLayout(panelJoueur1Layout);
        panelJoueur1Layout.setHorizontalGroup(
            panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJoueur1Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(labelJoueur1)
                .addGap(36, 36, 36))
        );
        panelJoueur1Layout.setVerticalGroup(
            panelJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJoueur1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(labelJoueur1)
                .addGap(19, 19, 19))
        );

        labelJoueur2.setText("Joueur 2");

        javax.swing.GroupLayout panelJoueur2Layout = new javax.swing.GroupLayout(panelJoueur2);
        panelJoueur2.setLayout(panelJoueur2Layout);
        panelJoueur2Layout.setHorizontalGroup(
            panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJoueur2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(labelJoueur2)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        panelJoueur2Layout.setVerticalGroup(
            panelJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJoueur2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelJoueur2)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        boutonValider.setText("Valider");
        boutonValider.setEnabled(false);
        boutonValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonValiderActionPerformed(evt);
            }
        });

        panelPiratesJoueur2.setPreferredSize(new java.awt.Dimension(160, 160));

        jPanel11.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel12.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel13.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jPanel14.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelPiratesJoueur2Layout = new javax.swing.GroupLayout(panelPiratesJoueur2);
        panelPiratesJoueur2.setLayout(panelPiratesJoueur2Layout);
        panelPiratesJoueur2Layout.setHorizontalGroup(
            panelPiratesJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPiratesJoueur2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPiratesJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPiratesJoueur2Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPiratesJoueur2Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPiratesJoueur2Layout.setVerticalGroup(
            panelPiratesJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPiratesJoueur2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPiratesJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPiratesJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelChoixLayout = new javax.swing.GroupLayout(panelChoix);
        panelChoix.setLayout(panelChoixLayout);
        panelChoixLayout.setHorizontalGroup(
            panelChoixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChoixLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(panelChoixDesPirates, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelChoixLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(panelJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChoixLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boutonValider)
                .addGap(164, 164, 164))
            .addGroup(panelChoixLayout.createSequentialGroup()
                .addGroup(panelChoixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChoixLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(panelPiratesJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChoixLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelDescriptionPirateGauche, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelChoixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPiratesJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDescriptionPirateDroite, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        panelChoixLayout.setVerticalGroup(
            panelChoixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChoixLayout.createSequentialGroup()
                .addComponent(panelChoixDesPirates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(panelChoixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChoixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPiratesJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPiratesJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panelChoixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDescriptionPirateGauche, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDescriptionPirateDroite, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                .addComponent(boutonValider)
                .addGap(18, 18, 18))
        );

        panelConteneur.add(panelChoix, "card2");

        javax.swing.GroupLayout panelImagePirate1Layout = new javax.swing.GroupLayout(panelImagePirate1);
        panelImagePirate1.setLayout(panelImagePirate1Layout);
        panelImagePirate1Layout.setHorizontalGroup(
            panelImagePirate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 69, Short.MAX_VALUE)
        );
        panelImagePirate1Layout.setVerticalGroup(
            panelImagePirate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelImagePirate2Layout = new javax.swing.GroupLayout(panelImagePirate2);
        panelImagePirate2.setLayout(panelImagePirate2Layout);
        panelImagePirate2Layout.setHorizontalGroup(
            panelImagePirate2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 86, Short.MAX_VALUE)
        );
        panelImagePirate2Layout.setVerticalGroup(
            panelImagePirate2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );

        panelMainJoueur2.setBackground(new java.awt.Color(0, 255, 255));
        panelMainJoueur2.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout panelMainJoueur2Layout = new javax.swing.GroupLayout(panelMainJoueur2);
        panelMainJoueur2.setLayout(panelMainJoueur2Layout);
        panelMainJoueur2Layout.setHorizontalGroup(
            panelMainJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );
        panelMainJoueur2Layout.setVerticalGroup(
            panelMainJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );

        panelMainJoueur1.setBackground(new java.awt.Color(0, 255, 255));
        panelMainJoueur1.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout panelMainJoueur1Layout = new javax.swing.GroupLayout(panelMainJoueur1);
        panelMainJoueur1.setLayout(panelMainJoueur1Layout);
        panelMainJoueur1Layout.setHorizontalGroup(
            panelMainJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );
        panelMainJoueur1Layout.setVerticalGroup(
            panelMainJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 86, Short.MAX_VALUE)
        );

        panelZoneStrategiqueJoueur1.setBackground(new java.awt.Color(51, 255, 255));

        javax.swing.GroupLayout panelZoneStrategiqueJoueur1Layout = new javax.swing.GroupLayout(panelZoneStrategiqueJoueur1);
        panelZoneStrategiqueJoueur1.setLayout(panelZoneStrategiqueJoueur1Layout);
        panelZoneStrategiqueJoueur1Layout.setHorizontalGroup(
            panelZoneStrategiqueJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 79, Short.MAX_VALUE)
        );
        panelZoneStrategiqueJoueur1Layout.setVerticalGroup(
            panelZoneStrategiqueJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 67, Short.MAX_VALUE)
        );

        panelZoneOffensiveJoueur1.setBackground(new java.awt.Color(0, 255, 255));

        javax.swing.GroupLayout panelZoneOffensiveJoueur1Layout = new javax.swing.GroupLayout(panelZoneOffensiveJoueur1);
        panelZoneOffensiveJoueur1.setLayout(panelZoneOffensiveJoueur1Layout);
        panelZoneOffensiveJoueur1Layout.setHorizontalGroup(
            panelZoneOffensiveJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 67, Short.MAX_VALUE)
        );
        panelZoneOffensiveJoueur1Layout.setVerticalGroup(
            panelZoneOffensiveJoueur1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );

        panelZoneStrategiqueJoueur2.setBackground(new java.awt.Color(0, 255, 255));

        javax.swing.GroupLayout panelZoneStrategiqueJoueur2Layout = new javax.swing.GroupLayout(panelZoneStrategiqueJoueur2);
        panelZoneStrategiqueJoueur2.setLayout(panelZoneStrategiqueJoueur2Layout);
        panelZoneStrategiqueJoueur2Layout.setHorizontalGroup(
            panelZoneStrategiqueJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );
        panelZoneStrategiqueJoueur2Layout.setVerticalGroup(
            panelZoneStrategiqueJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        panelZoneOffensiveJoueur2.setBackground(new java.awt.Color(51, 255, 255));

        javax.swing.GroupLayout panelZoneOffensiveJoueur2Layout = new javax.swing.GroupLayout(panelZoneOffensiveJoueur2);
        panelZoneOffensiveJoueur2.setLayout(panelZoneOffensiveJoueur2Layout);
        panelZoneOffensiveJoueur2Layout.setHorizontalGroup(
            panelZoneOffensiveJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );
        panelZoneOffensiveJoueur2Layout.setVerticalGroup(
            panelZoneOffensiveJoueur2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelJeuLayout = new javax.swing.GroupLayout(panelJeu);
        panelJeu.setLayout(panelJeuLayout);
        panelJeuLayout.setHorizontalGroup(
            panelJeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJeuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelJeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelZoneOffensiveJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelJeuLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(panelZoneStrategiqueJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelJeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelJeuLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(panelZoneStrategiqueJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelZoneOffensiveJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelJeuLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(panelImagePirate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelJeuLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(panelImagePirate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJeuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelMainJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJeuLayout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(panelMainJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        panelJeuLayout.setVerticalGroup(
            panelJeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJeuLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(panelMainJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelImagePirate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelJeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelJeuLayout.createSequentialGroup()
                        .addComponent(panelZoneOffensiveJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelZoneStrategiqueJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelJeuLayout.createSequentialGroup()
                        .addComponent(panelZoneStrategiqueJoueur1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelZoneOffensiveJoueur2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(25, 25, 25)
                .addComponent(panelImagePirate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelMainJoueur2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );

        panelConteneur.add(panelJeu, "panelJeu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelConteneur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelConteneur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boutonValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonValiderActionPerformed

        pirateJoueur1 = PanelPirate.getSelectedLeft().getPirate();
        pirateJoueur2 = PanelPirate.getSelectedRight().getPirate();
        controlJeu = new ControlJeu();
        controlJeu.initialiserJeu(pirateJoueur1, pirateJoueur2);
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);
        listeCartesJoueur1 = controlJoueur1.getMain();
        ControlJoueur controlJoueur2 = controlJeu.getJoueur(1);
        listeCartesJoueur2 = controlJoueur2.getMain();
        
        setupPlateauJeu();
        setupImagesPirates();
        setupMain();
        setupZones();
    }//GEN-LAST:event_boutonValiderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FramePlateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePlateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePlateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePlateau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FramePlateau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonValider;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel labelChoixDesPirates;
    private javax.swing.JLabel labelDescriptionPirateDroite;
    private javax.swing.JLabel labelDescriptionPirateGauche;
    private javax.swing.JLabel labelJoueur1;
    private javax.swing.JLabel labelJoueur2;
    private javax.swing.JPanel panelChoix;
    private javax.swing.JPanel panelChoixDesPirates;
    private javax.swing.JPanel panelConteneur;
    private javax.swing.JPanel panelImagePirate1;
    private javax.swing.JPanel panelImagePirate2;
    private javax.swing.JPanel panelJeu;
    private javax.swing.JPanel panelJoueur1;
    private javax.swing.JPanel panelJoueur2;
    private javax.swing.JPanel panelMainJoueur1;
    private javax.swing.JPanel panelMainJoueur2;
    private javax.swing.JPanel panelPiratesJoueur1;
    private javax.swing.JPanel panelPiratesJoueur2;
    private javax.swing.JPanel panelZoneOffensiveJoueur1;
    private javax.swing.JPanel panelZoneOffensiveJoueur2;
    private javax.swing.JPanel panelZoneStrategiqueJoueur1;
    private javax.swing.JPanel panelZoneStrategiqueJoueur2;
    // End of variables declaration//GEN-END:variables
}
