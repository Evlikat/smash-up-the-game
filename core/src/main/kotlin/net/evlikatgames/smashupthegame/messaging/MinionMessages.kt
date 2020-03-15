package net.evlikatgames.smashupthegame.messaging

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.OngoingActionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.game.God

class BeforeBaseScores(val targetBaseState: BaseState): Message(God, targetBaseState)
class AfterBaseScores(val targetBaseState: BaseState): Message(God, targetBaseState)

class BeforeOngoingActionIsPlayed(val player: Player, target: GameObject) : Message(player, target)
class AfterOngoingActionIsPlayed(val player: Player, val action: OngoingActionState, target: GameObject) : Message(player, target)

class BeforeOngoingActionDestroyed(source: GameObject, val action: OngoingActionState) : Message(source, action.action)
class AfterOngoingActionDestroyed(source: GameObject, val action: OngoingActionState) : Message(source, action.action)

class BeforeMinionIsPlayed(val player: Player, target: GameObject) : Message(player, target)
class AfterMinionIsPlayed(val player: Player, val minion: MinionState, val baseCard: BaseCard) : Message(player, baseCard)

class BeforeMinionIsMoved(val player: Player, val minion: MinionState, target: GameObject, val fromBaseCard: BaseCard, val toBaseCard: BaseCard) : Message(player, target)
class AfterMinionIsMoved(val player: Player, val minion: MinionState, val fromBaseCard: BaseCard, val toBaseCard: BaseCard) : Message(player, toBaseCard)

class BeforeMinionDestroyed(source: GameObject, val minion: MinionState) : Message(source, minion)
class AfterMinionDestroyed(source: GameObject, val minion: MinionState) : Message(source, minion)
