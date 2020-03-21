package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.DestroyMinions

class Powderkeg : InstantActionCard<MultipleTargetChoices, SingleTargetSelected<MinionState>>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<MinionState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.minionsInPlay().filter { it.controller == owner })
    }

    override fun play(
        player: Player,
        target: SingleTargetSelected<MinionState>,
        ctx: GameContext
    ) {
        val minionBase = ctx.baseOfMinion(target.gameObject.card)
        ctx.sendCommand(DestroyMinions(
            source = this,
            minions = minionBase.minionsInPlay.filter { it.effectivePower <= target.gameObject.effectivePower }
        ))
    }
}