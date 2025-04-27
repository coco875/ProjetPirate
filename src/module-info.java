module ProjetPirate {
    // Exports - packages que d'autres modules peuvent utiliser
    exports test;
    exports ProjetPirate;
    exports boundary;
    exports controllers;
    exports joueur;
    exports jeu;
    exports carte;
    
    // Requires - modules dont ce module dépend
    requires java.desktop; // Contient Swing, AWT, etc.
    requires com.fasterxml.jackson.databind; // Pour le support JSON
    requires com.fasterxml.jackson.core; // Fonctionnalités de base JSON
    requires com.fasterxml.jackson.annotation; // Annotations pour JSON
}