package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.Card

open class CardPile<C : Card>(
    initCards: List<C>
) : Zone {

    fun add(card: C) {
        cards.add(card)
    }

    protected val cards: MutableList<C> = initCards.toMutableList()

    open val visibleCards: List<C> = cards.toList()
}