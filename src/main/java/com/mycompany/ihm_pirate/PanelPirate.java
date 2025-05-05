package com.mycompany.ihm_pirate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.border.LineBorder;
import java.util.*;
import java.util.function.*;

/**
 *
 * @author FNX4294A
 */
public class PanelPirate extends javax.swing.JPanel {
    Image image;
    boolean estClique = false;
    private static PanelPirate selectedLeft = null;
    private static PanelPirate selectedRight = null;
    private static final List<Consumer<Boolean>> listeners = new ArrayList<>();
    private final boolean isLeftGroup;
    private String nomPirate;
    private static final List<Consumer<Boolean>> callbacks = new ArrayList<>();
    private static JLabel leftDescriptionLabel;
    private static JLabel rightDescriptionLabel;
    
    public PanelPirate(String pirateName, boolean isLeftGroup) {
        this.nomPirate = pirateName;
        this.isLeftGroup = isLeftGroup;
        String local_path = System.getProperty("user.dir");
        File image_path = new File(local_path+"/images/"+pirateName+".jpg");
        try {
            image = ImageIO.read(image_path);
        } catch (IOException io) {
            System.out.println("Error");
        }
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!estClique){
                    setBorder(new LineBorder(Color.ORANGE, 10));
                }
                 
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if(!estClique){
                    setBorder(null); 
                }
                repaint();
            }
            
            @Override
            
            public void mouseClicked(MouseEvent e){
                if (isLeftGroup) {
                    if (selectedLeft != null) {
                        selectedLeft.resetSelection();
                    }
                    selectedLeft = PanelPirate.this;
                } else {
                    if (selectedRight != null) {
                        selectedRight.resetSelection();
                    }
                    selectedRight = PanelPirate.this;
                }

                setSelection();
                updateDescriptionLabels();
                notifySelectionState();
                
            }
            
            
        });
    }
    
    public static void setupLabelsDescription(JLabel leftLabel, JLabel rightLabel) {
        leftDescriptionLabel = leftLabel;
        rightDescriptionLabel = rightLabel;
    }

    private static void updateDescriptionLabels() {
        SwingUtilities.invokeLater(() -> {
            if (selectedLeft != null && leftDescriptionLabel != null) {
                leftDescriptionLabel.setText(selectedLeft.getDescription());
            }
            if (selectedRight != null && rightDescriptionLabel != null) {
                rightDescriptionLabel.setText(selectedRight.getDescription());
            }
        });
    }
    
    private void resetSelection() {
        setBorder(null);
        estClique = false;
    }
    private void setSelection() {
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
        estClique = true;
    }
    
    
    public static void notifySelectionState() {
        boolean bothSelected = (selectedLeft != null && selectedRight != null);
        
        
        for (Consumer<Boolean> callback : callbacks) {
            try {
                callback.accept(bothSelected);
            } catch (Exception e) {
                System.err.println("Erreur dans callback: " + e.getMessage());
            }
        }
    }
    
    public static synchronized void setSelectionCallback(Consumer<Boolean> callback) {
        callbacks.clear();
        callbacks.add(callback);
    }
    
    public static void addSelectionListener(Consumer<Boolean> listener) {
        listeners.add(listener);
    }
    
    public void setEstClique(boolean etat) {
        this.estClique = etat;
    }
    public void setClique(){
        estClique = true;
    }
    public boolean estClique(){
        return estClique;
    }
    
    public String getDescription() {
        switch(this.nomPirate) { 
            case "jack":
                return "Jack Sparrow - Capitaine excentrique";
            case "davy":
                return "Davy Jones - Maître des profondeurs";
            case "pirate3":
                return "Barbe Noire - Terrible et impitoyable";
            case "pirate4":
                return "Capitaine Hook - Rusé et déterminé";
            default:
                return "Pirate inconnu";
        }
    }
    public static PanelPirate getSelectedLeft() {
        return selectedLeft;
    }

    public static PanelPirate getSelectedRight() {
        return selectedRight;
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
