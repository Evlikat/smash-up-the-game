package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AddPlayerResource
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.RevealTopPlayerDeckCard
import net.evlikatgames.smashupthegame.resource.PlayFixedExtraMinion

class HoverBot : MinionCard(basePower = 3) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        ctx.sendCommand(RevealTopPlayerDeckCard(source = this, targetPlayer = message.player))
        val topCard = ctx.topCardOfPlayerDeck(message.player)
        if (topCard is MinionCard) {
            ctx.sendCommand(AddPlayerResource(this, owner, PlayFixedExtraMinion(topCard)))
        }
    }
}