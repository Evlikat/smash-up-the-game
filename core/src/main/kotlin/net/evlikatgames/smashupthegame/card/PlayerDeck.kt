package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Zone

class PlayerDeck(initCards: List<FactionCard>) : Deck<FactionCard>(initCards), Zone {

    private var topCardRevealed = false

    fun revealTopCard() {
        topCardRevealed = true
    }

    val revealedCard: FactionCard?
        get() {
            return if (topCardRevealed) cards.firstOrNull() else null
        }

    override fun afterDraw(card: FactionCard) {
        topCardRevealed = false
    }
}