package net.evlikatgames.smashupthegame.sets.core.robots.cards.effects

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.consideredAs
import net.evlikatgames.smashupthegame.effect.BonusPower
import net.evlikatgames.smashupthegame.effect.CardOngoingEffect
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.ApplyEffectOnTargetMinion
import net.evlikatgames.smashupthegame.sets.core.robots.cards.MICROBOT

class BonusPowerToMicrobots(private val minionSource: MinionCard) : CardOngoingEffect {

    override fun selectObjects(objects: List<GameObject>, ctx: GameContext): List<GameObject> {
        return objects
            .filterIsInstance<MinionState>()
            .filter { it consideredAs MICROBOT && it.controller == minionSource.owner }
    }

    override fun enable(objects: List<GameObject>, ctx: GameContext) {
        objects.forEach {
            ctx.sendCommand(ApplyEffectOnTargetMinion(
                source = minionSource,
                effect = BonusPower(bonusPower = 1),
                targetMinion = it as MinionState
            ))
        }
    }
}