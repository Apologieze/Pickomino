package sae_dev.controleur

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.Window
import sae_dev.modele.Diamond
import sae_dev.modele.Jeu
import sae_dev.vue.AccueilVue
import sae_dev.vue.MainVue

class ControleurRotationJoueur(private val vue: MainVue, private val game: Jeu, private val primaryStage: Stage){
    fun changerJoueur(text: String = ""){
        if (game.isFinished()){
            finishGame()
            return
        }
        vue.updatePickoTop(game.pickoPlayer(),ControleurClickPickomino(vue,game,primaryStage), game.scorePlayer())
        vue.updatePickomino(game.accessiblePickomino(), ControleurClickPickomino(vue,game,primaryStage))
        vue.espaceBas.left.isVisible = true
        vue.boutonPrendre.isDisable = true
        vue.labelNbScore.text = "0"
        vue.updateDesLances(emptyList())
        vue.updateDesGardes(emptyList())
        vue.listeJoueur.add(vue.joueurEnCours)
        vue.joueurEnCours = vue.listeJoueur.removeAt(0)
        vue.updateJoueur()
        vue.boutonLancer.isDisable = false

        val dialog = Alert(Alert.AlertType.INFORMATION)
        dialog.title="Changement joueur"
        dialog.headerText="C'est maintenant au tour \ndu joueur ${vue.joueurEnCours.nom} de jouer"
        dialog.contentText = text
        dialog.graphic = Diamond(vue.joueurEnCours.couleur,70.0,100.0, Color.web("#292929"))
        dialog.initStyle(StageStyle.UTILITY)
        dialog.dialogPane
        dialog.dialogPane.style = "-fx-font-size: 24px; -fx-font-weight: bold;"
        requestFocusOnAlertWindow(dialog)
        dialog.showAndWait()
    }

    private fun requestFocusOnAlertWindow(alert: Alert) { //Permet de mettre le focus sur la PopUp alerte
        Platform.runLater {
            val window: Window? = alert.dialogPane.scene.window
            window?.requestFocus()
        }
    }

    private fun finishGame(){
        vue.listeJoueur.add(vue.joueurEnCours.numJoueur, vue.joueurEnCours)
        val textTitle: String
        val scoreFinal = game.scorePlayer()
        val maxPicko = game.maxPicko()
        val gagnant = game.endgame()
        var graphic: Diamond? = null
        if (scoreFinal.sum() == 0){
            textTitle = "Auncun joueur n'a gagné\nEgalité vous avez tous 0"
        }else{
            textTitle = "Le joueur ${vue.listeJoueur[gagnant].nom} a gagné"
            graphic = vue.listeJoueur[gagnant].diamant
        }
        var text = ""
        for ((i, e) in scoreFinal.withIndex()){
            text += "Joueur ${vue.listeJoueur[i].nom} finit avec ${e} points\n"
        }
        val dialog = Alert(Alert.AlertType.INFORMATION)
        dialog.title="Fin de la partie"
        dialog.headerText=textTitle
        dialog.contentText = text
        dialog.graphic = graphic
        dialog.initStyle(StageStyle.UTILITY)
        dialog.dialogPane
        dialog.dialogPane.style = "-fx-font-size: 24px; -fx-font-weight: bold;"
        dialog.showAndWait()
        primaryStage.close()
        val newVue = AccueilVue()
        val scene = Scene(newVue, 800.0, 500.0)
        game.newGame(2)
        primaryStage.scene = scene
        newVue.boutonNouvellePartie.onAction = ControleurNouvellePartie(newVue, primaryStage, game)
        primaryStage.title="Pickomino"
        primaryStage.scene=scene
        primaryStage.show()
    }
}