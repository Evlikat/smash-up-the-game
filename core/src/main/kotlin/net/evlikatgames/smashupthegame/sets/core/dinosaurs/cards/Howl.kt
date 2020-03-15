package net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards

import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.InstantActionCard
import net.evlikatgames.smashupthegame.card.NoTargetChoice
import net.evlikatgames.smashupthegame.card.NoTargetSelected
import net.evlikatgames.smashupthegame.effect.BonusPowerUntilEndOfTurn
import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.messaging.ApplyEffectOnMinions

class Howl : InstantActionCard<NoTargetChoice, NoTargetSelected>() {

    override fun availableTargets(
        player: Player,
        targetIndex: Int,
        previouslySelectedObjects: NoTargetSelected,
        ctx: GameContext
    ): NoTargetChoice = NoTargetChoice

    override fun play(player: Player, target: NoTargetSelected, ctx: GameContext) {
        ctx.sendCommand(ApplyEffectOnMinions(
            source = this,
            effect = BonusPowerUntilEndOfTurn(bonusPower = 1),
            minions = ctx.minionsInPlay().filter { it.controller == player }
        ))
    }
}