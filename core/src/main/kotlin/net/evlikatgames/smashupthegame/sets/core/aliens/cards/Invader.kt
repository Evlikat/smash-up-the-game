package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AfterMinionIsPlayed
import net.evlikatgames.smashupthegame.messaging.GainVictoryPoints

class Invader: MinionCard(basePower = 4) {

    override fun onEntersPlay(message: AfterMinionIsPlayed, ctx: GameContext) {
        ctx.sendCommand(GainVictoryPoints(this, points = 1))
    }
}