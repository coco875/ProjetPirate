#!/bin/bash

# Script pour compiler et lancer l'interface console du Jeu des Pirates

echo "Compilation et lancement de l'interface console du Jeu des Pirates..."

# Nettoyer le dossier bin s'il existe
if [ -d "bin" ]; then
    echo "Nettoyage du dossier bin..."
    rm -rf bin/*
else
    echo "Création du dossier bin..."
    mkdir -p bin
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

# Compiler le code source
echo "Compilation du code source..."
javac -d bin -sourcepath src src/ProjetPirate/Main.java src/ProjetPirate/MainUI.java

# Vérifier si la compilation a réussi
if [ $? -ne 0 ]; then
    echo "Erreur: La compilation a échoué."
    exit 1
fi

echo "Compilation terminée avec succès."
echo "Lancement de l'interface console..."

# Vérifier si le dossier lib existe pour les dépendances JSON
if [ -d "lib" ]; then
    # Lancer avec les dépendances JSON
    java -XX:+ShowCodeDetailsInExceptionMessages -cp bin:lib ProjetPirate.MainUI console
else
    # Lancer sans les dépendances JSON (fonctionnalités de sauvegarde désactivées)
    echo "Note: Dossier 'lib' non trouvé. Les fonctionnalités de sauvegarde/chargement seront désactivées."
    java -XX:+ShowCodeDetailsInExceptionMessages -cp bin ProjetPirate.MainUI console
fi

exit 0