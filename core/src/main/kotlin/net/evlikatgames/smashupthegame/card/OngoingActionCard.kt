package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.*

// TODO: like minion message handling
abstract class OngoingActionCard<AT : ActionTargetChoice, ST : SelectedTarget> : ActionCard<AT, ST>() {

    override fun onMessage(message: Message, ctx: GameContext) {
        when (message) {
            is BeforeOngoingActionIsPlayed -> {

            }
            is AfterOngoingActionIsPlayed -> {
                if (this == message.action) {
                    onEntersPlay(message, ctx)
                } else {
                    onAnotherOngoingActionEntersPlay(message, ctx)
                }
            }
            is BeforeOngoingActionDestroyed -> {
                if (this == message.action) {
                    onDestroying(message, ctx)
                } else {
                    onAnotherOngoingActionDestroying(message, ctx)
                }
            }
            is AfterOngoingActionDestroyed -> {
                if (this == message.action) {
                    onDestroyed(message, ctx)
                } else {
                    onAnotherOngoingActionDestroyed(message, ctx)
                }
            }
        }
    }

    open fun onEntersPlay(message: AfterOngoingActionIsPlayed, ctx: GameContext) {}
    open fun onAnotherOngoingActionEntersPlay(message: AfterOngoingActionIsPlayed, ctx: GameContext) {}

    open fun onDestroying(message: BeforeOngoingActionDestroyed, ctx: GameContext) {}
    open fun onAnotherOngoingActionDestroying(message: BeforeOngoingActionDestroyed, ctx: GameContext) {}

    open fun onDestroyed(message: AfterOngoingActionDestroyed, ctx: GameContext) {}
    open fun onAnotherOngoingActionDestroyed(message: AfterOngoingActionDestroyed, ctx: GameContext) {}
}