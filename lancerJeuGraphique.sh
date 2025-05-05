#!/bin/bash

# Script pour compiler et lancer l'interface graphique du Jeu des Pirates avec Maven

echo "Compilation et lancement de l'interface graphique du Jeu des Pirates..."

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

# Compiler le code avec Maven
echo "Compilation du code source avec Maven..."
mvn clean compile

# Vérifier si la compilation a réussi
if [ $? -ne 0 ]; then
    echo "Erreur: La compilation a échoué."
    exit 1
fi

echo "Compilation terminée avec succès."
echo "Lancement de l'interface graphique..."

# Lancer l'application via Maven
mvn exec:java@gui

exit 0