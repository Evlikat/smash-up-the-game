package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.consideredAs
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionDestroyed
import net.evlikatgames.smashupthegame.messaging.PlayerDrawsCards

class MicrobotArchive : MinionCard(basePower = 1, tribes = setOf(MICROBOT)) {

    override fun onDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {
        ctx.sendCommand(PlayerDrawsCards(this, owner, numberOfCards = 1))
    }

    override fun onAnotherMinionDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {
        if (message.minion consideredAs MICROBOT
            && message.minion.controller != owner) {
            ctx.sendCommand(PlayerDrawsCards(this, owner, numberOfCards = 1))
        }
    }
}