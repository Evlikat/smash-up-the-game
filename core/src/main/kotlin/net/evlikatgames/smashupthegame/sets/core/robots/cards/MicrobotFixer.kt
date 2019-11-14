package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.PlayMinion
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.effect.CardOngoingEffect
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.sets.core.robots.cards.effects.BonusPowerToMicrobots

class MicrobotFixer : MinionCard(basePower = 1, tribes = setOf(MICROBOT)) {

    override val cardOngoingEffects: Set<CardOngoingEffect> = setOf(BonusPowerToMicrobots(this))

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        if (ctx.cardsPlayedThisTurn().none { it is MinionCard }) {
            message.player.addResource(PlayMinion)
        }
    }
}