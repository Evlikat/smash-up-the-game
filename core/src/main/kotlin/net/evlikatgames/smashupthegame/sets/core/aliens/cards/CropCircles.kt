package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand

class CropCircles : InstantActionCard() {

    override fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject> {
        return ctx.bases
    }

    override fun play(player: Player, target: List<GameObject>, ctx: GameContext) {
        val targetBase = target.first() as BaseState
        targetBase.minionsInPlay.forEach { minionOnBase ->
            ctx.sendCommand(ReturnTargetMinionToItsOwnerHand(this, minionOnBase))
        }
    }
}