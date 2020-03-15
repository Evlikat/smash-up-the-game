package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.MinionOngoingActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.effect.BonusPower
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterOngoingActionDestroyed
import net.evlikatgames.smashupthegame.messaging.AfterOngoingActionIsPlayed
import net.evlikatgames.smashupthegame.messaging.ApplyEffectOnTargetMinion
import net.evlikatgames.smashupthegame.messaging.RemoveAllEffectsBySourceFromTargetMinion

class Upgrade : MinionOngoingActionCard<MultipleTargetChoices>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<MinionState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.minionsInPlay())
    }

    override fun onEntersPlay(message: AfterOngoingActionIsPlayed, ctx: GameContext) {
        ctx.sendCommand(ApplyEffectOnTargetMinion(
            source = this,
            effect = BonusPower(bonusPower = 2),
            targetMinion = message.target as MinionState
        ))
    }

    override fun onDestroyed(message: AfterOngoingActionDestroyed, ctx: GameContext) {
        val minion = ctx.minionOfOngoingEffect(this)
        ctx.sendCommand(
            RemoveAllEffectsBySourceFromTargetMinion(
                source = message.action,
                targetMinion = minion
            )
        )
    }
}