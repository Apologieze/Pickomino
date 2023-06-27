package sae_dev.controleur

import iut.info1.pickomino.data.DICE
import javafx.event.ActionEvent
import javafx.event.EventHandler
import sae_dev.modele.Jeu
import sae_dev.vue.MainVue

class ControleurPrendrePicko(private val vue: MainVue, private val game: Jeu) : EventHandler<ActionEvent>{
    override fun handle(event: ActionEvent?) {
        vue.disablePickomino(game.pickoTake())
        vue.espaceBas.left.isVisible = false
    }
}