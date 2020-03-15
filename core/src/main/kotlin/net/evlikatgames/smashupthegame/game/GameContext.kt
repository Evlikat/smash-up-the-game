package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.OngoingActionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.card.OngoingActionCard
import net.evlikatgames.smashupthegame.messaging.Command
import net.evlikatgames.smashupthegame.messaging.Intention

interface GameContext {

    val players: List<Player>

    val bases: Set<BaseState>

    val baseDeckCards: Collection<BaseCard>

    fun sendCommand(command: Command)

    fun askPlayerConfirm(player: Player, pendingCommand: Command): Boolean

    fun <T : GameObject> askPlayerChooseTarget(player: Player,
                                               pendingIntention: Intention,
                                               validTargets: List<T>): T

    fun <T : GameObject> askPlayerChooseTargetOrDecline(player: Player,
                                                        pendingIntention: Intention,
                                                        validTargets: List<T>): T?

    fun <T : GameObject> askPlayerChooseSomeTargets(
        player: Player,
        pendingIntention: Intention,
        validTargets: List<T>,
        numberOfTargets: Int = validTargets.size
    ): List<T>

    fun cardsPlayedThisTurn(): List<FactionCard>

    /**
     * Peeks top card of a target [player]'s deck.
     * Return it if it is revealed and null otherwise.
     */
    fun topCardOfPlayerDeck(player: Player): FactionCard?

    fun minionState(minionCard: MinionCard): MinionState?

    fun minionsInPlay(): List<MinionState>

    fun cardsInPlayerDiscardPile(player: Player): List<FactionCard>

    fun baseByCard(baseCard: BaseCard): BaseState

    fun baseOfMinion(minionCard: MinionCard): BaseState

    fun ongoingActionState(ongoingAction: OngoingActionCard<*, *>): OngoingActionState

    fun minionOfOngoingEffect(ongoingAction: OngoingActionCard<*, *>): MinionState

    fun baseOfOngoingEffect(ongoingAction: OngoingActionCard<*, *>): MinionState

    fun playerHand(targetPlayer: Player): Collection<FactionCard>
}