package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleMinionsSelected
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.DestroyTargetMinion
import java.lang.IllegalArgumentException

class Cannon : InstantActionCard<MultipleTargetChoices, MultipleMinionsSelected>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: MultipleMinionsSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return when (targetIndex) {
            0 -> MultipleTargetChoices(ctx.minionsInPlay().filter { it.effectivePower <= 2 })
            1 -> MultipleTargetChoices(ctx.minionsInPlay().filter { it.effectivePower <= 2 } - previouslySelectedObjects.selectedMinions)
            else -> throw IllegalArgumentException("Up to two targets expected")
        }
    }

    override fun play(
        player: Player,
        target: MultipleMinionsSelected,
        ctx: GameContext
    ) {
        target.selectedMinions.forEach { ctx.sendCommand(DestroyTargetMinion(this, it)) }
    }
}