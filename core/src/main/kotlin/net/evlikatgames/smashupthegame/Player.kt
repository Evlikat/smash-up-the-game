package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.resource.PlayerResource
import net.evlikatgames.smashupthegame.resource.PlayerResourcePool

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