package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.card.MinionCard

infix fun MinionCard.consideredAs(tribe: String): Boolean = tribe in this.tribes
infix fun MinionCard.notConsideredAs(tribe: String): Boolean = !this.consideredAs(tribe)

infix fun MinionState.consideredAs(tribe: String): Boolean = tribe in this.effectiveTribes
infix fun MinionState.notConsideredAs(tribe: String): Boolean = !this.consideredAs(tribe)
