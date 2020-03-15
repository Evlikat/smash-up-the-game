package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleMinionsSelected
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.MinionCardInPlay
import net.evlikatgames.smashupthegame.messaging.DestroyTargetMinion

class NaturalSelection : InstantActionCard<MultipleTargetChoices, MultipleMinionsSelected>() {

    override fun numberOfTargets(ctx: GameContext): Int = 2

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: MultipleMinionsSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return when (targetIndex) {
            0 -> {
                MultipleTargetChoices(ctx.bases.flatMap { baseState ->
                    player.minionsWithMoreEffectivePowerThanAtLeastOneAnotherPlayerMinionOn(baseState)
                })
            }
            1 -> {
                val myMinion = previouslySelectedObjects.selectedMinions.first()
                val base = ctx.baseOfMinion(myMinion.card)
                MultipleTargetChoices(base.minionsInPlay.filter { it.effectivePower < myMinion.effectivePower })
            }
            else -> MultipleTargetChoices(emptyList())
        }
    }

    private fun Player.minionsWithMoreEffectivePowerThanAtLeastOneAnotherPlayerMinionOn(
        baseState: BaseState
    ): List<MinionCardInPlay> {
        val minAnotherPlayerMinionEffectivePower = baseState
            .minionsInPlay
            .filter { it.controller != this }
            .map { it.effectivePower }
            .min() ?: return emptyList()

        return baseState.minionsInPlay
            .filter { it.controller == this && it.effectivePower > minAnotherPlayerMinionEffectivePower }
    }

    override fun play(player: Player, target: MultipleMinionsSelected, ctx: GameContext) {
        ctx.sendCommand(DestroyTargetMinion(this, target.selectedMinions[1]))
    }
}