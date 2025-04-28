#!/bin/bash

# Script pour lancer tous les tests du Jeu des Pirates

echo "Lancement des tests du Jeu des Pirates..."

# Vérifier si le dossier bin existe
if [ ! -d "bin" ]; then
    echo "Erreur: Le dossier 'bin' n'existe pas. Veuillez compiler le projet d'abord."
    exit 1
fi

# Lancer les tests en utilisant la méthode classique (sans modules)
cd bin
java test.RunAllTests

# Vérifier le code de retour
if [ $? -eq 0 ]; then
    echo "Tous les tests ont été exécutés avec succès."
else
    echo "Des erreurs ont été détectées lors de l'exécution des tests."
fi

# Retourner au répertoire original
cd ..

exit 0