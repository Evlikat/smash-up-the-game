package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.MoveTargetMinionToTargetBase

class Invasion : InstantActionCard(numberOfTargets = 2) {

    override fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject> {
        return when (targetIndex) {
            0 -> ctx.minionsInPlay()
            1 -> ctx.bases
            else -> emptyList()
        }
    }

    override fun play(player: Player, target: List<GameObject>, ctx: GameContext) {
        val targetMinion = target[0] as MinionState
        val targetBase = target[1] as BaseState
        ctx.sendCommand(MoveTargetMinionToTargetBase(this, targetMinion, targetBase))
    }
}