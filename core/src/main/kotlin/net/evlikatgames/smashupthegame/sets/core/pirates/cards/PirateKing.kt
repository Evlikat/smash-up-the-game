package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.BeforeBaseScores
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class PirateKing : MinionCard(basePower = 5) {

    override fun onAnotherBaseScoring(message: BeforeBaseScores, ctx: GameContext) {
        val moveToScoringBase = MoveTargetMinionToTargetBase(
            source = this,
            targetMinion = ctx.minionState(this)!!,
            targetBase = message.targetBaseState
        )
        if (ctx.askPlayerConfirm(
                player = owner,
                pendingCommand = moveToScoringBase
            )) {
            ctx.sendCommand(moveToScoringBase)
        }
    }
}