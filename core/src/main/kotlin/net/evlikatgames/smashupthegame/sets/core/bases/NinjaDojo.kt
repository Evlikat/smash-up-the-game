package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings
import net.evlikatgames.smashupthegame.messaging.DestroyMinion
import net.evlikatgames.smashupthegame.messaging.DestroyTargetMinion

class NinjaDojo : BaseCard(breakPoints = 18, victoryPoints = VictoryPointsDefinition(2, 3, 2)) {

    override fun afterBaseScores(rankings: Rankings<Player>, ctx: GameContext) {
        rankings.winners.forEach { player ->
            val targetMinion = ctx.askPlayerChooseTargetOrDecline(player, DestroyMinion(), ctx.minionsInPlay())
            if (targetMinion != null) {
                ctx.sendCommand(DestroyTargetMinion(this, targetMinion))
            }
        }
    }
}