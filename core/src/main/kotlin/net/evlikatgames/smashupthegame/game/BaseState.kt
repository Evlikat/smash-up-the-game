package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.card.FactionCard

class BaseState(
    private val minions: MutableList<MinionCardInPlay> = mutableListOf(),
    private val actions: MutableList<OngoingActionCardInPlay> = mutableListOf()
): GameObject {
    fun remove(card: FactionCard): Boolean {
        val removed = minions.removeIf { it.minion == card }
        if (removed) return true
        return actions.removeIf { it.action == card }
    }

    val minionsInPlay: List<MinionCardInPlay> get() = minions
    val actionsInPlay: List<OngoingActionCardInPlay> get() = actions
}