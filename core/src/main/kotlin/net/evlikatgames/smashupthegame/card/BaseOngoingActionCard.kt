package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.AttachOngoingActionToTargetBase

abstract class BaseOngoingActionCard<AT : ActionTargetChoice> : OngoingActionCard<AT, SingleTargetSelected<BaseState>>() {

    final override fun play(player: Player, target: SingleTargetSelected<BaseState>, ctx: GameContext) {
        ctx.sendCommand(AttachOngoingActionToTargetBase(
            source = this,
            ongoingAction = this,
            targetBase = target.gameObject
        ))
    }
}