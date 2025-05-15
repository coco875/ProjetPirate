

package ihm;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import carte.*;

public class PanelCarte extends JPanel {
    private boolean etaitDansZoneStrategique = false;
    private boolean etaitDansZoneOffensive = false;
    
    private Image image;
    private Point pointDessin = new Point(0, 0);
    private Carte carte;

    private int xSouris, ySouris;
    private JPanel zoneStrategique, zoneOffensive;
    private JLayeredPane zoneJeu;
    private JPanel zoneMain;
    private Point decalageSouris;
    private boolean enDeplacement = false;

    private Rectangle rectangleStrategique;
    private Rectangle rectangleOffensive;
    
    private boolean bloque;

    public PanelCarte(Carte carte, JPanel zoneStrategique, JPanel zoneOffensive, JLayeredPane zoneJeu, JPanel zoneMain, int numeroJoueur) {
        initComponents();
        this.carte = carte;
        this.zoneStrategique = zoneStrategique;
        this.zoneOffensive = zoneOffensive;
        this.zoneJeu = zoneJeu;
        this.zoneMain = zoneMain;

        try {
            Point posStrategique = zoneStrategique.getLocationOnScreen();
            Point posOffensive = zoneOffensive.getLocationOnScreen();

            rectangleStrategique = new Rectangle(posStrategique.x, posStrategique.y, zoneStrategique.getWidth(), zoneStrategique.getHeight());
            rectangleOffensive = new Rectangle(posOffensive.x, posOffensive.y, zoneOffensive.getWidth(), zoneOffensive.getHeight());
        } catch (IllegalComponentStateException e) {}

        try {
            String cheminImage = System.getProperty("user.dir") + "/" + carte.getCheminImage();
            image = ImageIO.read(new File(cheminImage));
        } catch (IOException e) {}

        setOpaque(false);
        setPreferredSize(new Dimension(149, 190));
        setVisible(true);
        setToolTipText("<html>" + carte.toString().replace("\n", "<br>") + "</html>");
        repaint();

        ajouterListeners();
    }

    public Carte getCarte() {
        return this.carte;
    }

