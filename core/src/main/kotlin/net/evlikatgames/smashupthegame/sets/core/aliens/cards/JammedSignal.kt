package net.evlikatgames.smashupthegame.sets.core.aliens.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.OngoingActionCard
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject

class JammedSignal : OngoingActionCard() {

    override fun availableTargets(player: Player, targetIndex: Int, previouslySelectedObjects: List<GameObject>, ctx: GameContext): Collection<GameObject> {
        return ctx.bases
    }

    override fun play(player: Player, target: List<GameObject>, ctx: GameContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}