package sae_dev.vue

import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Border
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import sae_dev.controleur.ControleurClickPickomino
import sae_dev.modele.Diamond
import sae_dev.modele.PickominoBouton

class VueJoueur(val couleur : Color, val numJoueur: Int, val nom: String) : BorderPane() {
    val labelPoint : Label
    val diamant : Diamond
    var pickomino : PickominoBouton? = null
    val fontPoint = Font.loadFont("file:img/Poppins-Medium.ttf", 20.0)

    init {
        this.padding = Insets(0.0,40.0,0.0,0.0)
        this.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
        this.labelPoint = Label("Points : 0")
        labelPoint.font = fontPoint
        this.labelPoint.textFill = Color.WHITE
        //this.pickomino = PickominoBouton(Image("file:img/picko/21.png", 70.0,140.0, true, true),21)
        this.diamant = Diamond(couleur, 70.0, 100.0)
        setMargin(diamant, Insets(0.0,0.0,0.0,15.0))
        this.maxWidth = 5.0
        setAlignment(labelPoint, Pos.CENTER_RIGHT)

        this.bottom = labelPoint
        this.center = diamant
        this.left = pickomino
    }

    fun changementDomino(num: Int, event: ControleurClickPickomino){
        if (num != 0){
            this.pickomino = PickominoBouton(Image("file:img/picko/${num}.png", 70.0,140.0, true, true),num)
            this.pickomino?.onAction = event
        }else{
            this.pickomino = null
        }
        this.left = pickomino
    }

    fun changementScore(value: Int){
        this.labelPoint.text = "Points : ${value}"
    }
}