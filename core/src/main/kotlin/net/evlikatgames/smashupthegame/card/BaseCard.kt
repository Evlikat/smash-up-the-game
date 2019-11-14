package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.VictoryPointsDefinition

abstract class BaseCard(
    val breakPoints: Int,
    val victoryPoints: VictoryPointsDefinition
) : Card() {

    open fun whenMinionEnters() {

    }

    open fun afterMinionIsPlayed(minion: MinionCard) {

    }

    open fun afterMinionDestroyed() {

    }

    open fun whenBaseScores() {

    }

    open fun beforeBaseScores() {

    }

    open fun afterBaseScores() {

    }

    open fun atStartOfPlayerTurn() {

    }
}