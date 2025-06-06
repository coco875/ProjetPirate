/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ihm;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.JLayeredPane;
/**
 *
 * @author Fonteyne
 */
public class PanelPlateau extends javax.swing.JLayeredPane {

    private Image image;
    
    public PanelPlateau(){
        String local_path = System.getProperty("user.dir");
        File image_path = new File(local_path+"/src/ressources/image_plateau/plateau.jpg");
        try {
            image = ImageIO.read(image_path);
        } catch (IOException io) {
            System.out.println("Erreur lors du chargement de l'image " + image_path + ": " + io.getMessage());
        }
        setLayout(null);
    }
    
    public void addCarte(PanelCarte carte) {
        //On peut ajouter la carte en haut de l'arrière-plan pour qu'elle soit visible
        add(carte, JLayeredPane.POPUP_LAYER); 
        revalidate();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
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
