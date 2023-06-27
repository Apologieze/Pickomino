import iut.info1.pickomino.Connector
import iut.info1.pickomino.exceptions.UnknownIdException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import sae_dev.modele.Jeu

class TestGame {
    val connect = Connector.factory("172.26.82.76", "8080")
    val identification = connect.newGame(4)
    val id = identification.first
    val key = identification.second
    @Test
    fun player_len_Serveur_Test4() {
        val identification = connect.newGame(4)
        val id = identification.first
        val key = identification.second
        try {  // On ne passe pas par un assertThrow mais par un try pour qu'il puisse être validé et renvoyé un message en cas de fail
            connect.gameState(id, key)
        }catch (e: UnknownIdException){
            fail { "len player not in [2;4]" }
        }

        //Test serveur ssi (joueurs == 4) => Result attendu : Pass
    }

    @ParameterizedTest
    @CsvSource(
        "-2",
        "0",
        "5",
    )
    fun player_len_Test1(nb_player: String) {
        // Convertir nb_player en Int
        val numberOfPlayers = nb_player.toInt()

        val identification = connect.newGame(numberOfPlayers)
        val id = identification.first
        val key = identification.second
        assertThrows<iut.info1.pickomino.exceptions.UnknownIdException> {
            connect.gameState(id, key)
        }        //Test serveur ssi nombre de joueur est cohérent => Result attendu : Pass (ici, le test est un not)
    }
    @Test
    fun roll_len_init_Test() {
        val game = Jeu(4)
        assertEquals(8, game.roll().size)
    }
    @Test
    fun roll_Test(){

    }
}