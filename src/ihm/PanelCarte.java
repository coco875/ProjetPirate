package ihm;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import carte.*;
/**
 *
 * @author Fonteyne
 */
public class PanelCarte extends JPanel {
    // === MODÈLE (Données) ===
    private final Carte carte;
    private boolean bloque = false;

    // === PRÉSENTATION (UI) ===
    private Image image;
    private final Point pointDessin = new Point(0, 0);
    private boolean etaitDansZoneStrategique = false;
    private boolean etaitDansZoneOffensive = false;

    // === DIALOGUE (Contrôle) ===
    private final JPanel zoneStrategique, zoneOffensive, zoneMain;
    private final JLayeredPane zoneJeu;
    private Point decalageSouris;
    private boolean enDeplacement = false;
    private Rectangle rectangleStrategique, rectangleOffensive;

    public PanelCarte(Carte carte, JPanel zoneStrategique, JPanel zoneOffensive, JLayeredPane zoneJeu, JPanel zoneMain, int numeroJoueur) {
        this.carte = carte;
        this.zoneStrategique = zoneStrategique;
        this.zoneOffensive = zoneOffensive;
        this.zoneJeu = zoneJeu;
        this.zoneMain = zoneMain;

        initUI();          //Initialisation de la présentation
        chargerImage();    //Chargement des ressources visuelles
        initZones();       //Configuration des zones interactives
        initInteractions(); //Configuration des gestionnaires d'événements
    }

    // === PRÉSENTATION ===
    private void initUI() {
        setOpaque(false);
        setPreferredSize(new Dimension(149, 190));
        setToolTipText("<html>" + carte.toString().replace("\n", "<br>") + "</html>");
    }

