/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author RCJ4464A
 */
import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel() {
        // Remplace le chemin par le bon emplacement de ton image
        image = new ImageIcon(getClass().getResource("/images/981.jpg")).getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
