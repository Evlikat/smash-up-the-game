package net.evlikatgames.smashupthegame.game

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ScoreBoardTest {

    private val scoreBoard = ScoreBoard(listOf("A", "B", "C"), 15)

    @Test
    fun `should gain vp with no winner`() {
        val winner = scoreBoard.gainVictoryPoints("A", 1)

        assertThat(scoreBoard.currentScores).isEqualTo(
            mapOf("A" to 1, "B" to 0, "C" to 0)
        )
        assertThat(winner).isNull()
    }

    @Test
    fun `should gain vp and sum up`() {
        assertThat(scoreBoard.gainVictoryPoints("A", 1)).isNull()
        assertThat(scoreBoard.gainVictoryPoints("A", 2)).isNull()
        assertThat(scoreBoard.gainVictoryPoints("B", 3)).isNull()
        assertThat(scoreBoard.gainVictoryPoints("C", 2)).isNull()

        assertThat(scoreBoard.currentScores).isEqualTo(
            mapOf("A" to 3, "B" to 3, "C" to 2)
        )
    }

    @Test
    fun `should gain vp and win`() {
        val winner = scoreBoard.gainVictoryPoints("A", 15)

        assertThat(scoreBoard.currentScores).isEqualTo(
            mapOf("A" to 15, "B" to 0, "C" to 0)
        )
        assertThat(winner).isEqualTo("A")
    }

    @Test
    fun `should gain vps and win`() {
        val winner = scoreBoard.gainVictoryPoints(Scores(mapOf(
            "A" to 16,
            "B" to 15
        )))

        assertThat(scoreBoard.currentScores).isEqualTo(
            mapOf("A" to 16, "B" to 15, "C" to 0)
        )
        assertThat(winner).isEqualTo("A")
    }

    @Test
    fun `should gain vps and no winner`() {
        val winner = scoreBoard.gainVictoryPoints(Scores(mapOf(
            "A" to 15,
            "B" to 15
        )))

        assertThat(scoreBoard.currentScores).isEqualTo(
            mapOf("A" to 15, "B" to 15, "C" to 0)
        )
        assertThat(winner).isNull()
    }
}