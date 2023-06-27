import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import sae_dev.modele.Jeu
import java.util.stream.Stream

public class TestTurn {
    val connect = Connector.factory("172.26.82.76", "8080")


    // Test sur les rolls
    @Test
    fun roll_init_(){ // Test si au premier roll on a bien 8 dés
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        assertEquals(8, connect.rollDices(id, key).size)
    }
    @Test  // Test de l'interface

    fun gameRoll_init_(){ // Test si au premier roll on a bien 8 dés

        val game = Jeu(4)
        assertEquals(8, game.roll().size)
    }
    @ParameterizedTest
    @MethodSource("tabRoll")
    fun rollDe(number_de: Int, debug: List<List<DICE>>, de_keep: List<DICE>){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        var index = 0
        for (turn in debug){
            connect.choiceDices(id, key, turn)
            connect.keepDices(id,key,de_keep[index])
            index += 1
        }
        assertEquals(number_de, connect.rollDices(id, key).size)
    }


    // Test de retour d'Exception (tester de roll quand on ne peut pas )
    @Test
    fun rollExceptionTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.rollDices(id, key)
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.rollDices(id, key)}
    }

    // Test des changements de status
    @Test
    fun changeStatusRollTest(){ // Test le changement de status après un roll puis après un keep
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        val status = connect.gameState(id, key).current.status
        connect.rollDices(id, key)
        assertTrue(status != connect.gameState(id, key).current.status) // test si le status a bien changé
    }

    @Test
    fun changeStatusKeepTest(){ // Test le changement de status après un roll puis après un keep
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.rollDices(id, key)
        val status = connect.gameState(id, key).current.status
        connect.keepDices(id, key, connect.gameState(id,key).current.rolls[0]) //cela va garder un dé present dans le roll, cette fonction est testé autre part
        assertTrue(status != connect.gameState(id, key).current.status)
    }

    @Test
    fun changeStatusTakeOrRollTrueTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d3))
        connect.keepDices(id, key, DICE.worm)
        assertTrue(connect.gameState(id, key).current.status == STATUS.ROLL_DICE_OR_TAKE_PICKOMINO)
    }

    @ParameterizedTest
    @MethodSource("tabStatusChange")
    fun changeStatusTakeOrRollFalseTest1(dice: List<DICE>, keep: DICE){ // score pas suffisant + pas de worm pour prendre un pickomino
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, dice)
        connect.keepDices(id, key, keep)
        assertTrue(connect.gameState(id, key).current.status != STATUS.ROLL_DICE_OR_TAKE_PICKOMINO)
    }
    @Test
    fun changeStatusTakeTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm))
        connect.keepDices(id, key, DICE.worm)
        assertTrue(connect.gameState(id, key).current.status == STATUS.TAKE_PICKOMINO)
    }

    // Test de la fonction KeepDice

    @Test
    fun keepExceptionIdTest(){
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.rollDices(id,key)
        assertThrows<iut.info1.pickomino.exceptions.UnknownIdException> {connect.keepDices(id+50, key, DICE.d5)}
    }
    @Test
    fun keepExceptionKeyTest(){
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.rollDices(id,key)
        assertThrows<iut.info1.pickomino.exceptions.IncorrectKeyException> {connect.keepDices(id, key+50000, DICE.d5)}
    }
    @Test
    fun keepExceptionTest(){
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.keepDices(id, key, DICE.d5)}
    }

    @Test
    fun keepExceptionNotInRollTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1))
        assertThrows<iut.info1.pickomino.exceptions.DiceNotInRollException> {connect.keepDices(id, key, DICE.d5)}
    }

    @Test
    fun keepExceptionAlreadyKeptTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.d1,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5))
        connect.keepDices(id,key,DICE.d1)
        connect.choiceDices(id, key, listOf(DICE.d1,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5))
        assertThrows<iut.info1.pickomino.exceptions.DiceAlreadyKeptException> {connect.keepDices(id, key, DICE.d1)}
    }


    @Test
    fun keepTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.d1,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5))
        assertTrue(connect.keepDices(id, key, DICE.d1))
    }
    @Test
    fun keepTest2(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm, DICE.d1))
        assertTrue(connect.keepDices(id, key, DICE.worm))
    }
    // Test de takePickomino si cela renvoie bien true et false au moment indiqué et si cela throw bien les erreurs.
    @Test
    fun takePickoTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        assertTrue(connect.takePickomino(id, key, 35))
    }

    @Test
    fun notTakePickoTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        assertFalse(connect.takePickomino(id, key, 20))
    }


    @Test
    fun takePickoExceptionTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        assertThrows<iut.info1.pickomino.exceptions.BadPickominoChosenException> {connect.takePickomino(id, key, 25)}
    }


    @Test
    fun takePickoExceptionTest2(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm, DICE.d1))
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.takePickomino(id, key, 25)}
    }

    @Test
    fun takePickoExceptionTest3(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.takePickomino(id, key, 25)}
    }

    @Test
    fun takePickoExceptionTest4(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.d1)
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.takePickomino(id, key, 25)}
    }


    @Test
    fun rollDebugTest(){ // Test si quand le mode debug = false si on peut lancer rollDebug
        val connect = Connector.factory("172.26.82.76", "8080", false)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
    }


    @Test
    fun finalScoreExceptionTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.finalScore(id, key)}
    }
    @Test
    fun finalScoreKeyExceptionTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        assertThrows<iut.info1.pickomino.exceptions.IncorrectKeyException> {connect.finalScore(id, key+5000)}
    }
    @Test
    fun finalScoreIdExceptionTest(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        assertThrows<iut.info1.pickomino.exceptions.UnknownIdException> {connect.finalScore(id+50, key)}
    }
    @Test
    fun finalScoreExceptionTest2(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.rollDices(id, key)
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.finalScore(id, key)}
    }

    @Test
    fun finalScoreExceptionTest3(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm, DICE.d1))
        connect.keepDices(id, key, DICE.worm)
        assertThrows<iut.info1.pickomino.exceptions.BadStepException> {connect.finalScore(id, key)}
    }

    companion object{
        @JvmStatic

        fun tabRoll(): Stream<Arguments?>?{
            return Stream.of(
                Arguments.of(
                    5,
                    listOf(
                        listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d5) ,
                        listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d4,DICE.d4)),
                    listOf(DICE.d5, DICE.d4)),

                Arguments.of(
                    1,
                    listOf(
                        listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d5)),
                    listOf(DICE.d1)),
                Arguments.of(
                    8,
                    listOf(
                        listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1)),
                    listOf(DICE.d1)))}

        @JvmStatic
        fun tabStatusChange():Stream<Arguments?>?{
            return Stream.of(
                Arguments.of((listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d3)), DICE.d3),
                Arguments.of((listOf(DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d3)), DICE.d5),
                Arguments.of((listOf(DICE.worm,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d5,DICE.d3)), DICE.worm)
            )
        }

    }

}
