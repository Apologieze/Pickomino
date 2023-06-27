package sae_dev.vue

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight

class AccueilVue : VBox(){
    val checkNombreJoueurs : ToggleGroup
    val boutonNouvellePartie : Button
    private val boxNombredejoueurs = HBox(40.0)


    init{
        //this.background = Background(BackgroundImage(Image("https://cdn.shopify.com/s/files/1/0607/9099/7215/products/89031.jpg?v=1636675653"),
        //BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)))
        this.background = Background.fill(Color.web("#292929"))

        val labelTitre = Label("Pickomino")
        val poppinsFont = Font.loadFont("file:img/Poppins-Medium.ttf", 30.0)
        val buttonFont = Font.loadFont("file:img/Poppins-SemiBold.ttf", 20.0)
        labelTitre.font= Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR,60.0)
        labelTitre.textFill= Color.WHITE
        labelTitre.padding = Insets(40.0)

        val labelNbJoueurs = Label("Nombre de joueurs :")
        labelNbJoueurs.textFill = Color.WHITE
        labelNbJoueurs.font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR,20.0)
        this.checkNombreJoueurs = ToggleGroup()
        val btNbJoueur2 = RadioButton("2")
        btNbJoueur2.style = "-fx-alignment: center; -fx-text-fill: white;"
        btNbJoueur2.font = buttonFont
        val btNbJoueur3 = RadioButton("3")
        btNbJoueur3.textFill = Color.WHITE
        btNbJoueur3.font = buttonFont
        val btNbJoueur4 = RadioButton("4")
        btNbJoueur4.font = buttonFont
        btNbJoueur4.textFill = Color.WHITE
        btNbJoueur2.toggleGroup = checkNombreJoueurs
        btNbJoueur3.toggleGroup = checkNombreJoueurs
        btNbJoueur4.toggleGroup = checkNombreJoueurs
        checkNombreJoueurs.selectToggle(btNbJoueur2)

        boxNombredejoueurs.children.addAll(btNbJoueur2,btNbJoueur3,btNbJoueur4)
        boxNombredejoueurs.alignment=Pos.CENTER
        boxNombredejoueurs.padding = Insets(0.0,0.0,30.0,0.0)

        boutonNouvellePartie = Button("Nouvelle Partie")
        boutonNouvellePartie.padding = Insets(10.0,0.0,10.0,0.0)
        boutonNouvellePartie.setPrefSize(290.0,50.0)
        boutonNouvellePartie.style = "-fx-base: #ff82f5; -fx-background-radius: 10px;"
        boutonNouvellePartie.font = poppinsFont

        this.children.addAll(labelTitre,labelNbJoueurs,boxNombredejoueurs,this.boutonNouvellePartie)
        this.alignment = Pos.CENTER
    }
}