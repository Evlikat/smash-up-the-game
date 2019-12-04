package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.Rankings
import net.evlikatgames.smashupthegame.game.Scores

abstract class BaseCard(
    val breakPoints: Int,
    val victoryPoints: VictoryPointsDefinition
) : Card() {

    open fun whenMinionEnters() {

    }

    open fun afterMinionIsPlayed(minion: MinionCard, ctx: GameContext) {

    }

    open fun afterMinionDestroyed(minion: MinionCard, ctx: GameContext) {

    }

    open fun bonusScore(rankings: Rankings<Player>, ctx: GameContext): Scores<Player> = Scores()

    open fun whenBaseScores() {

    }

    open fun beforeBaseScores() {

    }

    open fun afterBaseScores(rankings: Rankings<Player>, ctx: GameContext) {

    }

    open fun atStartOfPlayerTurn() {

    }
}