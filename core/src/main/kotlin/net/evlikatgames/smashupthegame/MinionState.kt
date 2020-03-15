package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.game.GameObject

interface MinionState: GameObject {

    val effectivePower: Int
    val effectiveTribes: Set<String>
    val controller: Player
    val card: MinionCard
}