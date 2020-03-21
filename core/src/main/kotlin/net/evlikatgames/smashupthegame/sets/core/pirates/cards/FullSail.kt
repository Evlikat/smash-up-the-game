package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MinionsAndBasesSelected
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class FullSail : InstantActionCard<MultipleTargetChoices, MinionsAndBasesSelected>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: MinionsAndBasesSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return if (targetIndex % 2 == 0) {
            MultipleTargetChoices(ctx.minionsInPlay() - previouslySelectedObjects.selectedMinions)
        } else {
            MultipleTargetChoices(ctx.bases - ctx.baseOfMinion(previouslySelectedObjects.selectedMinions[targetIndex / 2].card))
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