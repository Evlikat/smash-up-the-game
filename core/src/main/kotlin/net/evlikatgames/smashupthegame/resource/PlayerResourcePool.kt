package net.evlikatgames.smashupthegame.resource

import net.evlikatgames.smashupthegame.card.ActionCard
import net.evlikatgames.smashupthegame.card.MinionCard

class PlayerResourcePool {
    private val minionResources = mutableListOf<PlayerMinionResource>(PlayMinion)
    private val actionResources = mutableListOf<PlayerActionResource>(PlayAction)

    fun addResource(resource: PlayerResource) {
        when (resource) {
            is PlayerMinionResource -> minionResources.add(resource)
            is PlayerActionResource -> actionResources.add(resource)
        }
    }

    fun consumeResource(resource: PlayerResource) {
        when (resource) {
            is PlayerMinionResource -> minionResources.remove(resource)
            is PlayerActionResource -> actionResources.remove(resource)
        }
    }

    /**
     * **WARNING!**
     *
     * This method is unstable and requires modification every time a new minion resource appears.
     */
    fun selectResourceFor(minionCard: MinionCard): List<PlayerMinionResource> {
        return (
            minionResources.filterIsInstance<PlayFixedExtraMinion>().find { minionCard == it.minionCard }
                ?: minionResources.filterIsInstance<PlayExtraMinionWithRestrictedPower>().find { minionCard.basePower <= it.maxPower }
                ?: minionResources.filterIsInstance<PlayMinion>().firstOrNull())
            ?.let { listOf(it) } ?: emptyList()
    }

    fun selectResourceFor(actionCard: ActionCard): List<PlayerActionResource> {
        return if (actionResources.isEmpty()) {
            emptyList()
        } else {
            listOf(actionResources.last())
        }
    }

    fun clear() {
        minionResources.clear()
        actionResources.clear()
    }
}

