package net.evlikatgames.smashupthegame.effect

import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.CancelEffect
import net.evlikatgames.smashupthegame.messaging.EndTurn
import net.evlikatgames.smashupthegame.messaging.Message

interface Effect : GameObject {
    fun onMessage(message: Message, ctx: GameContext) {}
}

interface OngoingEffect : Effect

interface UntilEndOfTurnEffect : Effect {
    override fun onMessage(message: Message, ctx: GameContext) {
        if (message is EndTurn) {
            ctx.sendCommand(CancelEffect(source = this, effect = this))
        }
    }
}

interface WithBonusPower {
    val bonusPower: Int
}

interface WithBonusBreakPoints {
    val bonusBreakPoints: Int
}

interface TemporaryTribeEffect {
    val tribe: String
}

data class BonusPower(override val bonusPower: Int) : OngoingEffect, WithBonusPower
data class TemporaryTribe(override val tribe: String) : OngoingEffect, TemporaryTribeEffect
data class BonusPowerUntilEndOfTurn(override val bonusPower: Int) : UntilEndOfTurnEffect, WithBonusPower
data class BonusBreakPointUntilEndOfTurn(override val bonusBreakPoints: Int) : UntilEndOfTurnEffect, WithBonusBreakPoints