package net.evlikatgames.smashupthegame.card

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.game.BaseState
import net.evlikatgames.smashupthegame.game.FactionObject
import net.evlikatgames.smashupthegame.game.GameObject

sealed class ActionTargetChoice

object NoTargetChoice : ActionTargetChoice()
class MultipleTargetChoices(val gameObjects: Collection<GameObject>) : ActionTargetChoice()

sealed class SelectedTarget
object NoTargetSelected : SelectedTarget()
class SingleTargetSelected<T : GameObject>(val gameObject: T) : SelectedTarget()
class MultipleMinionsSelected(val selectedMinions: List<MinionState>) : SelectedTarget()
class MinionAndBaseSelected(val selectedMinion: MinionState, val baseState: BaseState) : SelectedTarget()
class MinionsAndBasesSelected(val selectedMinions: List<MinionState>, val selectedBases: List<BaseState>) : SelectedTarget()
class PlayerAndCardInHandSelected(val player: Player, val card: FactionCard) : SelectedTarget()
class BaseAndCardInBaseDeckSelected(val baseState: BaseState, val baseCard: BaseCard) : SelectedTarget()
class FactionAndBasesSelected(val faction: FactionObject, val bases: List<BaseState>) : SelectedTarget()