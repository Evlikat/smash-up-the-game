package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.ActionCard
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.PlayerDrawsCards

class TechCenter : ActionCard() {

    override fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject> {
        return ctx.bases.map { it.baseCard }
    }

    override fun play(player: Player, target: List<GameObject>, ctx: GameContext) {
        val baseCard = (target.first() as BaseCard)
        val minionNumberOnBase = ctx.baseByCard(baseCard)
            .minionsInPlay
            .count { it.controller == player }
        ctx.sendCommand(PlayerDrawsCards(source = this, targetPlayer = player, numberOfCards = minionNumberOnBase))
    }
}