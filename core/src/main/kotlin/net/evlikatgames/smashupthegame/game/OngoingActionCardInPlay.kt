package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.OngoingActionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.OngoingActionCard

class OngoingActionCardInPlay(
    override val action: OngoingActionCard,
    override val controller: Player
) : OngoingActionState