package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.card.PlayerAndCardInHandSelected
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.TargetPlayerDiscardsTargetCard

class Probe : InstantActionCard<MultipleTargetChoices, PlayerAndCardInHandSelected>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: PlayerAndCardInHandSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return when (targetIndex) {
            0 -> MultipleTargetChoices(ctx.players)
            1 -> {
                val targetPlayer = previouslySelectedObjects.player
                MultipleTargetChoices(ctx.playerHand(targetPlayer))
            }
            else -> MultipleTargetChoices(emptyList())
        }
    }

    override fun play(player: Player, target: PlayerAndCardInHandSelected, ctx: GameContext) {
        ctx.sendCommand(TargetPlayerDiscardsTargetCard(
            source = this,
            targetPlayer = target.player,
            targetCard = target.card
        ))
    }
}