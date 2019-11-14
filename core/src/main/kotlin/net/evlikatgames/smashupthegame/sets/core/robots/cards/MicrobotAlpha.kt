package net.evlikatgames.smashupthegame.sets.core.robots.cards

import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.effect.CardOngoingEffect
import net.evlikatgames.smashupthegame.sets.core.robots.cards.effects.AllMinionsAreMicrobots
import net.evlikatgames.smashupthegame.sets.core.robots.cards.effects.BonusPowerToMicrobots

class MicrobotAlpha : MinionCard(basePower = 1, tribes = setOf(MICROBOT)) {

    override val cardOngoingEffects: Set<CardOngoingEffect> = setOf(
        AllMinionsAreMicrobots(this),
        BonusPowerToMicrobots(this)
    )
}