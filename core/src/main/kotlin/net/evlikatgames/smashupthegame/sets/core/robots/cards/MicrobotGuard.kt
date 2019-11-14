package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.DestroyMinion
import net.evlikatgames.smashupthegame.messaging.DestroyTargetMinion

class MicrobotGuard : MinionCard(basePower = 1, tribes = setOf(MICROBOT)) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        val minionsOnBase = ctx.minionsOnBase(ctx.baseByCard(message.baseCard))
        val (playerMinions, otherMinions) = minionsOnBase.partition { it.controller == message.player }
        val playerMinionNumber = playerMinions.size
        val validTargets = otherMinions.filter { it.effectivePower < playerMinionNumber }

        if (validTargets.isNotEmpty()) {
            val target = ctx.askPlayerChooseTarget(message.player, DestroyMinion(), validTargets)
            ctx.sendCommand(DestroyTargetMinion(this, target))
        }
    }
}