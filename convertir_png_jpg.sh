#!/bin/bash

# Script pour convertir toutes les images PNG en JPG dans le projet

# Vérifier si ImageMagick est installé
if ! command -v convert &> /dev/null; then
    echo "ImageMagick n'est pas installé. Installation en cours..."
    sudo apt-get update
    sudo apt-get install -y imagemagick
fi

# Rechercher tous les fichiers PNG dans le projet
echo "Recherche des fichiers PNG dans le projet..."

find_result=$(find . -type f -name "*.png")

# Vérifier si des fichiers PNG ont été trouvés
if [ -z "$find_result" ]; then
    echo "Aucun fichier PNG trouvé dans le projet."
    exit 0
fi

# Compter le nombre de fichiers à convertir
nb_fichiers=$(echo "$find_result" | wc -l)
echo "Nombre de fichiers PNG trouvés : $nb_fichiers"

# Créer un dossier de sauvegarde pour les fichiers originaux
backup_dir="./png_backup_$(date +%Y%m%d_%H%M%S)"
mkdir -p "$backup_dir"
echo "Sauvegarde des fichiers PNG originaux dans $backup_dir"

# Convertir chaque fichier PNG en JPG
echo "Conversion des fichiers PNG en JPG..."
counter=0

echo "$find_result" | while read fichier; do
    # Créer le dossier de sauvegarde correspondant
    backup_subdir=$(dirname "$backup_dir/$fichier")
    mkdir -p "$backup_subdir"
    
    # Sauvegarder le fichier original
    cp "$fichier" "$backup_dir/$fichier"
    
    # Créer le nom du fichier JPG
    fichier_jpg="${fichier%.png}.jpg"
    
    # Convertir le fichier
    convert "$fichier" "$fichier_jpg"
    
    # Supprimer le fichier PNG original si la conversion a réussi
    if [ -f "$fichier_jpg" ]; then
        rm "$fichier"
        echo "Converti : $fichier → $fichier_jpg"
        ((counter++))
    else
        echo "ERREUR lors de la conversion de $fichier"
    fi
done

echo "Conversion terminée : $counter fichiers ont été convertis avec succès."
echo "Les fichiers PNG originaux ont été sauvegardés dans $backup_dir"