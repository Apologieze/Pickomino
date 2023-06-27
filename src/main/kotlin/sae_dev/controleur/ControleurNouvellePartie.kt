package sae_dev.controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.RadioButton
import javafx.scene.paint.Color
import javafx.stage.Stage
import sae_dev.modele.Jeu
import sae_dev.vue.AccueilVue
import sae_dev.vue.MainVue
import sae_dev.vue.VueJoueur

class ControleurNouvellePartie(private val vue: AccueilVue, private val primaryStage: Stage, private val game: Jeu): EventHandler<ActionEvent> {
    override fun handle(event: ActionEvent?) {
        val selecButton = vue.checkNombreJoueurs.selectedToggle as RadioButton
        val nbJoueur = selecButton.text.toInt()
        game.newGame(nbJoueur)

        val newVue = MainVue()
        newVue.updatePickomino(game.accessiblePickomino(), ControleurClickPickomino(newVue,game, primaryStage))

        newVue.listeJoueur.add(VueJoueur(Color.web("#FF4242"), 1, "Rouge"))
        if (nbJoueur > 2){
            newVue.listeJoueur.add(VueJoueur(Color.web("#62FF5D"), 2, "Vert"))
        }
        if (nbJoueur > 3){
            newVue.listeJoueur.add(VueJoueur(Color.web("#FFEC5A"), 3, "Jaune"))
        }
        newVue.updateJoueur()

        newVue.boutonLancer.onAction = ControleurLancerDes(newVue, game, primaryStage)
        newVue.boutonPrendre.onAction = ControleurPrendrePicko(newVue, game)

        for (i in newVue.tileBtnGarder.children){
            val button = i as Button
            button.onAction = ControleurGarderDes(newVue, game, primaryStage)
        }

        val scene = Scene(newVue, 1400.0, 900.0)
        primaryStage.close()
        primaryStage.scene = scene
        primaryStage.show()
    }
}