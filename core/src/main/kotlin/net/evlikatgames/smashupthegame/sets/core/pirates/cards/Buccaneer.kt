package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.BeforeMinionDestroyed
import net.evlikatgames.smashupthegame.messaging.InterruptException
import net.evlikatgames.smashupthegame.messaging.MoveMinionToAnotherBase
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class Buccaneer: MinionCard(basePower = 4) {

    override fun onDestroying(message: BeforeMinionDestroyed, ctx: GameContext) {
        val targetBase = ctx.askPlayerChooseTarget(
            player = owner,
            pendingIntention = MoveMinionToAnotherBase(),
            validTargets = ctx.bases - ctx.baseOfMinion(this)
        )
        ctx.sendCommand(MoveTargetMinionToTargetBase(this, ctx.minionState(this)!!, targetBase))
        throw InterruptException()
    }
}