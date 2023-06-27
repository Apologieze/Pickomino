package sae_dev

import sae_dev.modele.Jeu
import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import sae_dev.vue.AccueilVue
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import sae_dev.controleur.ControleurNouvellePartie


class Main: Application() {

    override fun start(primaryStage: Stage) {
        val vue = AccueilVue()
        val scene = Scene(vue, 800.0, 500.0)
        val game = Jeu(2)

        vue.boutonNouvellePartie.onAction = ControleurNouvellePartie(vue, primaryStage, game)
        primaryStage.title="Pickomino"
        primaryStage.scene=scene
        primaryStage.show()
    }
}


fun main() {

    Application.launch(Main::class.java)
}
