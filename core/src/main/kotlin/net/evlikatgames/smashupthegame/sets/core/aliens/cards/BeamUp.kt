package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.messaging.ReturnTargetMinionToItsOwnerHand

class BeamUp : InstantActionCard() {

    override fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject> {
        return ctx.minionsInPlay()
    }

    override fun play(player: Player, target: List<GameObject>, ctx: GameContext) {
        val targetMinion = target.first() as MinionState
        ctx.sendCommand(ReturnTargetMinionToItsOwnerHand(this, targetMinion))
    }
}