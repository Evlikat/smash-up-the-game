package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.Zone
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.effect.Effect
import net.evlikatgames.smashupthegame.effect.WithBonusBreakPoints
import java.util.*

class BaseState(
    val baseCard: BaseCard,
    private val minions: MutableList<MinionCardInPlay> = mutableListOf(),
    private val actions: MutableList<OngoingActionCardInPlay> = mutableListOf()
) : GameObject, Zone {

    private val effectsBySource: MutableMap<GameObject, MutableSet<Effect>> = IdentityHashMap()
    private val effects: Set<Effect> get() = effectsBySource.values.flatten().toSet()
    private val bonusBreakPoints = effects
        .asSequence()
        .filterIsInstance<WithBonusBreakPoints>()
        .map { it.bonusBreakPoints }.sum()

    val effectiveBreakPoints: Int get() = baseCard.breakPoints + bonusBreakPoints

    fun remove(card: FactionCard): Boolean {
        val removed = minions.removeIf { it.card == card }
        if (removed) return true
        return actions.removeIf { it.action == card }
    }

    val minionsInPlay: List<MinionCardInPlay> get() = minions
    val actionsInPlay: List<OngoingActionCardInPlay> get() = actions
}