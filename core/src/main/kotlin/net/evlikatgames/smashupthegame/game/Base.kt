package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.Zone
import net.evlikatgames.smashupthegame.card.BaseCard

class Base(val baseCard: BaseCard) : Zone {

    val effectiveBreakPoints: Int get() = baseCard.breakPoints
}