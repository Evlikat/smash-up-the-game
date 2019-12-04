package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard

class TheCentralBrain : BaseCard(breakPoints = 19, victoryPoints = VictoryPointsDefinition(4, 2, 1)) {

    override fun whenMinionEnters() {
        super.whenMinionEnters()
    }
}