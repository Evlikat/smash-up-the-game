package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand

class CropCircles : InstantActionCard<MultipleTargetChoices, SingleTargetSelected<BaseState>>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<BaseState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.bases)
    }

    override fun play(player: Player, target: SingleTargetSelected<BaseState>, ctx: GameContext) {
        val targetBase = target.gameObject
        targetBase.minionsInPlay.forEach { minionOnBase ->
            ctx.sendCommand(ReturnTargetMinionToItsOwnerHand(this, minionOnBase))
        }
    }
}