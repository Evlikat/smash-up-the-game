package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.PlayMinion
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.consideredAs
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.ShuffleCardToItsOwnerDeck
import net.evlikatgames.smashupthegame.messaging.ShuffleMinion

class MicrobotReclaimer : MinionCard(basePower = 1, tribes = setOf(MICROBOT)) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        if (ctx.cardsPlayedThisTurn().none { it is MinionCard }) {
            message.player.addResource(PlayMinion)
        }
        val targets = ctx.askPlayerChooseSomeTargets(
            player = message.player,
            pendingAction = ShuffleMinion(),
            validTargets = ctx
                .cardsInPlayerDiscardPile(message.player)
                .filter { it is MinionCard && it consideredAs MICROBOT }
        )
        targets.forEach {
            ctx.sendCommand(ShuffleCardToItsOwnerDeck(this, it))
        }
    }
}