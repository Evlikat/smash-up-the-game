package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.DestroyMinions

class Broadside : InstantActionCard<MultipleTargetChoices, SingleTargetSelected<BaseState>>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<BaseState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.bases.filter { base ->
            val anyMyMinionHere = base.minionsInPlay.any { it.controller == owner }
            val anyAnotherPlayerMinionsHere = base.minionsInPlay.any { it.controller != owner && it.effectivePower <= 2 }
            anyMyMinionHere && anyAnotherPlayerMinionsHere
        })
    }

    override fun play(
        player: Player,
        target: SingleTargetSelected<BaseState>,
        ctx: GameContext
    ) {
        val minionsToDestroy = target.gameObject.minionsInPlay
            .filter { it.controller != owner && it.effectivePower <= 2 }
        ctx.sendCommand(DestroyMinions(this, minionsToDestroy))
    }
}