# Description des différents fichiers et dossiers

## Animation d'une balle

La première partie portant sur l'nimation d'une balle différents fichiers ont été créés pour cette anilation. Ainsi : 

Code 1 – TestGravityBall / GravityBall

Contient une simulation d’une balle unique soumise à la gravité et aux rebonds. Le code définit la classe GravityBall avec ses attributs (couleur, rayon, vitesse, force de gravité, coefficient de rebond) et gère le mouvement, les collisions avec les bords et le dessin dans la fenêtre graphique via GUISimulator. Le programme principal crée et lance une instance de balle.

Code 2 – TestBalls / BallsSimulator

Contient une simulation de plusieurs balles se déplaçant simultanément. La classe Balls stocke les positions et vitesses de chaque balle, et BallsSimulator gère la translation, les collisions avec les bords et le dessin graphique de toutes les balles dans la fenêtre GUISimulator. Le programme principal initialise le simulateur avec un ensemble de balles.

## Automate cellulaire 

### Le jeu de la vie de Conway



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
