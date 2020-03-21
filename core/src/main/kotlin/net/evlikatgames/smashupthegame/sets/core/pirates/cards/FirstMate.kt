package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterBaseScores
import net.evlikatgames.smashupthegame.messaging.MoveMinionToAnotherBase
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class FirstMate : MinionCard(basePower = 2) {

    override fun onBaseScored(message: AfterBaseScores, ctx: GameContext) {
        val targetBase = ctx.askPlayerChooseTargetOrDecline(
            player = owner,
            pendingIntention = MoveMinionToAnotherBase(),
            validTargets = ctx.bases
        )
        if (targetBase != null) {
            ctx.sendCommand(MoveTargetMinionToTargetBase(
                source = this,
                targetMinion = ctx.minionState(this)!!,
                targetBase = targetBase
            ))
        }
    }
}