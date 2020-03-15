package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.DestroyMinion
import net.evlikatgames.smashupthegame.messaging.DestroyTargetMinion

class Laseratops : MinionCard(basePower = 4) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        val validTargets = ctx.baseByCard(message.baseCard)
            .minionsInPlay
            .filter { it.effectivePower <= 2 }
            .takeIf { it.isNotEmpty() } ?: return
        val targetMinion = ctx.askPlayerChooseTarget(
            player = owner,
            pendingIntention = DestroyMinion(),
            validTargets = validTargets
        )
        ctx.sendCommand(DestroyTargetMinion(this, targetMinion))
    }
}