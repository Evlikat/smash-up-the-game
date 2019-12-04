package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings
import net.evlikatgames.smashupthegame.game.Scores

class RhodesPlazaMall : BaseCard(breakPoints = 24, victoryPoints = VictoryPointsDefinition(0, 0, 0)) {

    override fun bonusScore(rankings: Rankings<Player>, ctx: GameContext): Scores<Player> {
        val minionCountByController = ctx.baseByCard(this)
            .minionsInPlay
            .groupingBy { it.controller }
            .eachCount()
        return Scores(minionCountByController)
    }
}