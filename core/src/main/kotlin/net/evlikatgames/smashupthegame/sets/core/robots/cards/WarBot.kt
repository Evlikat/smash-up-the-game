package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.BeforeMinionDestroyed
import net.evlikatgames.smashupthegame.messaging.InterruptException

class WarBot : MinionCard(basePower = 4) {

    override fun onDestroying(message: BeforeMinionDestroyed, ctx: GameContext) {
        throw InterruptException()
    }
}
