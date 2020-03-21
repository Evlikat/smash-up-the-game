package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MinionsAndBasesSelected
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class Dinghy : InstantActionCard<MultipleTargetChoices, MinionsAndBasesSelected>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: MinionsAndBasesSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return when (targetIndex) {
            0 -> MultipleTargetChoices(ctx.minionsInPlay())
            1 -> MultipleTargetChoices(ctx.bases - ctx.baseOfMinion(previouslySelectedObjects.selectedMinions[0].card))
            2 -> MultipleTargetChoices(ctx.minionsInPlay() - previouslySelectedObjects.selectedMinions)
            3 -> MultipleTargetChoices(ctx.bases - ctx.baseOfMinion(previouslySelectedObjects.selectedMinions[1].card))
            else -> throw IllegalArgumentException("Two minion and two bases expected")
        }
    }

    override fun play(
        player: Player,
        target: MinionsAndBasesSelected,
        ctx: GameContext
    ) {
        target.selectedMinions.forEachIndexed { index, minion ->
            ctx.sendCommand(MoveTargetMinionToTargetBase(this, minion, target.selectedBases[index]))
        }
    }
}