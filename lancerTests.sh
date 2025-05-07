#!/bin/bash

# Script pour compiler et lancer tous les tests du Jeu des Pirates

echo "Compilation et lancement des tests du Jeu des Pirates..."

# Créer les dossiers nécessaires
if [ ! -d "bin" ]; then
    echo "Création du dossier bin..."
    mkdir -p bin
else
    echo "Nettoyage du dossier bin..."
    rm -rf bin/*
fi

# Créer un dossier lib s'il n'existe pas
if [ ! -d "lib" ]; then
    echo "Création du dossier lib..."
    mkdir -p lib
fi

# Télécharger JUnit 5 si nécessaire
JUNIT_PLATFORM="lib/junit-platform-console-standalone-1.9.2.jar"

if [ ! -f "$JUNIT_PLATFORM" ]; then
    echo "Téléchargement de JUnit 5..."
    curl -L https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar -o "$JUNIT_PLATFORM"
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

# Compiler l'ensemble du code source y compris les tests
echo "Compilation du code source et des tests..."
javac -d bin -sourcepath src -cp "$JUNIT_PLATFORM" $(find src -name "*.java")

# Vérifier si la compilation a réussi
if [ $? -ne 0 ]; then
    echo "Erreur: La compilation a échoué."
    exit 1
fi

echo "Compilation terminée avec succès."
echo "Lancement des tests..."

# Exécuter les tests avec JUnit 5
java -jar "$JUNIT_PLATFORM" --class-path bin --scan-class-path

# Vérifier le code de retour
if [ $? -eq 0 ]; then
    echo "Tous les tests ont été exécutés avec succès."
else
    echo "Des erreurs ont été détectées lors de l'exécution des tests."
fi

exit 0