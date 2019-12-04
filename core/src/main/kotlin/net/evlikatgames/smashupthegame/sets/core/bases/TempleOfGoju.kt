package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings
import net.evlikatgames.smashupthegame.messaging.PlaceMinionCardOnTheBottomOfItsOwnerDeck
import net.evlikatgames.smashupthegame.messaging.PlaceTargetMinionOnTheBottomOfItsOwnerDeck

class TempleOfGoju : BaseCard(breakPoints = 18, victoryPoints = VictoryPointsDefinition(2, 3, 2)) {

    override fun afterBaseScores(rankings: Rankings<Player>, ctx: GameContext) {
        val strongestMinionsByController = ctx.baseByCard(this)
            .minionsInPlay
            .groupBy { it.controller }
            .mapValues { (_, minions) ->
                minions.map { it.effectivePower }.max()?.let { maxPower ->
                    minions.filter { it.effectivePower == maxPower }
                } ?: emptyList()
            }
        strongestMinionsByController.forEach { player, minions ->
            if (minions.isNotEmpty()) {
                val minionToPlace = if (minions.size > 1) {
                    ctx.askPlayerChooseTarget(player, PlaceMinionCardOnTheBottomOfItsOwnerDeck(), minions)
                } else minions.first()
                ctx.sendCommand(PlaceTargetMinionOnTheBottomOfItsOwnerDeck(this, minionToPlace))
            }
        }
    }
}