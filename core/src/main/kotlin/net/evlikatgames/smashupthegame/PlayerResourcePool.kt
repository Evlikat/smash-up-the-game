package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.MinionCard

sealed class PlayerResource

object PlayAction : PlayerResource()
object PlayMinion : PlayerResource()
class PlayExtraMinionWithRestrictedPower(val maxPower: Int) : PlayerResource()
class PlayFixedExtraMinion(val minionCard: MinionCard) : PlayerResource()

class PlayerResourcePool {
    private val resources = mutableListOf(PlayAction, PlayMinion)

    fun addResource(resource: PlayerResource) {
        resources.add(resource)
    }
}