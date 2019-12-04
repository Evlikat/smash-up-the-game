package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings

class Tortuga : BaseCard(breakPoints = 21, victoryPoints = VictoryPointsDefinition(4, 3, 2)) {

    override fun afterBaseScores(rankings: Rankings<Player>, ctx: GameContext) {
        val minionsByController = ctx.baseByCard(this)
            .minionsInPlay
            .groupBy { it.controller }

        rankings.runnersUp.forEach { player ->
            val minions = minionsByController[player] ?: emptyList()
            if (minions.isNotEmpty()) {
                // TODO: too complex
            }
        }
    }
}