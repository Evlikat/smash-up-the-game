package net.evlikatgames.smashupthegame.sets.core.bases

import net.evlikatgames.smashupthegame.VictoryPointsDefinition
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.PlaceTargetMinionCardOnTheBottomOfItsOwnerDeck

class TarPits : BaseCard(breakPoints = 16, victoryPoints = VictoryPointsDefinition(4, 3, 2)) {

    override fun afterMinionDestroyed(minion: MinionCard, ctx: GameContext) {
        ctx.sendCommand(PlaceTargetMinionCardOnTheBottomOfItsOwnerDeck(this, minion))
    }
}