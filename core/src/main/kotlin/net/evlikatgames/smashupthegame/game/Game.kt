package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.*
import net.evlikatgames.smashupthegame.card.*
import net.evlikatgames.smashupthegame.messaging.*
import net.evlikatgames.smashupthegame.resource.PlayerResourcePool
import net.evlikatgames.smashupthegame.sets.core.BASES
import java.util.*
import kotlin.collections.HashSet

class Game(
    override val players: List<Player>
) : GameContext {

    companion object {
        const val WIN_POINTS = 15
        const val HAND_CAPACITY = 10
    }

    private val scoreBoard = ScoreBoard(players, winPoints = WIN_POINTS)

    private val baseDiscardPile: DiscardPile<BaseCard> = createBaseDiscardPile()
    private val baseDeck: RecurringDeck<BaseCard> = createBaseDeck(baseDiscardPile)

    private val playerHands: MutableMap<Player, PlayerHand> = IdentityHashMap()
    private val playerDecks: MutableMap<Player, PlayerDeck> = IdentityHashMap()
    private val discardPiles: MutableMap<Player, DiscardPile<FactionCard>> = IdentityHashMap()
    private val activeBases: MutableSet<BaseState> = HashSet()
    private val cardLocations: MutableMap<FactionCard, Zone> = IdentityHashMap()

    init {
        if (players.size !in 1..4) throw IllegalArgumentException("Wrong player number")

        players.forEach { player ->
            val discardPile = createPlayerDiscardPile()
            discardPiles[player] = discardPile
            playerDecks[player] = createPlayerDeck(player, discardPile)
        }

        repeat(players.size + 1) {
            val newBase = createBase(baseDeck.draw())
            activeBases.add(newBase)
        }
    }

    fun start() {
        players.forEach { player ->
            player.drawsCards(5)
        }

        // TODO: mulligan

        try {
            players.forEach { player ->
                turn(player)
            }
        } catch (ex: GameFinished) {
            // TODO: game finished winner is ex.winner
        }
    }

    private fun turn(currentPlayer: Player) {
        atStartOfTurnPhase(currentPlayer)
        playPhase(currentPlayer)
        baseScorePhase(currentPlayer)
        drawPhase(currentPlayer)
        atEndOfTurnPhase(currentPlayer)
    }

    private fun atStartOfTurnPhase(currentPlayer: Player) {
        activeBases.forEach { baseState ->
            // TODO: current player decides the order

            // TODO: base.baseCard.sendMessage
            baseState.minionsInPlay.forEach { m -> m.onMessage(StartTurn(currentPlayer), this) }

            // TODO:
            // baseState.actionsInPlay.forEach { m -> m.onMessage(StartTurn(currentPlayer)) }
        }
    }

    private fun playPhase(currentPlayer: Player) {
        val pool = PlayerResourcePool()
        currentPlayer.resourcePool = pool

        loop@ while (true) {
            val card = askToPlay(currentPlayer) ?: break
            val resources = when (card) {
                is MinionCard -> pool.selectResourceFor(card)
                is ActionCard<*, *> -> pool.selectResourceFor(card)
                else -> TODO("Unknown card type")
            }
            val consumed = when {
                resources.isEmpty() -> continue@loop
                resources.size == 1 -> resources[0]
                else -> // TODO: ask choose
                    resources[0]
            }
            pool.consumeResource(consumed)
        }
        currentPlayer.resourcePool.clear()
    }

    private fun baseScorePhase(currentPlayer: Player) {
        val scoredBases = activeBases.filter { baseState ->
            val totalMinionPower = baseState.minionsInPlay.sumBy { it.effectivePower }
            totalMinionPower >= baseState.effectiveBreakPoints
        }

        val scoringBases = scoredBases.map { it.baseCard to it }.toMap(mutableMapOf())

        while (scoringBases.isNotEmpty()) {
            val chosenBaseCard = askPlayerChooseTarget(currentPlayer, ScoreBase(), scoringBases.keys.toList())
            val chosenBaseState = scoringBases[chosenBaseCard]!!

            // TODO: consider order
            chosenBaseCard.beforeBaseScores()
            chosenBaseState.minionsInPlay.forEach { it.onMessage(BeforeBaseScores(chosenBaseState), this) }
            // TODO:
            //chosenBaseState.actionsInPlay.forEach { it.onMessage(BeforeBaseScores(chosenBaseState), this) }

            val rankings = Rankings.build(currentPowerState = evaluateEffectivePowerByPlayers(chosenBaseState))
            val baseScore = calculateScore(
                victoryPointsDefinition = chosenBaseCard.victoryPoints,
                rankings = rankings
            )
            val bonusScore = chosenBaseCard.bonusScore(rankings, this)
            scoreBoard.gainVictoryPoints(baseScore + bonusScore)
                ?.let { throw GameFinished(winner = it) }

            // TODO: consider order
            chosenBaseCard.afterBaseScores(rankings, this)
            chosenBaseState.minionsInPlay.forEach { it.onMessage(AfterBaseScores(chosenBaseState), this) }
            // TODO:
            //chosenBaseState.actionsInPlay.forEach { it.onMessage(AfterBaseScores(chosenBaseState), this) }

            chosenBaseState.minionsInPlay.forEach { discardFactionCard(it.card) }
            chosenBaseState.actionsInPlay.forEach { discardFactionCard(it.action) }

            discardBaseCard(chosenBaseCard)

            val newBase = createBase(baseDeck.draw())
            activeBases.add(newBase)

            scoringBases.remove(chosenBaseCard)
        }
    }

    private fun drawPhase(currentPlayer: Player) {
        currentPlayer.drawsCards(numberOfCards = 2)
        if (playerHands[currentPlayer]!!.size > HAND_CAPACITY) {
            currentPlayer.discardsCards(numberOfCards = playerHands[currentPlayer]!!.size - HAND_CAPACITY)
        }
    }

    private fun atEndOfTurnPhase(currentPlayer: Player) {
        activeBases.forEach { baseState ->
            // TODO: current player decides the order

            // TODO: base.baseCard.sendMessage
            baseState.minionsInPlay.forEach { m -> m.onMessage(EndTurn(currentPlayer), this) }

            // TODO:
            // baseState.actionsInPlay.forEach { m -> m.onMessage(EndTurn(currentPlayer)) }
        }
    }

    override fun sendCommand(command: Command) {
        when (command) {
            is DestroyTargetMinion -> handleDestroyMinion(command)
            is RevealTopPlayerDeckCard -> handleRevealTopPlayerDeckCard(command)
            is PlayerDrawsCards -> handlePlayerDrawsCards(command)
            else -> TODO()
        }
    }

    override fun minionState(minionCard: MinionCard): MinionState? = minionsInPlay().find { it.card == minionCard }

    override fun minionsInPlay(): List<MinionState> = activeBases.flatMap { it.minionsInPlay }

    override fun baseByCard(baseCard: BaseCard): BaseState {
        return activeBases.find { it.baseCard == baseCard }
            ?: throw IllegalArgumentException("Base not found")
    }

    override fun topCardOfPlayerDeck(player: Player): FactionCard? {
        return playerDecks[player]?.revealedCard
    }

    override fun cardsInPlayerDiscardPile(player: Player): List<FactionCard> {
        return discardPiles[player]!!.visibleCards
    }

    override fun ongoingActionState(ongoingAction: OngoingActionCard<*, *>): OngoingActionState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun minionOfOngoingEffect(ongoingAction: OngoingActionCard<*, *>): MinionState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun baseOfOngoingEffect(ongoingAction: OngoingActionCard<*, *>): MinionState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun baseOfMinion(minionCard: MinionCard): BaseState = cardLocations[minionCard]
        as? BaseState
        ?: throw IllegalStateException("Minion is not on a bases")

    override fun playerHand(targetPlayer: Player): Collection<FactionCard> {
        return playerHands[targetPlayer]?.allCards ?: emptyList()
    }

    override val bases: Set<BaseState> get() = activeBases.toSet()

    override fun askPlayerConfirm(player: Player, pendingCommand: Command): Boolean {
        // TODO:
        return true
    }

    override fun <T : GameObject> askPlayerChooseTarget(
        player: Player,
        pendingIntention: Intention,
        validTargets: List<T>
    ): T {
        if (validTargets.isEmpty()) {
            throw IllegalArgumentException("No valid targets")
        }
        if (validTargets.size == 1) {
            return validTargets.first()
        }
        // TODO:
        return validTargets.first()
    }

    override fun <T : GameObject> askPlayerChooseTargetOrDecline(
        player: Player,
        pendingIntention: Intention,
        validTargets: List<T>
    ): T? {
        if (validTargets.isEmpty()) {
            throw IllegalArgumentException("No valid targets")
        }
        if (validTargets.size == 1) {
            return validTargets.first()
        }
        // TODO:
        return validTargets.first()
    }

    override fun <T : GameObject> askPlayerChooseSomeTargets(player: Player, pendingIntention: Intention, validTargets: List<T>, numberOfTargets: Int): List<T> {
        return validTargets.take(numberOfTargets)
    }

    override val baseDeckCards: Collection<BaseCard> get() = baseDeck.visibleCards

    override fun cardsPlayedThisTurn(): List<FactionCard> {
        // TODO:
        return emptyList()
    }

    private fun createBaseDeck(discardPile: DiscardPile<BaseCard>): RecurringDeck<BaseCard> {
        return RecurringDeck(BASES.toList()) {
            val allCards = discardPile.visibleCards.toList()
            discardPile.clear()
            // TODO: notify cards moved
            allCards
        } // TODO:
    }

    private fun createPlayerDeck(player: Player, discardPile: DiscardPile<FactionCard>): PlayerDeck {
        val initCards = player.factions.toList().flatMap { it.cards }
        initCards.forEach { it.owner = player }
        return PlayerDeck(initCards) {
            val allCards = discardPile.visibleCards.toList()
            discardPile.clear()
            // TODO: notify cards moved
            allCards
        } // TODO:
    }

    private fun createBase(baseCard: BaseCard): BaseState {
        return BaseState(baseCard) // TODO:
    }

    private fun createPlayerDiscardPile(): DiscardPile<FactionCard> {
        return DiscardPile() // TODO:
    }

    private fun createBaseDiscardPile(): DiscardPile<BaseCard> {
        return DiscardPile() // TODO:
    }

    private fun discardFactionCard(card: FactionCard) {
        val discardPile = discardPiles[card.owner]!!
        val currentLocation = cardLocations[card]
        when (currentLocation) {
            is BaseState -> currentLocation.remove(card)
            is PlayerHand -> playerHands[card.owner]?.remove(card)
            else -> TODO("Move from $currentLocation to discardPile is not implemented")
        }
        cardLocations[card] = discardPile
        discardPile.add(card)
    }

    private fun discardBaseCard(card: BaseCard) {
        activeBases.remove(baseByCard(card))
        baseDiscardPile.add(card)
    }

    private fun cleanUpMinionOngoingEffects(minionState: MinionState) {
        activeBases.forEach { state ->
            state.minionsInPlay.forEach { mip -> mip.removeOngoingEffectBySource(minionState) }
        }
    }

    /**
     * returns null if end of turn
     */
    private fun askToPlay(currentPlayer: Player): FactionCard? {
        // TODO:
        return playerHands[currentPlayer]!!.allCards.firstOrNull()
    }

    private fun Player.drawsCards(numberOfCards: Int) {
        val deck = playerDecks[this]!!
        val hand = playerHands[this]!!
        repeat(numberOfCards) {
            hand.add(deck.draw())
        }
    }

    private fun Player.discardsCards(numberOfCards: Int) {
        askPlayerChooseSomeTargets(this, DiscardCard(), playerHands[this]!!.allCards, numberOfCards)
            .forEach { discardFactionCard(it) }
    }

    private fun handleDestroyMinion(command: DestroyTargetMinion) {
        val minionToDestroy = command.targetMinion
        val currentMinionLocation = cardLocations[minionToDestroy.card]
        when (currentMinionLocation) {
            is Base -> {
                try {
                    val minionsInPlay = minionsInPlay()
                    minionsInPlay.forEach {
                        it.card.onMessage(BeforeMinionDestroyed(command.source, minionToDestroy), this)
                    }

                    discardFactionCard(minionToDestroy.card)
                    cleanUpMinionOngoingEffects(command.targetMinion)

                    minionsInPlay.forEach {
                        it.card.onMessage(AfterMinionDestroyed(command.source, minionToDestroy), this)
                    }
                } catch (ignored: InterruptException) {
                }
            }
        }
    }

    private fun handleRevealTopPlayerDeckCard(command: RevealTopPlayerDeckCard) {
        playerDecks[command.targetPlayer]?.revealTopCard()
    }

    private fun handlePlayerDrawsCards(command: PlayerDrawsCards) {
        command.targetPlayer.drawsCards(numberOfCards = command.numberOfCards)
    }
}