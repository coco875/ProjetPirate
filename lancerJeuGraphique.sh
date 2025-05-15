#!/bin/bash
# Script pour lancer la fenêtre FramePlateau

cd "$(dirname "$0")"

# Compilation si besoin
find src/ -name "*.java" | xargs javac -d bin -cp bin

# Exécution
java -cp bin ihm.FramePlateau