#!/bin/bash

# Script pour compiler et lancer les tests du Jeu des Pirates avec la couverture de code
# Ce script utilise JaCoCo pour générer un rapport de couverture de code

echo "Compilation et lancement des tests du Jeu des Pirates avec couverture de code..."

# Créer les dossiers nécessaires
if [ ! -d "bin" ]; then
    echo "Création du dossier bin..."
    mkdir -p bin
else
    echo "Nettoyage du dossier bin..."
    rm -rf bin/*
fi

# Créer un dossier pour les rapports de couverture
if [ ! -d "coverage" ]; then
    echo "Création du dossier coverage..."
    mkdir -p coverage
else
    echo "Nettoyage du dossier coverage..."
    rm -rf coverage/*
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

# Télécharger JaCoCo si nécessaire
JACOCO_AGENT="lib/org.jacoco.agent-0.8.10.jar"
JACOCO_CLI="lib/org.jacoco.cli-0.8.10.jar"
JACOCO_REPORT="lib/org.jacoco.report-0.8.10.jar"
JACOCO_CORE="lib/org.jacoco.core-0.8.10.jar"

if [ ! -f "$JACOCO_AGENT" ]; then
    echo "Téléchargement de JaCoCo..."
    curl -L https://repo1.maven.org/maven2/org/jacoco/org.jacoco.agent/0.8.10/org.jacoco.agent-0.8.10.jar -o "$JACOCO_AGENT"
    curl -L https://repo1.maven.org/maven2/org/jacoco/org.jacoco.cli/0.8.10/org.jacoco.cli-0.8.10.jar -o "$JACOCO_CLI"
    curl -L https://repo1.maven.org/maven2/org/jacoco/org.jacoco.report/0.8.10/org.jacoco.report-0.8.10.jar -o "$JACOCO_REPORT"
    curl -L https://repo1.maven.org/maven2/org/jacoco/org.jacoco.core/0.8.10/org.jacoco.core-0.8.10.jar -o "$JACOCO_CORE"
fi

# Extraire l'agent JaCoCo
echo "Extraction de l'agent JaCoCo..."
mkdir -p lib/jacoco
unzip -o -j "$JACOCO_AGENT" "*.jar" -d lib/jacoco 2>/dev/null
JACOCO_AGENT_JAR="lib/jacoco/jacocoagent.jar"

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
echo "Lancement des tests avec couverture..."

# Fichier des données de couverture
JACOCO_EXEC="coverage/jacoco.exec"

# Exécuter les tests avec JUnit 5 et l'agent JaCoCo
java -javaagent:"$JACOCO_AGENT_JAR"=destfile="$JACOCO_EXEC" -jar "$JUNIT_PLATFORM" --class-path bin --scan-class-path

# Vérifier le code de retour
if [ $? -eq 0 ]; then
    echo "Tous les tests ont été exécutés avec succès."
else
    echo "Des erreurs ont été détectées lors de l'exécution des tests."
    # Continuer quand même pour générer le rapport de couverture
fi

# Générer le rapport de couverture HTML
echo "Génération du rapport de couverture..."

# Utiliser Java avec les classes JaCoCo directement au lieu de la CLI
java -cp "$JACOCO_CORE:$JACOCO_REPORT:lib/*" org.jacoco.cli.internal.Main report "$JACOCO_EXEC" \
  --classfiles bin \
  --sourcefiles src \
  --html coverage/html \
  --name "Rapport de couverture JeuPirate"

# Si la méthode ci-dessus échoue, essayer une alternative
if [ ! -d "coverage/html" ]; then
    echo "Utilisation d'une méthode alternative pour générer le rapport..."
    mkdir -p coverage/html
    java -cp "$JACOCO_CORE:$JACOCO_REPORT:$JACOCO_AGENT:$JUNIT_PLATFORM" \
      org.jacoco.report.JavaNames \
      --encoding UTF-8 \
      --destdir coverage/html \
      --title "Rapport de couverture JeuPirate" \
      --sourcefiles src \
      --classfiles bin \
      coverage/jacoco.exec
fi

# Vérifier si le rapport a été généré
if [ -f "coverage/html/index.html" ]; then
    echo "Rapport de couverture généré avec succès dans le dossier 'coverage/html'"
    echo "Vous pouvez ouvrir le fichier 'coverage/html/index.html' dans un navigateur pour consulter le rapport."
else
    echo "Erreur: Échec de la génération du rapport de couverture."
    echo "Le fichier de données jacoco.exec a été créé, mais la conversion en HTML a échoué."
    
    # Dernière tentative avec une autre approche
    echo "Tentative avec une autre approche..."
    
    # Télécharger l'outil autonome si nécessaire
    if [ ! -f "lib/jacococli.jar" ]; then
        echo "Téléchargement de l'outil JaCoCo CLI autonome..."
        curl -L https://repo1.maven.org/maven2/org/jacoco/org.jacoco.cli/0.8.10/org.jacoco.cli-0.8.10-nodeps.jar -o "lib/jacococli.jar"
    fi
    
    java -jar lib/jacococli.jar report coverage/jacoco.exec \
      --classfiles bin \
      --sourcefiles src \
      --html coverage/html
    
    if [ -f "coverage/html/index.html" ]; then
        echo "Rapport de couverture généré avec succès dans le dossier 'coverage/html'"
    else
        echo "Échec complet. Essayez d'installer JaCoCo via Maven ou d'utiliser un IDE comme IntelliJ IDEA ou Eclipse pour générer le rapport."
    fi
fi

exit 0