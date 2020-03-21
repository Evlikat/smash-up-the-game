package net.evlikatgames.smashupthegame.sets.core.pirates.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.FactionAndBasesSelected
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.FactionObject
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.MoveMinionsToTargetBase
import net.evlikatgames.smashupthegame.sets.core.FACTIONS

class SeaDogs : InstantActionCard<MultipleTargetChoices, FactionAndBasesSelected>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: FactionAndBasesSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return when(targetIndex) {
            0 -> MultipleTargetChoices(ctx.bases)
            1 -> MultipleTargetChoices(ctx.bases - previouslySelectedObjects.bases)
            2 -> MultipleTargetChoices(FACTIONS.map { FactionObject(it) })
            else -> throw IllegalArgumentException("Faction and base expected")
        }
    }

    override fun play(
        player: Player,
        target: FactionAndBasesSelected,
        ctx: GameContext
    ) {
        ctx.sendCommand(MoveMinionsToTargetBase(
            source = this,
            minions = target.bases[0].minionsInPlay.filter { it.card in target.faction.faction.cards },
            targetBase = target.bases[1]
        ))
    }
}