package net.evlikatgames.smashupthegame.game

class Rankings<P : Any> private constructor(
    private val ranks: Map<P, Int>,
    val currentPowerState: Map<P, Int>
) : Map<P, Int> by ranks {

    companion object {
        fun <P : Any> build(currentPowerState: Map<P, Int>): Rankings<P> {
            val table = currentPowerState.values.sortedDescending()
            return Rankings(
                ranks = currentPowerState.mapValues { (_, power) -> table.indexOf(power) + 1 },
                currentPowerState = currentPowerState
            )
        }
    }

    val winners: List<P> get() = filter { (_, rank) -> rank == 1 }.map { it.key }
    val runnersUp: List<P> get() = filter { (_, rank) -> rank == 2 }.map { it.key }
}