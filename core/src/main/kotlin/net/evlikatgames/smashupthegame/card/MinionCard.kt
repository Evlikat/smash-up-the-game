package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.effect.CardOngoingEffect
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionDestroyed
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.BeforeMinionDestroyed
import net.evlikatgames.smashupthegame.messaging.Message

abstract class MinionCard(
    val basePower: Int,
    val tribes: Set<String> = emptySet()
) : FactionCard() {

    open val cardOngoingEffects: Set<CardOngoingEffect> = emptySet()

    final override fun onMessage(message: Message, ctx: GameContext) {
        when (message) {
            is AfterMinionIsPlayed -> {
                if (this == message.minion) {
                    onEntersPlay(message, ctx)
                } else {
                    onAnotherMinionEntersPlay(message, ctx)
                }
            }
            is BeforeMinionDestroyed -> {
                if (this == message.minion) {
                    onDestroying(message, ctx)
                } else {
                    onAnotherMinionDestroying(message, ctx)
                }
            }
            is AfterMinionDestroyed -> {
                if (this == message.minion) {
                    onDestroyed(message, ctx)
                } else {
                    onAnotherMinionDestroyed(message, ctx)
                }
            }
        }
    }

    open fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {}
    open fun onAnotherMinionEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {}

    open fun onDestroying(message: BeforeMinionDestroyed, ctx: GameContext) {}
    open fun onAnotherMinionDestroying(message: BeforeMinionDestroyed, ctx: GameContext) {}

    open fun onDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {}
    open fun onAnotherMinionDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {}
}