# Description des différents fichiers et dossiers

## Animation d'une balle

La première partie portant sur l'nimation d'une balle différents fichiers ont été créés pour cette anilation. Ainsi : 

**TestGravityBall / GravityBall**

Contient une simulation d’une balle unique soumise à la gravité et aux rebonds. Le code définit la classe GravityBall avec ses attributs (couleur, rayon, vitesse, force de gravité, coefficient de rebond) et gère le mouvement, les collisions avec les bords et le dessin dans la fenêtre graphique via GUISimulator. Le programme principal crée et lance une instance de balle.

TestBalls / BallsSimulator

Contient une simulation de plusieurs balles se déplaçant simultanément. La classe Balls stocke les positions et vitesses de chaque balle, et BallsSimulator gère la translation, les collisions avec les bords et le dessin graphique de toutes les balles dans la fenêtre GUISimulator. Le programme principal initialise le simulateur avec un ensemble de balles.

## Automate cellulaire 

Pour cette partie des automates une partie graphique a du être créée pour les différents jeux codés après. 

Board (cellularSim)

Contient la gestion d’un plateau pour simulation cellulaire. La classe Board crée une grille de cellules (Cell) et gère le calcul des voisins, les états des cellules, et les évolutions selon différents types de règles (Conway, Immigration, Seg). Le code inclut aussi la logique de bordures “wrap-around” et de propagation d’états pour chaque génération.

Cell (cellularSim)

Contient la définition d’une cellule pour simulation cellulaire. La classe Cell gère l’état de la cellule, son état maximum, et un seuil spécifique (segSeuil). Elle inclut les règles d’évolution pour différents types de simulations (Conway, Immigration, Seg), la vérification si la cellule est vivante, et la gestion de transitions d’état.


### Le jeu de la vie de Conway


ConwayBoard (cellularSim)

Contient la simulation graphique du jeu de la vie de Conway. La classe ConwayBoard hérite de Board et utilise GUISimulator pour dessiner chaque cellule avec un dégradé de couleur selon son état. Elle gère l’évolution des générations, le rafraîchissement graphique et la réinitialisation du plateau à son état de départ.


GameOfLife (cellularSim)

Contient le programme principal pour lancer une simulation graphique du jeu de la vie de Conway. Le code initialise une grille de cellules vivantes (aléatoire ou prédéfinie) et crée une instance de ConwayBoard qui gère l’évolution des générations et le rendu graphique via GUISimulator.


### Le jeu de l'immigration

ImmigrationBoard (cellularSim)

Contient la simulation graphique du jeu de l’Immigration. La classe ImmigrationBoard hérite de Board et utilise GUISimulator pour dessiner les cellules avec un dégradé de couleur selon leur état. Elle gère l’évolution des générations selon la règle d’Immigration, le rafraîchissement graphique et la réinitialisation du plateau à son état initial.

GameOfImmigration (cellularSim)

Contient le programme principal pour lancer une simulation graphique du jeu de l’Immigration. Le code initialise une grille de cellules avec des états aléatoires (ou définis), puis crée une instance d’ImmigrationBoard qui gère l’évolution des générations selon la règle d’Immigration et le dessin graphique via GUISimulator.


### Le modèle de Schelling

SegBoard (cellularSim)

Contient la simulation graphique d’un plateau cellulaire avec règles “Seg”. La classe SegBoard hérite de Board et utilise GUISimulator pour dessiner les cellules avec un dégradé de couleur selon leur état et seuil spécifique (segSeuil). Elle gère l’évolution des générations selon la règle Seg, le rafraîchissement graphique et la réinitialisation du plateau à son état initial.

SegregationSim (cellularSim)

Contient le programme principal pour lancer une simulation graphique du modèle de ségrégation. Le code initialise une grille de cellules avec des états aléatoires selon un pourcentage de cellules mortes, puis crée une instance de SegBoard qui gère l’évolution des générations selon la règle de ségrégation et le rendu graphique via GUISimulator.















# TPL 2A POO 

Les ressources distribuées contiennent:

- une librairie d'affichage graphique d'un simulateur (lib/gui.jar) et sa documentation (doc/index.html)
- un fichier de démonstration du simulateur (src/TestInvader.java)


## Compilation & exécution
### Avec un makefile?
Un fichier Makefile est distribué pour facilement compiler et exécuter le fichier TestInvader.java

Mais vu la taille de ce projet, il est ***très fortement recommandé d'utiliser un IDE*** pour compiler, exécuter et déboguer votre code!

### IDE Idea Intellij
- créer un nouveau projet:
    - menu *File/New Project*
    - si le répertoire distribué est dans "~/Ensimag/2A/POO/TPL_2A_POO", alors paramétrer les champs *Name* avec "TPL_2A_POO" et *Location* avec "~/Ensimag/2A/POO/"
    - configurer l'utilisation de la librairie
    - menu *File/Project Structure* puis *Projet setting/Modules*
    - clicker sur(*Add* puis "JARs & Directories" et sélectionner ~/Ensimag/2A/POO/TPL_2A_POO/lib
    - vous pouvez bien sûr utiliser git via l'interface d'idea Intellij

### IDE VS Code
- dans "~/Ensimag/2A/POO/TPL_2A_POO", lancer *code ."
- si vous avez installé les bonnes extensions java (exécution, debogage...) il est possible que tout fonctionne sans rien faire de spécial.
- s'il ne trouve pas la librairie, vous devez alors créer un vrai "projet" et configurer l'import du .jar.
- pas vraiment d'aide pour ça, vous trouverez
- vous pouvez bien sûr utiliser git via l'interface de VS code
