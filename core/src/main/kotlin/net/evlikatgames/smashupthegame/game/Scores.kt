package net.evlikatgames.smashupthegame.game

class Scores<P : Any>(private val scores: Map<P, Int> = emptyMap()) : Map<P, Int> by scores {

    operator fun plus(otherScores: Scores<P>): Scores<P> {
        val newScores = (keys + otherScores.keys).associateWith { player ->
            (scores[player] ?: 0) + (otherScores[player] ?: 0)
        }
        return Scores(newScores)
    }
}