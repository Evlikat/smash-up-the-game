package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AddPlayerResource
import net.evlikatgames.smashupthegame.resource.PlayExtraMinionWithRestrictedPower

class TheHomeworld : BaseCard(breakPoints = 23, victoryPoints = VictoryPointsDefinition(4, 2, 1)) {

    override fun afterMinionIsPlayed(minion: MinionCard, ctx: GameContext) {
        ctx.sendCommand(AddPlayerResource(this, minion.owner, PlayExtraMinionWithRestrictedPower(maxPower = 2)))
    }
}