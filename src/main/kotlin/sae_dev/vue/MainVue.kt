package sae_dev.vue

import iut.info1.pickomino.data.DICE
import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import sae_dev.controleur.ControleurClickPickomino
import sae_dev.modele.PickominoBouton

class MainVue(): BorderPane() {
    private val tilePickomino = TilePane(10.0, 10.0)
    private val espaceCentre = HBox(100.0)
    private val espaceJoueurEnCours = VBox()
    private val espaceJoueur = HBox(200.0)
    private val espaceDesLance = VBox()
    private val espaceDesGarde = VBox(10.0)
    val espaceBas = BorderPane()
    val listeJoueur = ArrayList<VueJoueur>()
    var joueurEnCours = VueJoueur(Color.web("#00ABFA"), 0, "Bleu")
    val vboxLancer = VBox(30.0)
    val vboxGarder = VBox()
    val tileBtnGarder = TilePane(10.0, 10.0)
    val hboxScore = HBox(10.0)

    val boutonLancer = Button("Lancer les dés")
    val boutonPrendre = Button("Prendre Pickomino")
    private val labelGarder = Label("Garder :")
    private val fontLabel = Font.loadFont("file:img/Poppins-Medium.ttf", 30.0)
    private val fontButton = Font.loadFont("file:img/Poppins-SemiBold.ttf", 25.0)
    private val imageDesLances = TilePane(8.0, 8.0)
    private val imageDesGardes = TilePane(8.0, 8.0)
    val labelNbScore = Label("0")


    init{
        this.background = Background.fill(Color.web("#292929"))

        val labelScore = Label("Score dés :")
        labelScore.font = fontLabel
        labelScore.textFill = Color.WHITE
        labelScore.style = "-fx-text-overrun: ellipsis;"
        val labelDesEnJeu = Label("Dés en jeu")
        labelDesEnJeu.visibleProperty().bind(Bindings.isNotEmpty(imageDesLances.children))
        labelDesEnJeu.textFill = Color.WHITE
        labelDesEnJeu.font = fontLabel
        val labelJoueurEnCours = Label("Joueur en cours:")
        labelJoueurEnCours.font = fontLabel
        labelJoueurEnCours.textFill = Color.WHITE



        tilePickomino.prefColumns = 8
        tilePickomino.maxWidth = 650.0
        tilePickomino.alignment = Pos.TOP_CENTER

        imageDesGardes.prefColumns = 4
        //imageDesGardes.style = "-fx-border-color: yellow; -fx-border-width: 3px;"
        imageDesGardes.alignment = Pos.CENTER_RIGHT
        imageDesGardes.maxWidth = 330.0

        labelNbScore.textFill = Color.BLACK
        labelNbScore.style = "-fx-background-color: white"
        labelNbScore.font = Font.font("Verdana", FontWeight.BLACK, 30.0)
        labelNbScore.padding = Insets(5.0,30.0,5.0,30.0)

        hboxScore.children.addAll(labelScore,labelNbScore)
        hboxScore.alignment = Pos.CENTER_RIGHT
        //hboxScore.style = "-fx-border-color: white; -fx-border-width: 3px;"
        hboxScore.padding = Insets(0.0, 15.0,0.0,0.0)
        hboxScore.visibleProperty().bind(Bindings.isNotEmpty(imageDesGardes.children))
        espaceDesGarde.alignment = Pos.TOP_RIGHT
        //espaceCentre.alignment = Pos.CENTER_RIGHT
        //espaceDesGarde.style = "-fx-border-color: blue; -fx-border-width: 3px;"
        espaceDesGarde.minWidth = 300.0
        espaceDesGarde.padding = Insets(0.0,10.0,0.0,0.0)
        espaceDesGarde.children.addAll(hboxScore, imageDesGardes)

        HBox.setHgrow(tilePickomino, Priority.ALWAYS)
        HBox.setHgrow(espaceDesGarde, Priority.ALWAYS)
        espaceCentre.padding = Insets(50.0,10.0,50.0,30.0)
        espaceCentre.children.addAll(tilePickomino,espaceDesGarde)



        boutonLancer.setPrefSize(270.0,55.0)
        boutonPrendre.setPrefSize(270.0,55.0)
        boutonLancer.style = "-fx-base: #faaaf4; -fx-background-radius: 10px;"
        boutonLancer.font = fontButton
        boutonPrendre.style = "-fx-base: #faaaf4; -fx-padding: 0 0 0 0; -fx-background-radius: 10px;"
        boutonPrendre.font = fontButton
        boutonPrendre.isDisable = true
        vboxLancer.children.addAll(boutonLancer,boutonPrendre)
        vboxLancer.padding = Insets(0.0,0.0,30.0,30.0)
        vboxLancer.alignment = Pos.BOTTOM_LEFT

        labelGarder.textFill = Color.WHITE
        labelGarder.font = fontLabel

        for (i in listOf<String>("1","2","3","4","5","Vers")){
            val tempButton = Button(i)
            tempButton.setPrefSize(70.0,70.0)
            tempButton.padding = Insets(10.0,0.0,10.0,0.0)
            tempButton.style = "-fx-base: #ff82f5; -fx-background-radius: 10px;"
            tempButton.font = fontButton
            tileBtnGarder.children.add(tempButton)
        }

        tileBtnGarder.prefColumns = 3
        vboxGarder.padding = Insets(0.0,30.0,0.0,40.0)
        vboxGarder.children.addAll(labelGarder, tileBtnGarder)

        espaceJoueur.children.addAll(listeJoueur)
        espaceJoueur.alignment = Pos.CENTER
        espaceJoueur.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
        espaceJoueur.maxWidth= Double.MAX_VALUE
        espaceJoueurEnCours.children.addAll(labelJoueurEnCours,joueurEnCours)
        espaceJoueurEnCours.padding = Insets(0.0,0.0,0.0,100.0)

        this.top=espaceJoueur
        this.bottom=espaceBas
        this.center=espaceCentre

        espaceBas.padding = Insets(0.0,0.0,10.0,0.0)
        espaceBas.left = vboxLancer
        espaceBas.center = espaceJoueurEnCours
        espaceBas.right = espaceDesLance

        imageDesLances.prefColumns = 4
        imageDesLances.alignment = Pos.CENTER_RIGHT
        espaceDesLance.padding = Insets(0.0,10.0,0.0,0.0)
        espaceDesLance.children.addAll(labelDesEnJeu,imageDesLances)

    }

