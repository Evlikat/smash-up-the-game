package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.ReturnMinionToItsOwnerHand
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand

class SupremeOverlord: MinionCard(basePower = 5) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        val targetMinion = ctx.askPlayerChooseTarget(
            player = owner,
            pendingIntention = ReturnMinionToItsOwnerHand(),
            validTargets = ctx.minionsInPlay()
        )
        ctx.sendCommand(ReturnTargetMinionToItsOwnerHand(source = this, targetMinion = targetMinion))
    }
}