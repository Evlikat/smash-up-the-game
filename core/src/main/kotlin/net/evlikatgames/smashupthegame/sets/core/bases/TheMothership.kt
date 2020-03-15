package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings
import net.evlikatgames.smashupthegame.messaging.ReturnMinionToItsOwnerHand
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand

class TheMothership : BaseCard(breakPoints = 20, victoryPoints = VictoryPointsDefinition(4, 2, 1)) {

    override fun afterBaseScores(rankings: Rankings<Player>, ctx: GameContext) {

        val minionsByController = ctx.baseByCard(this)
            .minionsInPlay
            .filter { it.card.basePower <= 3 }
            .groupBy { it.controller }

        rankings.winners.forEach { player ->
            minionsByController[player]?.takeIf { it.isNotEmpty() }?.let { controlledMinions ->
                val targetMinionToReturn = ctx.askPlayerChooseTargetOrDecline(player, ReturnMinionToItsOwnerHand(), controlledMinions)
                if (targetMinionToReturn != null) {
                    ctx.sendCommand(ReturnTargetMinionToItsOwnerHand(this, targetMinionToReturn))
                }
            }
        }
    }
}