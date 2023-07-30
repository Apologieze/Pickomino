package sae_dev.modele

import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.Game
import iut.info1.pickomino.data.STATUS


// Cette classe a des méthodes liées au serveur (interface) et des méthodes utilisés dans les contrôleur
class Jeu(nb_player: Int, debug: Boolean = false) {
    private var id : Int
    private var key : Int
    private val connect : Connector
    private val identification : Pair<Int, Int>
    val pickoJoueurList : ArrayList<ArrayList<Int>> = arrayListOf()
    init {
        connect = Connector.factory("127.0.0.1", "8080", debug)

        identification = connect.newGame(nb_player)
        id = identification.first
        key = identification.second
    }

    fun newGame(nb_Player: Int){ //Réinitialise la partie et permet de faire une nouvelle partie avec X joueurs
        val newIdentification = connect.newGame(nb_Player)
        this.id = newIdentification.first
        this.key = newIdentification.second
        for (i in 0..nb_Player-1){
            pickoJoueurList.add(arrayListOf())
        }
    }
    fun infoGame():Game{
        return connect.gameState(id, key)
    }

    // Interface concernant les actions roll et keep dice effectué par le serveur
    fun roll(): List<DICE> { // Interface permettant de lancer les dés
        return connect.rollDices(id, key)
    }
    fun rollDebug(dice: List<DICE>): List<DICE>{
        return connect.choiceDices(id, key, dice)
    }

    fun keep(dice: DICE): Boolean { // Interface permettant de garder les dés
        return connect.keepDices(id, key, dice)
    }

    fun checkRoll(): List<DICE>{ // Interface renvoyant la liste des dés lancé
        return infoGame().current.rolls
    }
    fun checkKeep(): List<DICE>{ // Interface renvoyant la liste des dés gardé
        return connect.gameState(id, key).current.keptDices
    }

    // Fonction prendre un pickomino
    fun take(pickomino: Int): Boolean{
        pickoJoueurList[connect.gameState(id,key).current.player].add(pickomino)
        return connect.takePickomino(id, key, pickomino)
    }

    fun scorePlayer(): List<Int> { // Interface renvoyant la liste des scores des joueurs de la partie
        return connect.gameState(id, key).score()

    }

    // Interface prennant le numéro du joueur
    fun pickoPlayer(): List<Int> {
        return connect.gameState(id, key).pickosStackTops()
    }

    //
    fun accessiblePickomino (): List<Int>{ // Interface renvoyer la liste des pickomino du centre
        return connect.gameState(id, key).accessiblePickos()
    }

    // return true si le pickomino est dans un sommet de pile d'un des joueurs mais pas celui du joueur courrant
    fun pickoInStack(pickomino : Int):Boolean{
        return (pickomino in infoGame().pickosStackTops() && pickomino != pickoPlayer()[infoGame().current.player])
    }


    // Renvoie la liste des pickominos qu'on peut prendre
    fun pickoTake():List<Int>{
        if (infoGame().current.status == STATUS.ROLL_DICE){
            return listOf()
        }
        val score = scoreKeepDice()
        if (score in accessiblePickomino()){
            return listOf(score)  // Liste contenant uniquement le pickomino du milieu
        }

        for (number in score downTo 21){ //Regarde si le pickomino est dans
            if (number in accessiblePickomino()){
                return if (pickoInStack(score)) {
                    listOf(number, score)
                }else{
                    listOf(number)
                }
            }
        }
        if (pickoInStack(score)) {  // regarde si le pickomino est dans la liste d'un joueur except le joueur courrant
            return listOf(score)
        }

        return listOf()
    }

    fun scoreKeepDice():Int{ // Renvoie le score des dées que le joueur a déjà gardé
        val diceList = checkKeep()
        var score = 0
        for (dice in diceList){
            score += if (dice == DICE.worm){
                dice.ordinal
            }else{
                dice.ordinal + 1
            }
        }
        return score
    }

    fun buttonActivable():ArrayList<Int>{ // Fonction qui renvoie une Arrayliste de Int permettant de savoir quelle bouton doit être activé.
        val buttonList = ArrayList<Int>()
        var index = 0
        val tempList = listOf(DICE.d1,DICE.d2,DICE.d3,DICE.d4,DICE.d5,DICE.worm)
        val tempCheckKeep = checkKeep()
        val tempCheckRoll = checkRoll()

        for (dice in tempList){
            if (dice !in tempCheckKeep && dice in tempCheckRoll){
                buttonList.add(index)
            }
            index += 1
        }
        return buttonList
    }

    fun updateDeRoll(de: DICE):ArrayList<DICE>{ //Permet de mettre a jour la liste de dé après avoir retiré les dés gardé.
        val deList = checkRoll()
        val retour = ArrayList<DICE>()
        for (i in deList){
            if (i != de){
                retour.add(i)
            }
        }
        return retour
    }

    fun isFinished(): Boolean{ // Renvoie true si la partie a le status terminé
        return infoGame().current.status == STATUS.GAME_FINISHED
    }

    fun removePickoJoueur(joueur: Int){
        pickoJoueurList[joueur].removeAt(pickoJoueurList[joueur].lastIndex)
    }

    fun maxPicko(): ArrayList<Int> {
        val list: ArrayList<Int> = arrayListOf()
        for (i in pickoJoueurList){
            val max = i.maxOrNull() ?: 0
            list.add(max)
        }
        return list
    }

    fun endgame(): Int{
        val listeScore = scorePlayer()
        val maxPicko = maxPicko()
        var max = listeScore[0]
        var egalite = arrayListOf<Int>(0)

        for (index in 1..listeScore.size -1){
            if (listeScore[index] > max){
                max = listeScore[index]
                egalite = arrayListOf(index)
            }
            if (listeScore[index] > max){
                egalite.add(index)
            }
        }
        var winner = egalite[0]
        for (challenger in egalite){
            if (maxPicko[challenger] > maxPicko[winner]){
                winner = challenger
            }
        }
        return winner
    }
}
