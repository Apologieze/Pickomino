package sae_dev.controleur

import iut.info1.pickomino.data.DICE
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage
import sae_dev.modele.Jeu
import sae_dev.vue.MainVue

class ControleurGarderDes(private val vue: MainVue, private val game: Jeu, primaryStage: Stage) : EventHandler<ActionEvent>{
    private val rotation = ControleurRotationJoueur(vue,game, primaryStage)
    override fun handle(event: ActionEvent?) {
        vue.espaceBas.left = vue.vboxLancer
        val tempArray = arrayOf<DICE>(DICE.d1, DICE.d2, DICE.d3, DICE.d4, DICE.d5, DICE.worm)
        val de = tempArray[vue.tileBtnGarder.children.indexOf(event?.source)]
        vue.updateDesLances(game.updateDeRoll(de))
        val tempJoueur = vue.joueurEnCours
        game.keep(de)
        vue.boutonPrendre.isDisable = game.pickoTake().isEmpty()
        val checkKeep = game.checkKeep()
        vue.updateDesGardes(checkKeep)
        vue.labelNbScore.text = game.scoreKeepDice().toString()
        if (checkKeep.size == 8){
            vue.boutonLancer.isDisable = true
        }
        if(checkKeep.isEmpty()){
            var text = "Le joueur ${tempJoueur.nom} n'a pas réussi à prendre de pickomino"
            val tempPicko = tempJoueur.pickomino
            if (tempPicko != null){
                text+="\net a perdu le pickomino ${tempPicko.valeur}"
                game.removePickoJoueur(vue.joueurEnCours.numJoueur)
            }
            rotation.changerJoueur(text)
        }
    }
}
