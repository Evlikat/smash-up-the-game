package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.sets.core.Faction

data class PlayerFactions(
    private val faction1: Faction,
    private val faction2: Faction
) {
    fun toList(): List<Faction> = listOf(faction1, faction2)
}