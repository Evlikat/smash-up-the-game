package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.OngoingActionCard

interface OngoingActionState {
    val controller: Player
    val action: OngoingActionCard
}