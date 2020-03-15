package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AttachOngoingActionToTargetMinion

abstract class MinionOngoingActionCard<AT : ActionTargetChoice> : OngoingActionCard<AT, SingleTargetSelected<MinionState>>() {

    final override fun play(player: Player, target: SingleTargetSelected<MinionState>, ctx: GameContext) {
        ctx.sendCommand(AttachOngoingActionToTargetMinion(
            source = this,
            ongoingAction = this,
            targetMinion = target.gameObject
        ))
    }
}