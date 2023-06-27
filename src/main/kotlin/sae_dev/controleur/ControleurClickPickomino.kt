package sae_dev.controleur

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage
import sae_dev.modele.Jeu
import sae_dev.modele.PickominoBouton
import sae_dev.vue.MainVue

class ControleurClickPickomino(private val vue: MainVue, private val game: Jeu, primaryStage: Stage) : EventHandler<ActionEvent>{
    private val rotation = ControleurRotationJoueur(vue,game, primaryStage)
    override fun handle(event: ActionEvent?) {
        val tempBut = event?.source as PickominoBouton
        if (tempBut.isPickable){
            game.take(tempBut.valeur)
            rotation.changerJoueur("Le joueur ${vue.joueurEnCours.nom} a pris le pickomino ${tempBut.valeur}")
        }
    }
}