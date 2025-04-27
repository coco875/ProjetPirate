#!/bin/bash

# Script pour lancer l'interface graphique du Jeu des Pirates

echo "Lancement de l'interface graphique du Jeu des Pirates..."

# Vérifier si le dossier bin existe
if [ ! -d "bin" ]; then
    echo "Erreur: Le dossier 'bin' n'existe pas. Veuillez compiler le projet d'abord."
    exit 1
fi

# Vérifier si le dossier lib existe pour les dépendances JSON
if [ -d "lib" ]; then
    # Lancer avec les dépendances JSON
    java -XX:+ShowCodeDetailsInExceptionMessages --module-path bin:lib -m ProjetPirate/ProjetPirate.MainUI gui
else
    # Lancer sans les dépendances JSON (fonctionnalités de sauvegarde désactivées)
    echo "Note: Dossier 'lib' non trouvé. Les fonctionnalités de sauvegarde/chargement seront désactivées."
    java -XX:+ShowCodeDetailsInExceptionMessages --module-path bin -m ProjetPirate/ProjetPirate.MainUI gui
fi

exit 0