    private void chargerImage() {
        try {
            String cheminImage = System.getProperty("user.dir") + "/" + carte.getCheminImage();
            image = ImageIO.read(new File(cheminImage));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Float(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
        g2.setClip(clip);
        g2.setColor(Color.RED);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (image != null) {
            g2.drawImage(image, 5, 5, getWidth() - 10, getHeight() - 10, this);
        }

        g2.setClip(null);
        g2.setStroke(new BasicStroke(3));
        if(carte.getVieGagne() >= 3 || carte.getDegatsInfliges() >= 3 || carte.getPopulariteGagnee() >= 3 || carte.getOrGagne() >= 10){
            g2.setColor(new Color(255, 215, 0)); 
        }else{
            g2.setColor(Color.WHITE); 
        }
        g2.drawRoundRect(10, 10, getWidth() - 21, getHeight() - 21, 20, 20);

        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRoundRect(13, 13, getWidth() - 27, getHeight() - 27, 16, 16);

        dessinerIcones(g2);
        g2.dispose();
    }

    private void dessinerIcones(Graphics g) {
        pointDessin.x = 0;
        pointDessin.y = 0;

        if (carte.getDegatsInfliges() != 0) {
            dessinerImage(g, "/src/ressources/icones/swords/swords_minus" + carte.getDegatsInfliges() + ".png", 40);
        }
        if (carte.getDegatsSubis() != 0) {
            dessinerImage(g, "/src/ressources/icones/heart_red/heart_red_minus" + carte.getDegatsSubis() + ".png", 40);
        }
        if (carte.getPopulariteGagnee() != 0) {
            dessinerImage(g, "/src/ressources/icones/star/star_plus" + carte.getPopulariteGagnee() + ".png", 40);
        }
        if (carte.getVieGagne() != 0) {
            dessinerImage(g, "/src/ressources/icones/heart_blue/heart_blue_plus" + carte.getVieGagne() + ".png", 40);
        }
        if( carte.getOrGagne() != 0){
            dessinerImage(g, "/src/ressources/icones/or/or_plus_" + carte.getOrGagne() + ".png", 40);
        }
    }

    private void dessinerImage(Graphics g, String chemin, int taille) {
        try {
            File fichier = new File(System.getProperty("user.dir") + chemin);
            Image img = ImageIO.read(fichier);
            g.drawImage(img, pointDessin.x, pointDessin.y, taille, taille, this);
            pointDessin.x += taille + 5;
        } catch (IOException e) {}
    }

    // === DIALOGUE ===
    private void initZones() {
        try {
            Point posStrategique = zoneStrategique.getLocationOnScreen();
            Point posOffensive = zoneOffensive.getLocationOnScreen();
            rectangleStrategique = new Rectangle(posStrategique.x, posStrategique.y, zoneStrategique.getWidth(), zoneStrategique.getHeight());
            rectangleOffensive = new Rectangle(posOffensive.x, posOffensive.y, zoneOffensive.getWidth(), zoneOffensive.getHeight());
        } catch (IllegalComponentStateException ignored) {}
    }

    private void initInteractions() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressedActionPerformed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleasedActionPerformed(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDraggedActionPerformed(e);
            }
        });
    }
    
    public void mousePressedActionPerformed(MouseEvent e){
        if (bloque) return;
        decalageSouris = new Point(e.getX(), e.getY());
        if (getParent() instanceof JLayeredPane) {
            ((JLayeredPane) getParent()).moveToFront(PanelCarte.this);
        }
        recalculerZones();
    }
    
    public void mouseReleasedActionPerformed(MouseEvent e){
        if (bloque || !enDeplacement) return;
        enDeplacement = false;
        gererDepotCarte();
    }
    
    public void mouseDraggedActionPerformed(MouseEvent e){
        if (bloque) return;
        deplacerCarte(e);
        verifierZones();
    }

    private void recalculerZones() {
        Point posZoneStrategique = zoneStrategique.getLocationOnScreen();
        Dimension dimZoneStrategique = ((PanelZoneCarte) zoneStrategique).getRectangleZone();
        rectangleStrategique = new Rectangle(
            posZoneStrategique.x + zoneStrategique.getWidth() - dimZoneStrategique.width, 
            posZoneStrategique.y, 
            dimZoneStrategique.width, 
            dimZoneStrategique.height
        );

        Point posZoneOffensive = zoneOffensive.getLocationOnScreen();
        Dimension dimZoneOffensive = ((PanelZoneCarte) zoneOffensive).getRectangleZone();
        rectangleOffensive = new Rectangle(
            posZoneOffensive.x, 
            posZoneOffensive.y, 
            dimZoneOffensive.width, 
            dimZoneOffensive.height
        );
    }

    private void deplacerCarte(MouseEvent e) {
        if (!enDeplacement && getParent() == zoneMain) {
            demarrerDeplacement();
        }

        if (getParent() == zoneJeu) {
            setLocation(getX() + e.getX() - decalageSouris.x, getY() + e.getY() - decalageSouris.y);
        }
    }

    private void demarrerDeplacement() {
        Point ecran = getLocation();
        SwingUtilities.convertPointToScreen(ecran, zoneMain);
        SwingUtilities.convertPointFromScreen(ecran, zoneJeu);

        zoneMain.remove(this);
        setBounds(ecran.x, ecran.y, getWidth(), getHeight());
        zoneJeu.add(this, JLayeredPane.DRAG_LAYER);
        zoneJeu.moveToFront(this);
        enDeplacement = true;
    }

    private void verifierZones() {
        Point pos = getLocationOnScreen();
        Point centre = new Point(pos.x + getWidth() / 2, pos.y + getHeight() / 2);

        boolean dansZoneStrat = rectangleStrategique.contains(centre);
        boolean dansZoneOff = rectangleOffensive.contains(centre);

        if (carte.getType() == TypeCarte.STRATEGIQUE) {
            if (dansZoneStrat != etaitDansZoneStrategique) {
                ((PanelZoneCarte) zoneStrategique).setGrise(dansZoneStrat);
                etaitDansZoneStrategique = dansZoneStrat;
            }
        } else if (carte.getType() == TypeCarte.OFFENSIVE) {
            if (dansZoneOff != etaitDansZoneOffensive) {
                ((PanelZoneCarte) zoneOffensive).setGrise(dansZoneOff);
                etaitDansZoneOffensive = dansZoneOff;
            }
        }
    }

    private void gererDepotCarte() {
        Point positionCarte = getLocationOnScreen();
        Point centreCarte = new Point(positionCarte.x + getWidth() / 2, positionCarte.y + getHeight() / 2);

        boolean dansZoneStrategique = rectangleStrategique.contains(centreCarte);
        boolean dansZoneOffensive = rectangleOffensive.contains(centreCarte);

        Container parent = getParent();
        if (parent != null) parent.remove(this);

        if (dansZoneStrategique && carte.getType() == TypeCarte.STRATEGIQUE) {
            placerDansZone(zoneStrategique);
        } else if (dansZoneOffensive && carte.getType() == TypeCarte.OFFENSIVE) {
            placerDansZone(zoneOffensive);
        } else {
            placerDansZone(zoneMain);
        }

        zoneJeu.revalidate();
        zoneJeu.repaint();
    }

    private void placerDansZone(JPanel zone) {
        setBounds(0, 0, getWidth(), getHeight());
        zone.add(this);
        if (zone instanceof PanelZoneCarte) ((PanelZoneCarte) zone).setGrise(false);
        zone.revalidate();
        zone.repaint();
        if (zone != zoneMain) setBloque(true);
    }

    // === GETTERS/SETTERS ===
    public Carte getCarte() {
        return carte;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
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
    