    private void ajouterListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(bloque) return;
                xSouris = e.getX();
                ySouris = e.getY();
                decalageSouris = new Point(xSouris, ySouris);
                if (getParent() instanceof JLayeredPane) {
                    ((JLayeredPane) getParent()).moveToFront(PanelCarte.this);
                }
                Point posZoneStrategique = zoneStrategique.getLocationOnScreen();
                Dimension dimZoneStrategique = ((PanelZoneCarte) zoneStrategique).getRectangleZone();
                rectangleStrategique = new Rectangle(posZoneStrategique.x + zoneStrategique.getWidth() - dimZoneStrategique.width, posZoneStrategique.y, dimZoneStrategique.width, dimZoneStrategique.height);
                Point posZoneOffensive = zoneOffensive.getLocationOnScreen();
                Dimension dimZoneOffensive = ((PanelZoneCarte) zoneOffensive).getRectangleZone();
                rectangleOffensive = new Rectangle(posZoneOffensive.x, posZoneOffensive.y, dimZoneOffensive.width, dimZoneOffensive.height);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!enDeplacement || bloque) return;

                enDeplacement = false;

                Point positionCarte = getLocationOnScreen();
                Point centreCarte = new Point(positionCarte.x + getWidth() / 2, positionCarte.y + getHeight() / 2);

                boolean dansZoneStrategique = rectangleStrategique.contains(centreCarte);
                boolean dansZoneOffensive = rectangleOffensive.contains(centreCarte);

                boolean carteStrategique = carte.getType() == TypeCarte.STRATEGIQUE;
                boolean carteOffensive = carte.getType() == TypeCarte.OFFENSIVE;

             
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(PanelCarte.this);
                    parent.revalidate();
                    parent.repaint();
                }

              
                if (dansZoneStrategique && carteStrategique) {
                    setBounds(0, 0, getWidth(), getHeight());
                    setBloque(true);
                    zoneStrategique.add(PanelCarte.this);
                    PanelZoneCarte zoneCarte = (PanelZoneCarte) zoneStrategique;
                    zoneCarte .setGrise(false);
                    zoneStrategique.revalidate();
                    zoneStrategique.repaint();
                } else if (dansZoneOffensive && carteOffensive) {
                    setBounds(0, 0, getWidth(), getHeight());
                    setBloque(true);
                    zoneOffensive.add(PanelCarte.this);
                    PanelZoneCarte zoneCarte = (PanelZoneCarte) zoneOffensive;
                    zoneCarte .setGrise(false);
                    zoneOffensive.revalidate();
                    zoneOffensive.repaint();
                } else {
                    setBounds(0, 0, getWidth(), getHeight());
                    zoneMain.add(PanelCarte.this);
                    zoneMain.revalidate();
                    zoneMain.repaint();
                }

                
                zoneJeu.revalidate();
                zoneJeu.repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(bloque) return;
                enDeplacement = true;

                Component racine = SwingUtilities.getRoot(PanelCarte.this);
                if (!(racine instanceof Container)) return;

                if (getParent() == zoneMain) {
                    int dx = e.getX() - xSouris;
                    int dy = e.getY() - ySouris;
                    Point nouvellePosition = new Point(getLocation().x + dx, getLocation().y + dy);
                    Point ecran = new Point(nouvellePosition);
                    SwingUtilities.convertPointToScreen(ecran, zoneMain);
                    SwingUtilities.convertPointFromScreen(ecran, zoneJeu);

                    zoneMain.remove(PanelCarte.this);
                    setBounds(ecran.x, ecran.y, getWidth(), getHeight());
                    zoneJeu.add(PanelCarte.this, JLayeredPane.DRAG_LAYER);
                    zoneJeu.moveToFront(PanelCarte.this);
                    zoneJeu.revalidate();
                    zoneJeu.repaint();
                }

                if (getParent() == zoneJeu) {
                    int dx = e.getX() - xSouris;
                    int dy = e.getY() - ySouris;
                    setLocation(getX() + dx, getY() + dy);
                    zoneJeu.repaint();
                }

                Point pos = getLocationOnScreen();
                Point centre = new Point(pos.x + getWidth() / 2, pos.y + getHeight() / 2);

                boolean dansZoneStrat = rectangleStrategique.contains(centre);
                boolean dansZoneOff = rectangleOffensive.contains(centre);

                if (carte.getType() == TypeCarte.STRATEGIQUE) {
                    if (dansZoneStrat && !etaitDansZoneStrategique) {
                        if (zoneStrategique instanceof PanelZoneCarte) {
                            ((PanelZoneCarte) zoneStrategique).setGrise(true);
                        }
                        etaitDansZoneStrategique = true;
                    } else if (!dansZoneStrat && etaitDansZoneStrategique) {
                        if (zoneStrategique instanceof PanelZoneCarte) {
                            ((PanelZoneCarte) zoneStrategique).setGrise(false);
                        }
                        etaitDansZoneStrategique = false;
                    }
                } else if (carte.getType() == TypeCarte.OFFENSIVE) {
                    if (dansZoneOff && !etaitDansZoneOffensive) {
                        if (zoneOffensive instanceof PanelZoneCarte) {
                            ((PanelZoneCarte) zoneOffensive).setGrise(true);
                        }
                        etaitDansZoneOffensive = true;
                    } else if (!dansZoneOff && etaitDansZoneOffensive) {
                        if (zoneOffensive instanceof PanelZoneCarte) {
                            ((PanelZoneCarte) zoneOffensive).setGrise(false);
                        }
                        etaitDansZoneOffensive = false;
                    }
                }
            }
        });
    }
    public void setBloque(Boolean bloque){
        this.bloque = bloque;
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
        g2.setColor(Color.WHITE);
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
    
