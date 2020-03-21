package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.DestroyMinion

class SaucyWench : MinionCard(basePower = 3) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        val minionsHere = ctx.baseOfMinion(this).minionsInPlay - ctx.minionState(this)!!
        if (minionsHere.isNotEmpty()) {
            ctx.askPlayerChooseTargetOrDecline(owner, DestroyMinion(), minionsHere)
        }
    }
}