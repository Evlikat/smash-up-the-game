package net.evlikatgames.smashupthegame.game

import net.evlikatgames.smashupthegame.*
import net.evlikatgames.smashupthegame.card.*
import net.evlikatgames.smashupthegame.messaging.*
import net.evlikatgames.smashupthegame.sets.core.bases.JungleOasis
import net.evlikatgames.smashupthegame.sets.core.bases.TheHomeworld
import java.util.*

class Game(
    private val players: List<Player>
) : GameContext {

    private val baseDeck: Deck<BaseCard> = Deck(listOf(
        TheHomeworld(),
        JungleOasis()
    ))
    private val scoreBoard = ScoreBoard(players, winPoints = 15)

    private val playerHands: MutableMap<Player, PlayerHand> = IdentityHashMap()
    private val playerDecks: MutableMap<Player, PlayerDeck> = IdentityHashMap()
    private val discardPiles: MutableMap<Player, DiscardPile> = IdentityHashMap()
    private val activeBases: MutableMap<Base, BaseState> = IdentityHashMap()
    private val cardLocations: MutableMap<FactionCard, Zone> = IdentityHashMap()

    init {
        if (players.size !in 1..4) throw IllegalArgumentException("Wrong player number")

        players.forEach { player ->
            playerDecks[player] = createPlayerDeck(player)
            discardPiles[player] = createDiscardPile()
        }

        repeat(players.size + 1) {
            val newBase = createBase(baseDeck.draw())
            activeBases[newBase] = BaseState()
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
        activeBases.forEach { base, baseState ->
            // TODO: current player decides the order

            // TODO: base.baseCard.sendMessage
            baseState.minionsInPlay.forEach { m -> m.onMessage(StartTurn(currentPlayer), this) }

            // TODO:
            // baseState.actionsInPlay.forEach { m -> m.onMessage(StartTurn(currentPlayer)) }
        }
    }

    private fun playPhase(currentPlayer: Player) {
        currentPlayer.resourcePool = PlayerResourcePool()
        // TODO
    }

    private fun baseScorePhase(currentPlayer: Player) {
        val scoredBases = activeBases.filter { (base, baseState) ->
            val totalMinionPower = baseState.minionsInPlay.sumBy { it.effectivePower }
            totalMinionPower >= base.effectiveBreakPoints
        }

        val scoringBases = scoredBases.keys.map { it.baseCard to it }.toMap(mutableMapOf())

        while (scoringBases.isNotEmpty()) {
            val chosenBaseCard = askPlayerChooseTarget(currentPlayer, ScoreBase(), scoringBases.keys.toList())
            val chosenBase = scoringBases[chosenBaseCard]!!
            val chosenBaseState = activeBases[chosenBase]!!

            // TODO: consider order
            chosenBaseCard.beforeBaseScores()
            chosenBaseState.minionsInPlay.forEach { it.onMessage(BeforeBaseScores(chosenBaseState), this) }
            // TODO:
            //chosenBaseState.actionsInPlay.forEach { it.onMessage(BeforeBaseScores(chosenBaseState), this) }

            val totalScore = calculateScore(
                victoryPointsDefinition = chosenBaseCard.victoryPoints,
                currentPowerState = evaluateEffectivePowerByPlayers(chosenBaseState)
            )
            scoreBoard.gainVictoryPoints(totalScore)
                ?.let { throw GameFinished(winner = it) }

            // TODO: consider order
            chosenBaseCard.afterBaseScores()
            chosenBaseState.minionsInPlay.forEach { it.onMessage(AfterBaseScores(chosenBaseState), this) }
            // TODO:
            //chosenBaseState.actionsInPlay.forEach { it.onMessage(AfterBaseScores(chosenBaseState), this) }

            scoringBases.remove(chosenBaseCard)
        }
    }

    private fun drawPhase(currentPlayer: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun atEndOfTurnPhase(currentPlayer: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendCommand(command: Command) {
        when (command) {
            is DestroyTargetMinion -> handleDestroyMinion(command)
            is RevealTopPlayerDeckCard -> handleRevealTopPlayerDeckCard(command)
            is PlayerDrawsCards -> handlePlayerDrawsCards(command)
            else -> TODO()
        }
    }

    override fun minionsOnBase(base: Base): List<MinionState> = (activeBases[base]
        ?: throw IllegalStateException("Base is not in play"))
        .minionsInPlay

    override fun minionsInPlay(): List<MinionState> = activeBases.flatMap { it.value.minionsInPlay }

    override fun baseByCard(baseCard: BaseCard): Base {
        return activeBases.keys.find { it.baseCard == baseCard }
            ?: throw IllegalArgumentException("Base not found")
    }

    override fun topCardOfPlayerDeck(player: Player): FactionCard? {
        return playerDecks[player]?.revealedCard
    }

    override fun cardsInPlayerDiscardPile(player: Player): List<FactionCard> {
        return discardPiles[player]!!.visibleCards
    }

    override fun baseOfMinion(minionCard: MinionCard): Base = cardLocations[minionCard]
        as? Base
        ?: throw IllegalStateException("Minion is not on a bases")

    override val bases: Set<Base>
        get() = activeBases.keys

    override fun askPlayerConfirm(player: Player, pendingCommand: Command): Boolean {
        // TODO:
        return true
    }

    override fun <T : GameObject> askPlayerChooseTarget(player: Player, pendingAction: Action, validTargets: List<T>): T {
        if (validTargets.isEmpty()) {
            throw IllegalArgumentException("No valid targets")
        }
        if (validTargets.size == 1) {
            return validTargets.first()
        }
        // TODO:
        return validTargets.first()
    }

    override fun <T : GameObject> askPlayerChooseSomeTargets(player: Player, pendingAction: Action, validTargets: List<T>, numberOfTargets: Int): List<T> {
        return validTargets.take(numberOfTargets)
    }

    override fun cardsPlayedThisTurn(): List<FactionCard> {
        // TODO:
        return emptyList()
    }

    private fun createPlayerDeck(player: Player): PlayerDeck {
        val initCards = player.factions.toList().flatMap { it.cards }
        initCards.forEach { it.owner = player }
        return PlayerDeck(initCards) // TODO:
    }

    private fun createBase(baseCard: BaseCard): Base {
        return Base(baseCard) // TODO:
    }

    private fun createDiscardPile(): DiscardPile {
        return DiscardPile() // TODO:
    }

    private fun discardFactionCard(card: FactionCard) {
        val discardPile = discardPiles[card.owner]!!
        val currentLocation = cardLocations[card]
        when (currentLocation) {
            is Base -> activeBases[currentLocation]?.remove(card)
            else -> TODO("Move from $currentLocation to discardPile is not implemented")
        }
        cardLocations[card] = discardPile
    }

    private fun cleanUpMinionOngoingEffects(minionState: MinionState) {
        activeBases.forEach { _, state ->
            state.minionsInPlay.forEach { mip -> mip.removeOngoingEffectBySource(minionState) }
        }
    }

    private fun Player.drawsCards(numberOfCards: Int) {
        val deck = playerDecks[this]!!
        val hand = playerHands[this]!!
        repeat(numberOfCards) {
            hand.add(deck.draw())
        }
    }

    private fun handleDestroyMinion(command: DestroyTargetMinion) {
        val minionToDestroy = command.targetMinion
        val currentMinionLocation = cardLocations[minionToDestroy.minion]
        when (currentMinionLocation) {
            is Base -> {
                try {
                    val minionsInPlay = minionsInPlay()
                    minionsInPlay.forEach {
                        it.minion.onMessage(BeforeMinionDestroyed(command.source, minionToDestroy), this)
                    }

                    discardFactionCard(minionToDestroy.minion)
                    cleanUpMinionOngoingEffects(command.targetMinion)

                    minionsInPlay.forEach {
                        it.minion.onMessage(AfterMinionDestroyed(command.source, minionToDestroy), this)
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