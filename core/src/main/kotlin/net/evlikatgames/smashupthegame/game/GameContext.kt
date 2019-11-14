package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.messaging.Action
import net.evlikatgames.smashupthegame.messaging.Command

interface GameContext {

    val bases: Set<Base>

    fun sendCommand(command: Command)

    fun askPlayerConfirm(player: Player, pendingCommand: Command): Boolean

    fun <T: GameObject> askPlayerChooseTarget(player: Player, pendingAction: Action, validTargets: List<T>): T

    fun <T: GameObject> askPlayerChooseSomeTargets(
        player: Player,
        pendingAction: Action,
        validTargets: List<T>,
        numberOfTargets: Int = validTargets.size
    ): List<T>

    fun cardsPlayedThisTurn(): List<FactionCard>

    /**
     * Peeks top card of a target [player]'s deck.
     * Return it if it is revealed and null otherwise.
     */
    fun topCardOfPlayerDeck(player: Player): FactionCard?

    fun minionsOnBase(base: Base): List<MinionState>

    fun minionsInPlay(): List<MinionState>

    fun cardsInPlayerDiscardPile(player: Player): List<FactionCard>

    fun baseByCard(baseCard: BaseCard): Base

    fun baseOfMinion(minionCard: MinionCard): Base
}