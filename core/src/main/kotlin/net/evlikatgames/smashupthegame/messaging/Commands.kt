package net.evlikatgames.smashupthegame.messaging

import net.evlikatgames.smashupthegame.MinionState
import net.evlikatgames.smashupthegame.Player
import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.effect.OngoingEffect
import net.evlikatgames.smashupthegame.game.GameObject

abstract class Command(val source: GameObject)

class RevealTopPlayerDeckCard(source: GameObject, val targetPlayer: Player) : Command(source)
class DestroyTargetMinion(source: GameObject, val targetMinion: MinionState) : Command(source)
class PlayerDrawsCards(source: GameObject, val targetPlayer: Player, val numberOfCards: Int) : Command(source)
class ShuffleCardToItsOwnerDeck(source: GameObject, val targetCard: FactionCard) : Command(source)
class ApplyOngoingEffectOnTargetMinion(
    source: GameObject,
    val ongoingEffect: OngoingEffect,
    val targetMinion: MinionState
) : Command(source)