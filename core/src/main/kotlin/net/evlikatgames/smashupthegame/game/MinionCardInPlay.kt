package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.effect.WithBonusPower
import net.evlikatgames.smashupthegame.effect.Effect
import net.evlikatgames.smashupthegame.effect.TemporaryTribeEffect
import net.evlikatgames.smashupthegame.messaging.Message
import java.util.*

class MinionCardInPlay(
    override val card: MinionCard
) : MinionState {
    private val effectsBySource: MutableMap<GameObject, MutableSet<Effect>> = IdentityHashMap()
    private val effects: Set<Effect> get() = effectsBySource.values.flatten().toSet()

    private val bonusPower: Int
        get() = effects
            .asSequence()
            .filterIsInstance<WithBonusPower>()
            .map { it.bonusPower }.sum()

    private val temporaryTribes: Set<String> = effects
        .asSequence()
        .filterIsInstance<TemporaryTribeEffect>()
        .map { it.tribe }.toSet()

    override val effectivePower: Int get() = (card.basePower + bonusPower).coerceAtLeast(0)
    override val effectiveTribes: Set<String> get() = card.tribes + temporaryTribes
    override val controller: Player get() = card.owner

    fun onMessage(message: Message, ctx: GameContext) {
        card.onMessage(message, ctx)
        effectsBySource.forEach { _, effects ->
            effects.forEach { effect ->
                effect.onMessage(message, ctx)
            }
        }
    }

    fun addOngoingEffect(source: GameObject, effect: Effect) {
        effectsBySource.computeIfAbsent(source) { mutableSetOf() }.add(effect)
    }

    fun removeOngoingEffectBySource(source: GameObject) {
        effectsBySource.remove(source)
    }
}