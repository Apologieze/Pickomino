package sae_dev.controleur

import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage
import sae_dev.modele.Jeu
import sae_dev.vue.MainVue

class ControleurLancerDes(private val vue: MainVue, private val game: Jeu, primaryStage: Stage) : EventHandler<ActionEvent>{
    private val rotation = ControleurRotationJoueur(vue,game,primaryStage)
    override fun handle(event: ActionEvent?) {
        val tempJoueur = vue.joueurEnCours
        game.roll()
        vue.updateBtnDesGarde(game.buttonActivable())
        vue.updateDesLances(game.checkRoll())

        if (game.infoGame().current.status == STATUS.ROLL_DICE || game.infoGame().current.status == STATUS.GAME_FINISHED){
            var text = "Le joueur ${tempJoueur.nom} n'a pas réussi à prendre de pickomino"
            val tempPicko = tempJoueur.pickomino
            if (tempPicko != null){
                text+="\net a perdu le pickomino ${tempPicko.valeur}"
                game.removePickoJoueur(vue.joueurEnCours.numJoueur)
            }
            rotation.changerJoueur(text)
        }else{
            vue.espaceBas.left = vue.vboxGarder
        }
    }
}