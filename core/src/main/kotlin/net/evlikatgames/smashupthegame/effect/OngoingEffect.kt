package net.evlikatgames.smashupthegame.effect

interface OngoingEffect

interface BonusPowerEffect : OngoingEffect {
    val bonusPower: Int
}

interface TemporaryTribeEffect : OngoingEffect {
    val tribe: String
}

data class BonusPower(override val bonusPower: Int) : BonusPowerEffect
data class TemporaryTribe(override val tribe: String) : TemporaryTribeEffect