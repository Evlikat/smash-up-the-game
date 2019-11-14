package net.evlikatgames.smashupthegame.card

class DiscardPile : Deck<FactionCard>(mutableListOf()) {
    override val visibleCards: List<FactionCard> get() = cards
}