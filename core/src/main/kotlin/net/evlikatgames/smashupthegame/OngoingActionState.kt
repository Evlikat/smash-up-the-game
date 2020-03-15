package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.OngoingActionCard
import net.evlikatgames.smashupthegame.game.GameObject

interface OngoingActionState : GameObject {
    val controller: Player
    val action: OngoingActionCard<*, *>
}