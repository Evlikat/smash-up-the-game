package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Zone

open class Deck<C : Card>(
    initCards: List<C>
) : Zone {

    protected val cards: MutableList<C> = initCards.toMutableList()

    open val visibleCards: List<C> = cards.toList()

    fun draw(): C {
        val card = cards.removeAt(0)
        afterDraw(card)
        return card
    }

    protected open fun afterDraw(card: C) {}
}