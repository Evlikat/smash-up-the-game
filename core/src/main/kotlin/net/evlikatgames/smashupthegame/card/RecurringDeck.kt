package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.CardPile

open class RecurringDeck<C : Card>(
    initCards: List<C>,
    private val discardPileCardReshuffle: () -> List<C>
) : CardPile<C>(initCards) {

    fun draw(): C {
        if (cards.isEmpty()) {
            cards.addAll(discardPileCardReshuffle())
        }
        val card = cards.removeAt(cards.lastIndex)
        afterDraw(card)
        return card
    }

    protected open fun afterDraw(card: C) {}
}