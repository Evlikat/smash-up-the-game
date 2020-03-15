package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.MinionAndBaseSelected
import net.evlikatgames.smashupthegame.card.MultipleTargetChoices
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class Invasion : InstantActionCard<MultipleTargetChoices, MinionAndBaseSelected>() {

    override fun numberOfTargets(ctx: GameContext): Int = 2

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: MinionAndBaseSelected,
        ctx: GameContext
    ): MultipleTargetChoices {
        return MultipleTargetChoices(
            when (targetIndex) {
                0 -> ctx.minionsInPlay()
                1 -> ctx.bases
                else -> emptyList()
            }
        )
    }

    override fun play(player: Player, target: MinionAndBaseSelected, ctx: GameContext) {
        val targetMinion = target.selectedMinion
        val targetBase = target.baseState
        ctx.sendCommand(MoveTargetMinionToTargetBase(this, targetMinion, targetBase))
    }
}