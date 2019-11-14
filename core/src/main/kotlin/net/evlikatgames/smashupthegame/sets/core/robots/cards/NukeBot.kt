package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionDestroyed
import net.evlikatgames.smashupthegame.messaging.DestroyTargetMinion

class NukeBot : MinionCard(basePower = 5) {

    override fun onDestroyed(message: AfterMinionDestroyed, ctx: GameContext) {
        val minions = ctx.minionsOnBase(ctx.baseOfMinion(this))
        minions.forEach { minion ->
            ctx.sendCommand(DestroyTargetMinion(source = this, targetMinion = minion))
        }
    }
}