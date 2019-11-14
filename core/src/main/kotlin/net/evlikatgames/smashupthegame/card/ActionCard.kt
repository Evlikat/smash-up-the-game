package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject

abstract class ActionCard : FactionCard() {

    abstract fun availableTargets(player: Player, ctx: GameContext): Collection<GameObject>

    abstract fun play(player: Player, target: Collection<GameObject>, ctx: GameContext)
}