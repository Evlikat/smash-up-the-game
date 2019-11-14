package net.evlikatgames.smashupthegame.messaging

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.BaseCard
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.GameObject
import net.evlikatgames.smashupthegame.game.God

class BeforeBaseScores(val targetBaseState: BaseState): Message(God, targetBaseState)
class AfterBaseScores(val targetBaseState: BaseState): Message(God, targetBaseState)

class BeforeMinionIsPlayed(val player: Player, target: GameObject) : Message(player, target)
class AfterMinionIsPlayed(val player: Player, val minion: MinionState, val baseCard: BaseCard) : Message(player, baseCard)

class BeforeMinionDestroyed(source: GameObject, val minion: MinionState) : Message(source, minion)
class AfterMinionDestroyed(source: GameObject, val minion: MinionState) : Message(source, minion)
