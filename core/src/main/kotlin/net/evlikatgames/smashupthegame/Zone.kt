package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.FactionCard

interface Zone

class PlayerHand() : Zone {

    private val cards = mutableListOf<FactionCard>()

    fun add(card: FactionCard) = cards.add(card)
}

