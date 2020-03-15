package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.effect.BonusBreakPointUntilEndOfTurn
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.ApplyEffectOnTargetBase

class Rampage : InstantActionCard<MultipleTargetChoices, SingleTargetSelected<MinionState>>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<MinionState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.minionsInPlay().filter { it.controller == player })
    }

    override fun play(player: Player, target: SingleTargetSelected<MinionState>, ctx: GameContext) {
        ctx.sendCommand(
            ApplyEffectOnTargetBase(
                source = this,
                effect = BonusBreakPointUntilEndOfTurn(bonusBreakPoints = -target.gameObject.effectivePower),
                targetBase = ctx.baseOfMinion(target.gameObject.card)
            )
        )
    }
}