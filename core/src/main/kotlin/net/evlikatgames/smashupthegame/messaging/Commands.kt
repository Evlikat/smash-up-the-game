package net.evlikatgames.smashupthegame.messaging

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.OngoingActionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.card.MinionCard
import net.evlikatgames.smashupthegame.card.OngoingActionCard
import net.evlikatgames.smashupthegame.effect.Effect
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.resource.PlayerResource

abstract class Command(val source: GameObject)

class AddPlayerResource(source: GameObject, val targetPlayer: Player, val resource: PlayerResource) : Command(source)
class GainVictoryPoints(source: GameObject, val points: Int) : Command(source)
class ReturnTargetMinionToItsOwnerHand(source: GameObject, val targetMinion: MinionState) : Command(source)
class PlaceTargetMinionOnTheBottomOfItsOwnerDeck(source: GameObject, val targetMinion: MinionState) : Command(source)
class PlaceTargetMinionCardOnTheBottomOfItsOwnerDeck(source: GameObject, val targetMinion: MinionCard) : Command(source)
class RevealTopPlayerDeckCard(source: GameObject, val targetPlayer: Player) : Command(source)
class DestroyTargetMinion(source: GameObject, val targetMinion: MinionState) : Command(source)
class DestroyMinions(source: GameObject, val minions: List<MinionState>) : Command(source)
class AttachOngoingActionToTargetMinion(source: GameObject, val ongoingAction: OngoingActionCard<*, *>, val targetMinion: MinionState) : Command(source)
class AttachOngoingActionToTargetBase(source: GameObject, val ongoingAction: OngoingActionCard<*, *>, val targetBase: BaseState) : Command(source)
class DestroyTargetOngoingAction(source: GameObject, val targetOngoingAction: OngoingActionState) : Command(source)
class PlayerDrawsCards(source: GameObject, val targetPlayer: Player, val numberOfCards: Int) : Command(source)
class ShuffleCardToItsOwnerDeck(source: GameObject, val targetCard: FactionCard) : Command(source)
class TargetPlayerDiscardsTargetCard(source: GameObject, val targetPlayer: Player, val targetCard: FactionCard) : Command(source)
class ApplyEffectOnTargetMinion(
    source: GameObject,
    val effect: Effect,
    val targetMinion: MinionState
) : Command(source)

class ApplyEffectOnTargetBase(
    source: GameObject,
    val effect: Effect,
    val targetBase: BaseState
) : Command(source)

class ApplyEffectOnMinions(
    source: GameObject,
    val effect: Effect,
    val minions: List<MinionState>
) : Command(source)

class CancelEffect(
    source: GameObject,
    val effect: Effect
) : Command(source)

class RemoveAllEffectsBySourceFromTargetMinion(
    source: GameObject,
    val targetMinion: MinionState
) : Command(source)

class MoveTargetMinionToTargetBase(
    source: GameObject,
    val targetMinion: MinionState,
    val targetBase: BaseState
) : Command(source)

class MoveMinionsToTargetBase(
    source: GameObject,
    val minions: List<MinionState>,
    val targetBase: BaseState
) : Command(source)

// TODO: shuffle old base - not discard
class SwapBases(
    source: GameObject,
    val targetBase: BaseState,
    val newBaseCard: BaseCard
) : Command(source)