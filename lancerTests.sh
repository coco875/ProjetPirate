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

# Télécharger JUnit si nécessaire
JUNIT_JAR="lib/junit-4.13.2.jar"
HAMCREST_JAR="lib/hamcrest-core-1.3.jar"

if [ ! -f "$JUNIT_JAR" ]; then
    echo "Téléchargement de JUnit..."
    curl -L https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar -o "$JUNIT_JAR"
fi

if [ ! -f "$HAMCREST_JAR" ]; then
    echo "Téléchargement de Hamcrest (dépendance de JUnit)..."
    curl -L https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar -o "$HAMCREST_JAR"
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
javac -d bin -sourcepath src -cp "$JUNIT_JAR:$HAMCREST_JAR" $(find src -name "*.java")

# Vérifier si la compilation a réussi
if [ $? -ne 0 ]; then
    echo "Erreur: La compilation a échoué."
    exit 1
fi

echo "Compilation terminée avec succès."
echo "Lancement des tests..."

# Exécuter les tests
java -cp bin:"$JUNIT_JAR":"$HAMCREST_JAR" test.RunAllTests

# Vérifier le code de retour
if [ $? -eq 0 ]; then
    echo "Tous les tests ont été exécutés avec succès."
else
    echo "Des erreurs ont été détectées lors de l'exécution des tests."
fi

exit 0