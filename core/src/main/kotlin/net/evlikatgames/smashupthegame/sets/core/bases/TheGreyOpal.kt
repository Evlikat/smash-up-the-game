package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings
import net.evlikatgames.smashupthegame.messaging.MoveMinionToAnotherBase
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class TheGreyOpal : BaseCard(breakPoints = 17, victoryPoints = VictoryPointsDefinition(3, 1, 1)) {

    override fun afterBaseScores(rankings: Rankings<Player>, ctx: GameContext) {
        val minionsByController = ctx.baseByCard(this)
            .minionsInPlay
            .groupBy { it.controller }

        val winners = rankings.winners
        minionsByController.forEach { player, minions ->
            if (player !in winners) {
                val minionToMove = ctx.askPlayerChooseTargetOrDecline(player, MoveMinionToAnotherBase(), minions)
                if (minionToMove != null) {
                    val baseToMove = ctx.askPlayerChooseTarget(player, MoveMinionToAnotherBase(), ctx.bases.toList())
                    ctx.sendCommand(MoveTargetMinionToTargetBase(this, minionToMove, baseToMove))
                }
            }
        }
    }
}