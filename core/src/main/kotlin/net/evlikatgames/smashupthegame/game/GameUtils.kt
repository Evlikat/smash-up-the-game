package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition

fun <P: Any> calculateScore(
    victoryPointsDefinition: VictoryPointsDefinition,
    currentPowerState: Map<P, Int>
): Map<P, Int> {

    val table = currentPowerState.values.sortedDescending()
    return currentPowerState.mapValues { (_, power) ->
        victoryPointsDefinition[(table.indexOf(power) + 1)]
    }
}

fun evaluateEffectivePowerByPlayers(chosenBaseState: BaseState): Map<Player, Int> {
    return chosenBaseState.minionsInPlay
        .groupBy { it.controller }
        .mapValues { (_, v) -> v.sumBy { it.effectivePower } }
}