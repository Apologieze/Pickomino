## Architecture du Répertoire

### Package `controleur`

- Fichier `ControleurClickPickomino.kt`
  - Fonction : `handle(event: ActionEvent?)`
    - Description : Gérer le clic sur un bouton de pickomino, de prendre le pickomino correspondant et de passer au joueur suivant dans le jeu Pickomino.

- Fichier `ControleurNouvellePartie.kt`
  
  - Fonction : `handle(event: ActionEvent?)`
    - Description : Contrôleur pour la création d'une nouvelle partie de jeu. Il gère l'interaction avec l'écran d'accueil et initialise le jeu avec le nombre de joueurs choisi.

- Fichier `ControleurLancerDes.kt`
  
  - Fonction : `handle(event: ActionEvent?)`
    - Description : Contrôleur pour l'action de lancer les dés. Il gère le lancer des dés par le joueur et met à jour l'interface utilisateur en conséquence.

- Fichier `ControleurGarderDes.kt`
  
  - Fonction : `handle(event: ActionEvent?)`
    - Description : Contrôleur pour l'action de garder certains dés. Il gère la sélection des dés à garder par le joueur et met à jour l'interface utilisateur en conséquence.

- Fichier `ControleurPrendrePicko.kt`
  
  - Fonction : `handle(event: ActionEvent?)`
    - Description : Contrôleur pour l'action de prendre un pickomino. Il gère la prise d'un pickomino par le joueur et met à jour l'interface utilisateur en conséquence.

- Fichier `ControleurRotationJoueur.kt`
  
  - Fonction : `changerJoueur()`
    - Description : Contrôleur pour la rotation des joueurs. Il gère la rotation des joueurs actifs et met à jour l'interface utilisateur en conséquence.
- ### Package `modele`

- Fichier `Jeu.kt`
  - Fonction : `newGame(nb_Player: Int)`
    - Description : Initialise une nouvelle partie de jeu avec le nombre de joueurs spécifié.
  - Fonction : `infoGame(): Game`
    - Description : Renvoie l'état actuel du jeu.
  - Fonction : `roll(): List<DICE>`
    - Description : Effectue le lancer de dés et renvoie la liste des dés obtenus.
  - Fonction : `rollDebug(dice: List<DICE>): List<DICE>`
    - Description : Effectue le lancer de dés avec des dés spécifiques pour le débogage et renvoie la liste des dés obtenus.
  - Fonction : `keep(dice: DICE): Boolean`
    - Description : Garde un dé spécifique et renvoie true si l'opération réussit, sinon renvoie false.
  - Fonction : `checkRoll(): List<DICE>`
    - Description : Renvoie la liste des dés lancés par le joueur actif.
  - Fonction : `checkKeep(): List<DICE>`
    - Description : Renvoie la liste des dés gardés par le joueur actif.
  - Fonction : `take(pickomino: Int): Boolean`
    - Description : Prend un pickomino spécifique et renvoie true si l'opération réussit, sinon renvoie false.
  - Fonction : `scorePlayer(): List<Int>`
    - Description : Renvoie la liste des scores des joueurs.
  - Fonction : `playerTurn(): Int`
    - Description : Renvoie le numéro du joueur actif.
  - Fonction : `pickoPlayer(): List<Int>`
    - Description : Renvoie la liste des pickominos des joueurs.
  - Fonction : `accessiblePickomino(): List<Int>`
    - Description : Renvoie la liste des pickominos accessibles.
  - Fonction : `pickoInStack(pickomino: Int): Boolean`
    - Description : Vérifie si un pickomino donné se trouve sur le dessus d'une pile de pickominos d'un autre joueur (à l'exception du joueur actif).
  - Fonction : `pickoTake(): List<Int>`
    - Description : Renvoie la liste des pickominos que le joueur peut prendre lors de son tour.
  - Fonction : `scoreKeepDice(): Int`
    - Description : Calcule le score total des dés gardés par le joueur.
  - Fonction : `buttonActivable(): ArrayList<Int>`
    - Description : Renvoie une liste d'indices des dés qui peuvent être activés (sélectionnés) par le joueur.
  - Fonction : `updateDeRoll(de: DICE): ArrayList<DICE>`
    - Description : Met à jour la liste des dés lancés en retirant un dé spécifique.
  - Fonction : `estFini(): Boolean`
    - Description : Vérifie si le jeu est terminé.

### Package `vue`

- Fichier `AccueilVue.kt`
  
  - Classe : `AccueilVue`
    - Description : Représente l'écran d'accueil du jeu où les joueurs peuvent choisir le nombre de joueurs.

- Fichier `MainVue.kt`
  
  - Classe : `MainVue`
    - Description : Représente la vue principale du jeu où le plateau de jeu est affiché et les actions des joueurs sont gérées.
    - Fonction : `updatePickomino(pickominos: List<Int>, eventHandler: EventHandler<ActionEvent>)`
      - Description : Met à jour les boutons de pickominos disponibles sur le plateau de jeu.
    - Fonction : `updateJoueur()`
      - Description : Met à jour l'affichage du joueur actif.
    - Fonction : `updatePickoTop(pickominos: List<Int>, eventHandler: EventHandler<ActionEvent>, scores: List<Int>)`
      - Description : Met à jour les pickominos visibles sur le dessus des piles des autres joueurs. Et met à jours les scores des joueurs.
    - Fonction : `disablePickomino(pickominos: List<Int>)`
      - Description : Désactive les boutons de pickominos spécifiés.
    - Fonction : `updateBtnDesGarde(buttonList: ArrayList<Int>)`
      - Description : Met à jour les boutons de dés gardés par le joueur.
    - Fonction : `updateDesLances(diceList: List<DICE>)`
      - Description : Met à jour les dés lancés par le joueur.
    - Fonction : `updateDesGardes(diceList: List<DICE>)`
      - Description : Met à jour les dés gardés par le joueur.

- Fichier `VueJoueur.kt`
  
  - Classe : `VueJoueur`
    - Description : Représente la vue d'un joueur affichant ses informations spécifiques telles que son score et ses pickominos.
    - Fonction : `changementDomino(num: Int, event: ControleurClickPickomino)`
      - Description : Met à jour l'image du pickomino du joueur.
    - Fonction : `changementScore(value: Int)`
      - Description : Met à jour l'affichage du score du joueur.
