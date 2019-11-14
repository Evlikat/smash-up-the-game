package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.effect.BonusPowerEffect
import net.evlikatgames.smashupthegame.effect.OngoingEffect
import net.evlikatgames.smashupthegame.effect.TemporaryTribeEffect
import net.evlikatgames.smashupthegame.messaging.Message
import java.util.*

class MinionCardInPlay(
    override val minion: MinionCard
) : MinionState {
    private val ongoingEffectsBySource: MutableMap<GameObject, MutableSet<OngoingEffect>> = IdentityHashMap()
    private val ongoingEffects: Set<OngoingEffect> get() = ongoingEffectsBySource.values.flatten().toSet()

    private val bonusPower: Int
        get() = ongoingEffects
            .asSequence()
            .filterIsInstance<BonusPowerEffect>()
            .map { it.bonusPower }.sum()

    private val temporaryTribes: Set<String> = ongoingEffects
        .asSequence()
        .filterIsInstance<TemporaryTribeEffect>()
        .map { it.tribe }.toSet()

    override val effectivePower: Int get() = (minion.basePower + bonusPower).coerceAtLeast(0)
    override val effectiveTribes: Set<String> get() = minion.tribes + temporaryTribes
    override val controller: Player get() = minion.owner

    fun onMessage(message: Message, ctx: GameContext) {
        minion.onMessage(message, ctx)
    }

    fun addOngoingEffect(source: GameObject, ongoingEffect: OngoingEffect) {
        ongoingEffectsBySource.computeIfAbsent(source) { mutableSetOf() }.add(ongoingEffect)
    }

    fun removeOngoingEffectBySource(source: GameObject) {
        ongoingEffectsBySource.remove(source)
    }
}