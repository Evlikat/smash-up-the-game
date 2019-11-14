package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.FactionCard

interface Zone

class PlayerHand() : Zone {

    private val cards = mutableListOf<FactionCard>()
    val allCards: List<FactionCard> get() = cards
    val size: Int get() = cards.size

    fun add(card: FactionCard) = cards.add(card)
    fun remove(card: FactionCard): Boolean = cards.remove(card)
}