    fun updateJoueur(){ //remplace le joueur qui jouait au dernier tour par celui qui va jouer en faisant la rotation des joueurs dans l'affichage
        espaceJoueurEnCours.children[1] = joueurEnCours
        espaceJoueur.children.clear()
        espaceJoueur.children.addAll(listeJoueur)
    }

    fun updatePickoTop(listeDomino: List<Int>, event: ControleurClickPickomino, listeScore: List<Int>){ //Actualise les vues sur les joueurs, leurs scores est mis à jour et leurs pickomino au sommet est changé aussi. Elle met aussi len controleur sur les pickomino.
        for (i in listeJoueur){
            i.changementDomino(listeDomino[i.numJoueur], event)
            i.changementScore(listeScore[i.numJoueur])
        }
        joueurEnCours.changementDomino(listeDomino[joueurEnCours.numJoueur], event)
        joueurEnCours.changementScore(listeScore[joueurEnCours.numJoueur])
    }

    fun updateBtnDesGarde(boutons: ArrayList<Int>){ //Met à jour les boutons qui permettent de garder les dés, elle active et désactive les boutons selon les dés qui ont été lancés et déja pris.
        for ((i, e) in tileBtnGarder.children.withIndex()){
            e.isDisable = i !in boutons
        }
    }

    fun updateDesLances(desLances : List<DICE>){ //Actualise l'affichage des dés qui sont lancés.
        imageDesLances.children.clear()
        for (des in desLances){
            imageDesLances.children.add(ImageView(Image("file:img/dee/${des.name}.png",70.0,70.0, true, true)))
        }
    }

    fun updateDesGardes(desLances : List<DICE>){ //Actualise l'affichage des dés qui ont sont gardés.
        imageDesGardes.children.clear()
        for (des in desLances){
            imageDesGardes.children.add(ImageView(Image("file:img/dee/${des.name}.png",70.0,70.0, true, true)))
        }
    }

    fun updatePickomino(domino: List<Int>, event: ControleurClickPickomino){ //Actualise les pickomino qui sont des boutons qui sont encore prenable au centre.
        tilePickomino.children.clear()
        for (i in domino){
            val tempButton = PickominoBouton(Image("file:img/picko/${i}.png",70.0,140.0, true, true), i)
            tempButton.onAction = event
            tilePickomino.children.add(tempButton)
        }
    }

    fun disablePickomino(domino: List<Int>){ //Désactive tout les pickomino que le joueur ne peut pas prendre avec son score.
        for (i in tilePickomino.children){
            val tempPicko = i as PickominoBouton
            i.pickable(tempPicko.valeur in domino)
        }
        for (i in listeJoueur){
            val tempValue = i.pickomino?.valeur
            i.pickomino?.pickable(tempValue in domino)
        }
    }
}