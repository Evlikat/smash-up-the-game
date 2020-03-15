package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseAndCardInBaseDeckSelected
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AddPlayerResource
import net.evlikatgames.smashupthegame.messaging.DestroyTargetOngoingAction
import net.evlikatgames.smashupthegame.messaging.SwapBases
import net.evlikatgames.smashupthegame.resource.PlayExtraMinionOnFixedBase

class Terraforming : InstantActionCard<MultipleTargetChoices, BaseAndCardInBaseDeckSelected>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: BaseAndCardInBaseDeckSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(
            when (targetIndex) {
                0 -> ctx.baseDeckCards
                1 -> ctx.bases
                else -> emptyList()
            }
        )
    }

    override fun play(player: Player, target: BaseAndCardInBaseDeckSelected, ctx: GameContext) {
        val targetBaseCard = target.baseCard
        val targetBase = target.baseState

        targetBase.actionsInPlay.forEach { ongoingActionState ->
            ctx.sendCommand(DestroyTargetOngoingAction(this, ongoingActionState))
        }

        ctx.sendCommand(SwapBases(this, targetBase, targetBaseCard))

        val newBaseState = ctx.baseByCard(targetBaseCard)
        ctx.sendCommand(AddPlayerResource(this, owner, PlayExtraMinionOnFixedBase(newBaseState)))
    }
}