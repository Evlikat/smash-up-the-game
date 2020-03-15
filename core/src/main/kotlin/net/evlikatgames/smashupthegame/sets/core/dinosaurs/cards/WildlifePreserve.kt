package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseOngoingActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.OngoingActionCard
import net.evlikatgames.smashupthegame.card.SingleTargetSelected
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext

class WildlifePreserve : BaseOngoingActionCard<MultipleTargetChoices>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<BaseState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.bases)
    }
}