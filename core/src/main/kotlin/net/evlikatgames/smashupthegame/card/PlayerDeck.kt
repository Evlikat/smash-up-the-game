package net.evlikatgames.smashupthegame.card

class PlayerDeck(
    initCards: List<FactionCard>,
    discardPileCardReshuffle: () -> List<FactionCard>
) : RecurringDeck<FactionCard>(initCards, discardPileCardReshuffle) {

    private var topCardsRevealed = 0

    fun revealTopCard() {
        topCardsRevealed = 1
    }

    val revealedCard: FactionCard?
        get() {
            return if (topCardsRevealed > 0) cards.firstOrNull() else null
        }

    override fun afterDraw(card: FactionCard) {
        topCardsRevealed--
    }
}