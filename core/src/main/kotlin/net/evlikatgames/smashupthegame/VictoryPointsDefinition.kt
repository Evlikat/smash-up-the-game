package net.evlikatgames.smashupthegame

data class VictoryPointsDefinition(
    private val winnerPoints: Int,
    private val secondPlacePoints: Int,
    private val thirdPlacePoints: Int
) {
    operator fun get(index: Int): Int {
        return when (index) {
            1 -> winnerPoints
            2 -> secondPlacePoints
            3 -> thirdPlacePoints
            else -> 0
        }
    }
}