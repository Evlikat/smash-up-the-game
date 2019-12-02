package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AddPlayerResource
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.resource.PlayExtraMinionWithRestrictedPower

class ZapBot : MinionCard(basePower = 2) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        ctx.sendCommand(AddPlayerResource(this, owner, PlayExtraMinionWithRestrictedPower(maxPower = 2)))
    }
}