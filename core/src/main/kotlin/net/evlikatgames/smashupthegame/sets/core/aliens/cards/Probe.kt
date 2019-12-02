package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.TargetPlayerDiscardsTargetCard

class Probe : InstantActionCard() {

    override fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject> {
        return when (targetIndex) {
            0 -> ctx.players
            1 -> {
                val targetPlayer = previouslySelectedObjects.first() as Player
                ctx.playerHand(targetPlayer)
            }
            else -> emptyList()
        }
    }

    override fun play(player: Player, target: List<GameObject>, ctx: GameContext) {
        ctx.sendCommand(TargetPlayerDiscardsTargetCard(
            source = this,
            targetPlayer = target[0] as Player,
            targetCard = target[1] as FactionCard
        ))
    }
}