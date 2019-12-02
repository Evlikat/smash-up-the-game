package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject

abstract class ActionCard(val numberOfTargets: Int = 1) : FactionCard() {

    abstract fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject>

    abstract fun play(player: Player, target: List<GameObject>, ctx: GameContext)
}