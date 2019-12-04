package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition

fun <P : Any> calculateScore(
    victoryPointsDefinition: VictoryPointsDefinition,
    rankings: Rankings<P>
): Scores<P> {
    return Scores(
        rankings.mapValues { (_, rank) ->
            victoryPointsDefinition[rank]
        }
    )
}

fun evaluateEffectivePowerByPlayers(chosenBaseState: BaseState): Map<Player, Int> {
    return chosenBaseState.minionsInPlay
        .groupBy { it.controller }
        .mapValues { (_, v) -> v.sumBy { it.effectivePower } }
}