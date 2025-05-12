/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ihm;

import java.awt.Graphics;
import java.awt.*;
import carte.*;
import static ihm.PanelPirate.notifySelectionState;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;
/**
 *
 * @author FNX4294A
 */
public class PanelCarte extends javax.swing.JPanel {
    private Image image;
    private Point pDessinIcone = new Point(0, 0);
    private Carte carte;
    private int x, y, xCarte, yCarte, xCentreCarte, yCentreCarte;
    
    /**
     * Creates new form panelCarte
     */
    public PanelCarte(Carte carte) {
        initComponents();
        this.carte = carte;
        String local_path = System.getProperty("user.dir");
        File image_path = new File(local_path + "/" + carte.getCheminImage());
        System.out.println(carte.getType());
        System.out.println(carte.getCheminImage());
        try {
            image = ImageIO.read(image_path);
        } catch (IOException io) {
            System.out.println("Error");
        }
        setOpaque(false);
        setPreferredSize(new Dimension(149, 190));
        this.setVisible(true);
        repaint();
        
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setToolTipText(carte.toString());
                 
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e){
                x = e.getX();
                y = e.getY();
            }
           
            
            
            
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                Point location = getLocation();
                xCarte = e.getX();
                yCarte = e.getY();
                setLocation(location.x + getWidth() / 2, location.y + yCarte);
                xCentreCarte = location.x + getWidth() / 2;
                yCentreCarte = location.y + getHeight() / 2;
                repaint();
            }
        });
    }
    public Carte getCarte(){
        return this.carte;
    }
   
    
    private void dessinerImage(Graphics g, String cheminImage, int taille) {
        try {
            String local_path = System.getProperty("user.dir");
            File imageFile = new File(local_path + cheminImage);
            Image img = ImageIO.read(imageFile);
            if (img == null) {
                System.out.println("L'image n'a pas pu être lue !");
            } else {
                System.out.println("Image chargée : " + imageFile.getName());
            }
            g.drawImage(img, pDessinIcone.x, pDessinIcone.y, taille, taille, this);
            pDessinIcone.x += taille + 5;  // Déplacer la position de p1.x pour la prochaine image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dessinerIcones(Graphics g) {
        pDessinIcone.x = 0;  // Réinitialisation de la position horizontale pour le prochain dessin
        pDessinIcone.y = 0;

        if (carte.getDegatsInfliges() != 0) {
            dessinerImage(g, "/src/ressources/icones/swords/swords_minus" + carte.getDegatsInfliges() + ".png", 30);
        }

        if (carte.getDegatsSubis() != 0) {
            dessinerImage(g, "/src/ressources/icones/heart_red/heart_red_minus" + carte.getDegatsSubis() + ".png", 30);
        }

        if (carte.getPopulariteGagnee() != 0) {
            dessinerImage(g, "/src/ressources/icones/star/star_plus" + carte.getPopulariteGagnee() + ".png", 30);
        }

        if (carte.getVieGagne() != 0) {
            dessinerImage(g, "/src/ressources/icones/heart_blue/heart_blue_plus" + carte.getVieGagne() + ".png", 30);
        }
    }

   @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Appliquer le clip arrondi pour que tout le contenu respecte cette forme
    Shape clip = new RoundRectangle2D.Float(10, 10, getWidth() -20, getHeight() -20, 20, 20);
    g2.setClip(clip);

    // Dessin du fond
    g2.setColor(Color.RED);
    g2.fillRect(0, 0, getWidth(), getHeight());

    if (image != null) {
        g2.drawImage(image, 5, 5, getWidth() -10, getHeight() -10, this);
    }

    

    // Retirer le clip pour dessiner les bords visibles
    g2.setClip(null);
    g2.setStroke(new BasicStroke(3));
    g2.setColor(Color.WHITE);
    g2.drawRoundRect(10, 10, getWidth() - 21, getHeight() - 21, 20, 20);

    g2.setStroke(new BasicStroke(1));
    g2.setColor(Color.LIGHT_GRAY);
    g2.drawRoundRect(13, 13, getWidth() - 27, getHeight() - 27, 16, 16);
    
    dessinerIcones(g2);
    
    g2.dispose();
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
