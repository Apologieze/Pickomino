import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import sae_dev.modele.Jeu
import java.util.stream.Stream

public class TestPickomino {

    // Test pour vérifier si playerPicko est bien modifié
    @ParameterizedTest
    @MethodSource("tabPlayerNbr")
    fun playerPickoTest(nbPlayer: Int, nbr: Int) { // Test paramétrique des valeurs d'entré
        val connect = Connector.factory("172.26.82.76", "8080", false)
        val identification = connect.newGame(nbPlayer)
        val id = identification.first
        val key = identification.second
        assertThrows<IndexOutOfBoundsException> { connect.gameState(id, key).pickosStackTops()[nbr] }
    }

    @ParameterizedTest
    @MethodSource("tabPlayerPicko")
    fun playerPickoTest2(nbPlayer: Int, nbr: Int) { // Test paramétrique des valeurs d'entré
        val connect = Connector.factory("172.26.82.76", "8080", false)
        val identification = connect.newGame(nbPlayer)
        val id = identification.first
        val key = identification.second
        assertEquals(connect.gameState(id, key).pickosStackTops()[nbr], 0) // Cela permet de tester si a l'init vous avez bien 0 comme sommet
    }

    @Test
    fun playerPickoTest3() { // Test si après un take la mise a jour est effectué
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        connect.keepDices(id,key, DICE.worm)
        connect.takePickomino(id,key,35)
        assertEquals(connect.gameState(id, key).pickosStackTops()[0], 35)
    }

    @Test
    fun playerPickoTest4() { // Test si après un take du joueur 1 si le joueur deux garde la valeur 0
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        connect.keepDices(id,key, DICE.worm)
        connect.takePickomino(id,key,35)
        assertEquals(connect.gameState(id, key).pickosStackTops()[1], 0)
    }
    @Test
    fun playerPickoTest5() { // Test si après un take du joueur 1 du picko 35 et un take du joueur 2 du picko 35 si le stacktop du joueur 2 reprend bien le 35
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        connect.keepDices(id,key, DICE.worm)
        connect.takePickomino(id,key,35)
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        connect.keepDices(id,key, DICE.worm)
        connect.takePickomino(id,key,35)
        assertEquals(connect.gameState(id, key).pickosStackTops()[1], 35)
    }
    @Test
    fun playerPickoTest6() { // Test si après un take du joueur 1 du picko 35 et un take du joueur 2 du picko 35 si le stacktop du joueur 1 revient bien a 0
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val identification = connect.newGame(2)
        val id = identification.first
        val key = identification.second
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        connect.keepDices(id,key, DICE.worm)
        connect.takePickomino(id,key,35)
        connect.choiceDices(id, key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        connect.keepDices(id,key, DICE.worm)
        connect.takePickomino(id,key,35)
        assertEquals(connect.gameState(id, key).pickosStackTops()[0], 0)
    }

    @Test
    fun pickoTaketest(){ // test si sans nombre cela renvoie bien un liste vide
        val game = Jeu(2, true)
        assertEquals(listOf<Int>(), game.pickoTake())
    }
    @Test
    fun pickoTaketest2(){ // Test si cela renvoie uniquement le pickomino avec notre score de dé quand il se situe au centre
        val game = Jeu(2, true)
        game.rollDebug(listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        game.keep(DICE.worm)
        assertEquals(listOf(35), game.pickoTake())
    }
    @Test
    fun pickoTaketest3(){ // test si quand le picko est au top de la pile adverse si cela nous renvoie ce picko + celui du centre
        val game = Jeu(2, true)
        game.rollDebug(listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        game.keep(DICE.worm)
        game.take(35)
        game.rollDebug(listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1))
        game.keep(DICE.worm)

        assertEquals(listOf(34, 35), game.pickoTake())
    }
    @Test
    fun pickoTaketest4(){ // Test quand le score exacte est chez l'ennemi et que c'est le plus petit visible si cela renvoie bien que son picko
        val game = Jeu(2, true)
        game.rollDebug(listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d3,DICE.d3,DICE.d2,DICE.d1))
        game.keep(DICE.worm)
        game.rollDebug(listOf(DICE.d1,DICE.worm,DICE.worm,DICE.worm))
        game.keep(DICE.d1)
        game.take(21)
        game.rollDebug(listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d3,DICE.d3,DICE.d2,DICE.d1))
        game.keep(DICE.worm)
        game.rollDebug(listOf(DICE.d1,DICE.worm,DICE.worm,DICE.worm))
        game.keep(DICE.d1)
        assertEquals(listOf(21), game.pickoTake())
    }
    @Test
    fun pickoTaketest5(){ // Test quand le score n'est pas égal à des pickominos visible, retourne le plus proche du score inférieur
        val game = Jeu(2, true)
        game.rollDebug(listOf(DICE.worm, DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm))
        game.keep(DICE.worm)
        assertEquals(listOf(36), game.pickoTake())
    }

    companion object {
        @JvmStatic
        fun tabPlayerNbr(): Stream<Arguments?>? {
            return Stream.of(
                    Arguments.of(2, 3),
                    Arguments.of(3, 4),
                    Arguments.of(4, 5),
                    Arguments.of(3, 3),
                    Arguments.of(2, 2)
            )
        }

        @JvmStatic
        fun tabPlayerPicko(): Stream<Arguments?>? {
            return Stream.of(
                    Arguments.of(2, 1),
                    Arguments.of(2, 0),
                    Arguments.of(3, 2),
                    Arguments.of(4, 3)
            )
        }
    }
}
