package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterBaseScores
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand

class Scout: MinionCard(basePower = 3) {

    override fun onBaseScored(message: AfterBaseScores, ctx: GameContext) {
        val minionState = ctx.minionState(this) ?: return
        val returnItselfCommand = ReturnTargetMinionToItsOwnerHand(this, minionState)
        if (ctx.askPlayerConfirm(owner, pendingCommand = returnItselfCommand)) {
            ctx.sendCommand(returnItselfCommand)
        }
    }
}