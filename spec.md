# Gameplay
Le Jeu des Pirates est un jeu de cartes dans lequel deux joueurs s’affrontent.
Chaque joueur pioche quatre cartes. À tour de rôle, un pirate pioche une nouvelle carte et l’ajoute à sa main. Il doit ensuite décider s’il attaque son adversaire afin qu’il perde des points de vie ou s’il s’attribue des points de popularité.
Le but du jeu est d’atteindre cinq points de popularité ou simplement de survivre plus longtemps que son adversaire !

## Contraintes
* Chaque joueur dispose de points de vie (initialisé à 5 et pouvant tomber à 0) et de points de popularité (initialisé à 0 et pouvant atteindre 5).
* Une fois son tour terminé il doit obligatoirement avoir 4 cartes en main.

## Nouvelle fonctionnalité
* Ajout de l'or
* Ajout d'un marché
* Choix des capitaines
* Ajout de carte spéciale pour le capitaine

## Implementation
* Les cartes sont décrites via un JSON.

# Interface
## Contraintes
* Les cartes du joueur doivent être visibles.

# Projet Informatique
Ce projet consiste à développer un jeu de cartes en Java mettant en scène des pirates. L’objectif est d’appliquer les concepts appris en Java, notamment :
* Les tests unitaires.
* L’IHM (avec Java Swing).
* La méthode ECB.
* Les diagrammes UML (séquence, robustesse, classe, etc.).

## Règles du jeu
* Deux joueurs jouent à tour de rôle.
* Le but est soit de réduire les points de vie de l’adversaire à 0, soit d’atteindre 5 points de popularité.
* Les cartes sont de deux types :
  * **Cartes Popularité** : augmentent la popularité du joueur mais ne peuvent pas attaquer l’adversaire.
  * **Cartes Attaque** : permettent d’attaquer l’adversaire mais ne peuvent pas augmenter la popularité.
* Les cartes sont jouées sur deux zones différentes.
* Les joueurs peuvent récupérer des pièces d’or, qui peuvent être utilisées dans un marché apparaissant de temps en temps.
* À chaque tour, un joueur pioche une carte et peut utiliser un maximum de 2 cartes par tour.

# Diagramme de Robustesse

## Acteurs
- **Actor** : Représente l'utilisateur ou le joueur.

## Frontières (Boundary)
- **Main** : Interface principale du jeu.
- **Pioche** : Interface pour piocher des cartes.
- **Zone Attaque** : Zone où les cartes d'attaque sont jouées.
- **Zone Pop** : Zone où les cartes de popularité sont jouées.
- **Défausse** : Zone où les cartes inutilisées sont défaussées.
- **Zone Carte Spéciale** : Zone dédiée aux cartes spéciales.
- **Marché** : Interface pour interagir avec le marché.

## Contrôleurs (Control)
- **Control Carte Plateau** : Gère les interactions avec les cartes sur le plateau.
- **Control Joueur** : Gère les actions des joueurs.
- **Control Pioche** : Gère la pioche des cartes.
- **Control Carte Spéciale** : Gère les cartes spéciales.
- **Control Marché** : Gère les interactions avec le marché.
- **Control Jeu** : Gère la logique globale du jeu.
- **ParserCarte** : Gère le parsing des cartes depuis un fichier.

## Entités (Entity)
- **Joueur** : Représente un joueur dans le jeu.
- **Carte** : Représente une carte générique.
- **CarteActive** : Représente une carte active.
- **CartePassive** : Représente une carte passive.
- **Carte Spéciale** : Représente une carte spéciale.
- **CarteProbabilité** : Représente une carte avec des probabilités.
- **Marché** : Représente le marché.

## Relations
- L'**Actor** interagit avec **Main**.
- **Main** communique avec **Control Joueur**.
- **Control Joueur** interagit avec **Joueur** et **Control Pioche**.
- **Control Pioche** interagit avec **Pioche** et **Carte**.
- **Control Carte Plateau** gère les interactions entre **Zone Attaque**, **Zone Pop**, **Défausse**, et **CarteActive**/**CartePassive**.
- **Control Carte Spéciale** gère les interactions avec **Zone Carte Spéciale** et **Carte Spéciale**.
- **Control Marché** interagit avec **Marché** et **Carte**.
- **ParserCarte** interagit avec un fichier texte et **CarteProbabilité**.
