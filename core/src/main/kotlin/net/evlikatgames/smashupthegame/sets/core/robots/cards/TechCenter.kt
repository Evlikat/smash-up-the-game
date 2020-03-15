package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.*
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.PlayerDrawsCards

class TechCenter : InstantActionCard<MultipleTargetChoices, SingleTargetSelected<BaseState>>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: SingleTargetSelected<BaseState>,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(ctx.bases)
    }

    override fun play(player: Player, target: SingleTargetSelected<BaseState>, ctx: GameContext) {
        val baseCard = target.gameObject.baseCard
        val minionNumberOnBase = ctx.baseByCard(baseCard)
            .minionsInPlay
            .count { it.controller == player }
        ctx.sendCommand(PlayerDrawsCards(source = this, targetPlayer = player, numberOfCards = minionNumberOnBase))
    }
}