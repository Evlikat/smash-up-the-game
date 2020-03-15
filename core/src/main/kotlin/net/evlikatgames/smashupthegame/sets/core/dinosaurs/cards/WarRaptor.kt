package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.effect.BonusPower
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.*

class WarRaptor : MinionCard(basePower = 2) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        recalculateBonusPowerEffect(ctx.baseByCard(message.baseCard), ctx)
    }

    override fun onAnotherMinionEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        applyEffectForWarRaptor(ctx, message.minion.card)
    }

    override fun onAnotherMinionDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {
        applyEffectForWarRaptor(ctx, message.minion.card)
    }

    override fun onAnotherMinionMovedToAnotherBase(message: AfterMinionIsMoved, ctx: GameContext) {
        applyEffectForWarRaptor(ctx, message.minion.card)
    }

    override fun onMovedToAnotherBase(message: AfterMinionIsMoved, ctx: GameContext) {
        applyEffectForWarRaptor(ctx)
    }

    private fun applyEffectForWarRaptor(ctx: GameContext, minion: MinionCard? = null) {
        val myBase = ctx.baseOfMinion(this)
        if (minion == null || (minion is WarRaptor && myBase == ctx.baseOfMinion(minion))) {
            recalculateBonusPowerEffect(myBase, ctx)
        }
    }

    private fun recalculateBonusPowerEffect(myBase: BaseState, ctx: GameContext) {
        val warRaptorsOnThisBase = myBase
            .minionsInPlay
            .count { it.card is WarRaptor }

        val minionState = ctx.minionState(this) ?: return
        ctx.sendCommand(
            RemoveAllEffectsBySourceFromTargetMinion(
                source = this,
                targetMinion = minionState
            )
        )
        ctx.sendCommand(
            ApplyEffectOnTargetMinion(
                source = this,
                effect = BonusPower(bonusPower = warRaptorsOnThisBase),
                targetMinion = minionState
            )
        )
    }
}