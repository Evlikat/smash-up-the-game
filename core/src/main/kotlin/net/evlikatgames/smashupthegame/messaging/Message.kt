package net.evlikatgames.smashupthegame.messaging

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.game.God

abstract class Message(
    val source: GameObject,
    val target: GameObject
)

class StartTurn(val currentPlayer: Player) : Message(God, currentPlayer)
class EndTurn(val currentPlayer: Player) : Message(God, currentPlayer)