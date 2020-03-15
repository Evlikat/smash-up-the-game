package net.evlikatgames.smashupthegame.sets.core.robots.cards.effects

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.effect.CardOngoingEffect
import net.evlikatgames.smashupthegame.effect.TemporaryTribe
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.ApplyEffectOnTargetMinion
import net.evlikatgames.smashupthegame.sets.core.robots.cards.MICROBOT

class AllMinionsAreMicrobots(private val minionSource: MinionCard) : CardOngoingEffect {

    override fun selectObjects(objects: List<GameObject>, ctx: GameContext): List<GameObject> {
        return objects
            .filterIsInstance<MinionState>()
            .filter { it.controller == minionSource.owner }
    }

    override fun enable(objects: List<GameObject>, ctx: GameContext) {
        objects.forEach {
            ctx.sendCommand(ApplyEffectOnTargetMinion(
                source = minionSource,
                effect = TemporaryTribe(MICROBOT),
                targetMinion = it as MinionState
            ))
        }
    }
}