#!/bin/bash

# Script pour compiler et lancer tous les tests du Jeu des Pirates avec Maven

echo "Compilation et lancement des tests du Jeu des Pirates..."

# Vérifier si Maven est installé
if ! command -v mvn &> /dev/null; then
    echo "Erreur: Maven n'est pas installé ou n'est pas dans le PATH."
    echo "Veuillez installer Maven avant de continuer."
    exit 1
fi

# Vérifier et traiter les caractères BOM dans les fichiers Java
echo "Vérification des caractères BOM dans les fichiers Java..."
for file in $(find src -name "*.java"); do
    # Vérifier si le fichier contient un BOM UTF-8
    if [[ $(hexdump -n 3 -e '"%X"' "$file") == "EFBBBF" ]]; then
        echo "Correction du BOM dans le fichier $file"
        # Créer un fichier temporaire sans le BOM
        tail -c +4 "$file" > "$file.tmp"
        # Remplacer le fichier original par la version sans BOM
        mv "$file.tmp" "$file"
    fi
done

echo "Exécution des tests avec Maven..."

# Lancer les tests via Maven
mvn clean test

# Vérifier le code de retour
if [ $? -eq 0 ]; then
    echo "Tous les tests ont été exécutés avec succès."
else
    echo "Des erreurs ont été détectées lors de l'exécution des tests."
fi

exit 0