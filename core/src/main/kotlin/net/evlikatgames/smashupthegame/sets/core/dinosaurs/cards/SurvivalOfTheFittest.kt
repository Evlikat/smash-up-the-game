package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleMinionsSelected
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.MinionCardInPlay
import net.evlikatgames.smashupthegame.messaging.DestroyMinions

class SurvivalOfTheFittest : InstantActionCard<MultipleTargetChoices, MultipleMinionsSelected>() {

    override fun numberOfTargets(ctx: GameContext): Int {
        return ctx.bases.count { baseState -> minionsWithTiedMinPowerOn(baseState).size > 1 }
    }

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: MultipleMinionsSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        val previouslySelectedBases = previouslySelectedObjects.selectedMinions.map { ctx.baseOfMinion(it.card) }.toSet()
        return MultipleTargetChoices(
            ctx.bases.flatMap { baseState ->
                if (baseState in previouslySelectedBases) {
                    emptyList()
                } else {
                    minionsWithTiedMinPowerOn(baseState)
                }
            }
        )
    }

    override fun play(player: Player, target: MultipleMinionsSelected, ctx: GameContext) {
        ctx.sendCommand(DestroyMinions(this, target.selectedMinions))
    }

    private fun minionsWithTiedMinPowerOn(baseState: BaseState): List<MinionCardInPlay> {
        val minPower = baseState.minionsInPlay.asSequence().map { it.effectivePower }.min()
        return baseState.minionsInPlay.filter { it.effectivePower == minPower }
    }
}