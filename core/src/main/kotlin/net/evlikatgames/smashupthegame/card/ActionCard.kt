package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.GameContext

abstract class ActionCard<AT : ActionTargetChoice, ST : SelectedTarget> : FactionCard() {

    open fun numberOfTargets(ctx: GameContext): Int = 1

    abstract fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: ST,
        ctx: GameContext
    ): AT

    abstract fun play(
        player: Player,
        target: ST,
        ctx: GameContext
    )
}