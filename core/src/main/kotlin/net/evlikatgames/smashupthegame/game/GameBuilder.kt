package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.Player

class GameBuilder {

    private val players = mutableListOf<Player>()

    fun registerPlayer(player: Player) {
        players.add(player)
    }
}