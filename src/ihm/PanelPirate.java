
package ihm;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import joueur.Pirate;
/**
 *
 * @author Fonteyne
 */
public class PanelPirate extends JPanel {
    // === PARTIE PRÉSENTATION ===
    private Image image;
    private boolean estClique = false;
    private final String nomPirate;
    private final Pirate pirate;

    // === PARTIE DIALOGUE ===
    private final boolean isLeftGroup;
    private static PanelPirate selectedLeft = null;
    private static PanelPirate selectedRight = null;
    private static final List<Consumer<Boolean>> listeners = new ArrayList<>();
    private static Consumer<Boolean> callback = null; 
    private static JLabel leftDescriptionLabel;
    private static JLabel rightDescriptionLabel;

    public PanelPirate(Pirate pirate, boolean isLeftGroup) {
        this.pirate = pirate;
        this.nomPirate = pirate.getNom();
        this.isLeftGroup = isLeftGroup;

       
        chargerImage();
        setOpaque(false);

        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEnteredActionPerformed(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExitedActionPerformed(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                gererClic();
            }
        });
    }

    private void mouseEnteredActionPerformed(MouseEvent e) {
        if (!estClique) {
            setBorder(new LineBorder(Color.ORANGE, 10));
            repaint();
        }
    }

    private void mouseExitedActionPerformed(MouseEvent e) {
        if (!estClique) {
            setBorder(null);
            repaint();
        }
    }

    // === MÉTHODES DE PRÉSENTATION ===
    private void chargerImage() {
        try {
            String path = System.getProperty("user.dir") + "/" + pirate.getCheminImage();
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Erreur chargement image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void afficherSelection() {
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
        estClique = true;
        repaint();
    }

    private void reinitialiserSelection() {
        setBorder(null);
        estClique = false;
        repaint();
    }

    // === MÉTHODES DE DIALOGUE ===
    private void gererClic() {
        //Gestion de la sélection
        if (isLeftGroup) {
            if (selectedLeft != null) {
                selectedLeft.reinitialiserSelection();
            }
            selectedLeft = this;
        } else {
            if (selectedRight != null) {
                selectedRight.reinitialiserSelection();
            }
            selectedRight = this;
        }

        afficherSelection();
        mettreAJourDescription();
        notifierSelection();
    }

    private static void mettreAJourDescription() {
        SwingUtilities.invokeLater(() -> {
            if (selectedLeft != null && leftDescriptionLabel != null) {
                leftDescriptionLabel.setText(formatHtml(selectedLeft.getDescription()));
            }
            if (selectedRight != null && rightDescriptionLabel != null) {
                rightDescriptionLabel.setText(formatHtml(selectedRight.getDescription()));
            }
        });
    }

    private static String formatHtml(String text) {
        return "<html>" + text.replace("\r\n", "<br>").replace("\n", "<br>") + "</html>";
    }

    private static void notifierSelection() {
        boolean deuxSelectionnes = (selectedLeft != null && selectedRight != null);
        for (Consumer<Boolean> listener : listeners) {
            listener.accept(deuxSelectionnes);
        }
        if (callback != null) {
            callback.accept(deuxSelectionnes);
        }
    }

    // === MÉTHODES PUBLIQUES ===
    public static void setupLabelsDescription(JLabel leftLabel, JLabel rightLabel) {
        leftDescriptionLabel = leftLabel;
        rightDescriptionLabel = rightLabel;
    }

    public Pirate getPirate() {
        return pirate;
    }

    public String getDescription() {
        return pirate.getNom() + " - " + pirate.getDescription();
    }

    public static PanelPirate getSelectedLeft() {
        return selectedLeft;
    }

    public static PanelPirate getSelectedRight() {
        return selectedRight;
    }

    public static void setSelectionCallback(Consumer<Boolean> callback) {
        PanelPirate.callback = callback; 
    }

    public static void addSelectionListener(Consumer<Boolean> listener) {
        listeners.add(listener);
    }

    public void setEstClique(boolean etat) {
        this.estClique = etat;
    }

    public boolean estClique() {
        return estClique;
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
