package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AddPlayerResource
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand
import net.evlikatgames.smashupthegame.resource.PlayMinion

class Abduction : InstantActionCard<MultipleTargetChoices, SingleTargetSelected<MinionState>>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<MinionState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.minionsInPlay())
    }

    override fun play(player: Player, target: SingleTargetSelected<MinionState>, ctx: GameContext) {
        val targetMinion = target.gameObject
        ctx.sendCommand(ReturnTargetMinionToItsOwnerHand(this, targetMinion))
        ctx.sendCommand(AddPlayerResource(this, owner, PlayMinion))
    }
}