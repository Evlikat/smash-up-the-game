package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.ActionCard
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.PlayerDrawsCards

class TechCenter : ActionCard() {

    override fun availableTargets(player: Player, ctx: GameContext): Collection<GameObject> {
        return ctx.bases.map { it.baseCard }
    }

    override fun play(player: Player, target: Collection<GameObject>, ctx: GameContext) {
        val baseCard = (target.first() as BaseCard)
        val minionNumberOnBase = ctx.minionsOnBase(ctx.baseByCard(baseCard) ?: return)
            .count { it.controller == player }
        ctx.sendCommand(PlayerDrawsCards(source = this, targetPlayer = player, numberOfCards = minionNumberOnBase))
    }
}