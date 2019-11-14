package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.resource.PlayExtraMinionWithRestrictedPower

class TheHomeworld : BaseCard(breakPoints = 23, victoryPoints = VictoryPointsDefinition(4, 2, 1)) {

    override fun afterMinionIsPlayed(minion: MinionCard) {
        minion.owner.addResource(PlayExtraMinionWithRestrictedPower(maxPower = 2))
    }
}