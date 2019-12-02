package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.AddPlayerResource
import net.evlikatgames.smashupthegame.messaging.DestroyTargetOngoingAction
import net.evlikatgames.smashupthegame.messaging.SwapBases
import net.evlikatgames.smashupthegame.resource.PlayExtraMinionOnFixedBase

class Terraforming : InstantActionCard() {

    override fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject> {
        return when (targetIndex) {
            0 -> ctx.baseDeckCards
            1 -> ctx.bases
            else -> emptyList()
        }
    }

    override fun play(player: Player, target: List<GameObject>, ctx: GameContext) {
        val targetBaseCard = target[0] as BaseCard
        val targetBase = target[1] as BaseState

        targetBase.actionsInPlay.forEach { ongoingActionState ->
            ctx.sendCommand(DestroyTargetOngoingAction(this, ongoingActionState))
        }

        ctx.sendCommand(SwapBases(this, targetBase, targetBaseCard))

        val newBaseState = ctx.baseByCard(targetBaseCard)
        ctx.sendCommand(AddPlayerResource(this, owner, PlayExtraMinionOnFixedBase(newBaseState)))
    }
}