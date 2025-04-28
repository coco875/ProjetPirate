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
}