package net.evlikatgames.smashupthegame

data class PlayerFactions(
    private val faction1: Faction,
    private val faction2: Faction
) {
    fun toList(): List<Faction> = listOf(faction1, faction2)
}