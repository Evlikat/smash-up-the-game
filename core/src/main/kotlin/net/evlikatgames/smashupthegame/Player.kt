package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.game.GameObject

class Player(
    val factions: PlayerFactions,
    private val playerController: PlayerController
): GameObject {


    fun addResource(resource: PlayerResource) {
        resourcePool.addResource(resource)
        playerController.publishEvent(PlayerResourceAdded(resource))
    }

    var resourcePool = PlayerResourcePool()
}