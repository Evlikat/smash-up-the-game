package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.ReturnMinionToItsOwnerHand
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand

class Collector: MinionCard(basePower = 2) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        val anotherMinionsOnBase = ctx.baseOfMinion(this).minionsInPlay
        val validTargetMinions = anotherMinionsOnBase.filter { it.minion !is Collector && it.effectivePower <= 3 }
        if (validTargetMinions.isEmpty()) {
            return
        }
        val targetMinion = ctx.askPlayerChooseTargetOrDecline(
            player = owner,
            pendingIntention = ReturnMinionToItsOwnerHand(),
            validTargets = validTargetMinions
        ) ?: return

        ctx.sendCommand(ReturnTargetMinionToItsOwnerHand(this, targetMinion))
    }
}