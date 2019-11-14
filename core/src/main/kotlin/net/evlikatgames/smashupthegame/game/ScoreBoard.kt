package net.evlikatgames.smashupthegame.game

class ScoreBoard<P : Any>(players: List<P>, private val winPoints: Int) {

    val currentScores: Map<P, Int> get() = scores

    private val scores = players.associate { it to 0 }.toMutableMap()

    fun gainVictoryPoints(player: P, score: Int): P? {
        addVictoryPoints(player, score)
        return winner
    }

    fun gainVictoryPoints(scores: Map<P, Int>): P? {
        scores.forEach { player, score -> addVictoryPoints(player, score) }
        return winner
    }

    private fun addVictoryPoints(player: P, score: Int) {
        val currentScore = scores[player] ?: 0
        scores[player] = currentScore + score
    }

    private val winner: P?
        get() {
            val winnerCandidates: MutableList<P> = mutableListOf()
            var winnerCandidatePoints = winPoints
            for ((player, playerScore) in scores) {
                when {
                    playerScore == winnerCandidatePoints -> winnerCandidates.add(player)
                    playerScore > winnerCandidatePoints -> {
                        winnerCandidates.clear()
                        winnerCandidates.add(player)
                        winnerCandidatePoints = playerScore
                    }
                }
            }
            return winnerCandidates.singleOrNull()
        }
}