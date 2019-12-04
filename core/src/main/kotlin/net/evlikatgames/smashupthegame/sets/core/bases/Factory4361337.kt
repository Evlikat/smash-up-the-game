package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings
import net.evlikatgames.smashupthegame.game.Scores

class Factory4361337 : BaseCard(breakPoints = 25, victoryPoints = VictoryPointsDefinition(2, 2, 1)) {

    override fun bonusScore(rankings: Rankings<Player>, ctx: GameContext): Scores<Player> {
        return Scores(
            rankings.winners.associateWith { winner ->
                (rankings.currentPowerState[winner] ?: 0) / 5
            }
        )
    }
}