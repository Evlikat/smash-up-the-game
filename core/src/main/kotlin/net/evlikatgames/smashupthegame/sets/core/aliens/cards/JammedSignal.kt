package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.OngoingActionCard
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext

class JammedSignal : OngoingActionCard<MultipleTargetChoices, SingleTargetSelected<BaseState>>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<BaseState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.bases)
    }

    override fun play(player: Player, target: SingleTargetSelected<BaseState>, ctx: GameContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}