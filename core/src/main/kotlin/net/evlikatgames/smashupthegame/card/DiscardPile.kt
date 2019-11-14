package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.CardPile

class DiscardPile<C : Card> : CardPile<C>(mutableListOf()) {
    override val visibleCards: List<C> get() = cards
    fun clear() {
        cards.clear()
    }
}