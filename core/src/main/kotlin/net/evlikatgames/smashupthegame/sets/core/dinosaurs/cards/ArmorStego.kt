package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.effect.BonusPower
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.ApplyEffectOnMinions
import net.evlikatgames.smashupthegame.messaging.RemoveAllEffectsBySourceFromTargetMinion
import net.evlikatgames.smashupthegame.messaging.StartTurn

class ArmorStego : MinionCard(basePower = 3) {

    override fun atStartOfTheTurn(message: StartTurn, ctx: GameContext) {
        if (message.currentPlayer != owner) {
            ctx.sendCommand(ApplyEffectOnMinions(
                source = this,
                effect = BonusPower(bonusPower = 2),
                minions = listOf(ctx.minionState(this)!!)
            ))
        } else {
            ctx.sendCommand(RemoveAllEffectsBySourceFromTargetMinion(
                source = this,
                targetMinion = ctx.minionState(this)!!
            ))
        }
    }
}