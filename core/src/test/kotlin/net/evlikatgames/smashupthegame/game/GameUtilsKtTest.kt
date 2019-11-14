package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GameUtilsKtTest {

    private val victoryPointsDefinition = VictoryPointsDefinition(4, 3, 2)

    @Test
    fun `single player score`() {
        test(powerMap = mapOf("A" to 5), expectedPoints = mapOf("A" to 4))
    }

    @Test
    fun `two players score`() {
        test(powerMap = mapOf("A" to 5, "B" to 1), expectedPoints = mapOf("A" to 4, "B" to 3))
    }

    @Test
    fun `two players zero power`() {
        test(powerMap = mapOf("A" to 5, "B" to 0), expectedPoints = mapOf("A" to 4, "B" to 3))
    }

    @Test
    fun `two players share first place`() {
        test(powerMap = mapOf("A" to 5, "B" to 5, "C" to 1), expectedPoints = mapOf("A" to 4, "B" to 4, "C" to 2))
    }

    private fun test(powerMap: Map<String, Int>, expectedPoints: Map<String, Int>) {
        val result = calculateScore(victoryPointsDefinition, powerMap)
        assertThat(result).isEqualTo(expectedPoints)
    }
}