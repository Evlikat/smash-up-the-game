package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.Message

abstract class FactionCard : Card() {

    lateinit var owner: Player

    open fun onMessage(message: Message, ctx: GameContext) {}
}