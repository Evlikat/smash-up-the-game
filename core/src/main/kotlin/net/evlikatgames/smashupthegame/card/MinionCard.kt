package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.effect.CardOngoingEffect
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.*

abstract class MinionCard(
    val basePower: Int,
    val tribes: Set<String> = emptySet()
) : FactionCard() {

    open val cardOngoingEffects: Set<CardOngoingEffect> = emptySet()

    final override fun onMessage(message: Message, ctx: GameContext) {
        when (message) {
            is StartTurn -> {
                atStartOfTheTurn(message, ctx)
            }
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
            is BeforeMinionIsMoved -> {
                if (this == message.minion) {
                    onMovingToAnotherBase(message, ctx)
                } else {
                    onAnotherMinionMovingToAnotherBase(message, ctx)
                }
            }
            is AfterMinionIsMoved -> {
                if (this == message.minion) {
                    onMovedToAnotherBase(message, ctx)
                } else {
                    onAnotherMinionMovedToAnotherBase(message, ctx)
                }
            }
            is BeforeBaseScores -> {
                if (ctx.baseOfMinion(this) == message.targetBaseState) {
                    onBaseScoring(message, ctx)
                } else {
                    onAnotherBaseScoring(message, ctx)
                }
            }
            is AfterBaseScores -> {
                if (ctx.baseOfMinion(this) == message.targetBaseState) {
                    onBaseScored(message, ctx)
                } else {
                    onAnotherBaseScored(message, ctx)
                }
            }
        }
    }

    open fun atStartOfTheTurn(message: StartTurn, ctx: GameContext) {}

    open fun onMovingToAnotherBase(message: BeforeMinionIsMoved, ctx: GameContext) {}
    open fun onMovedToAnotherBase(message: AfterMinionIsMoved, ctx: GameContext) {}

    open fun onAnotherMinionMovingToAnotherBase(message: BeforeMinionIsMoved, ctx: GameContext) {}
    open fun onAnotherMinionMovedToAnotherBase(message: AfterMinionIsMoved, ctx: GameContext) {}

    open fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {}
    open fun onAnotherMinionEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {}

    open fun onDestroying(message: BeforeMinionDestroyed, ctx: GameContext) {}
    open fun onAnotherMinionDestroying(message: BeforeMinionDestroyed, ctx: GameContext) {}

    open fun onDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {}
    open fun onAnotherMinionDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {}

    open fun onBaseScoring(message: BeforeBaseScores, ctx: GameContext) {}
    open fun onAnotherBaseScoring(message: BeforeBaseScores, ctx: GameContext) {}

    open fun onBaseScored(message: AfterBaseScores, ctx: GameContext) {}
    open fun onAnotherBaseScored(message: AfterBaseScores, ctx: GameContext) {}
}