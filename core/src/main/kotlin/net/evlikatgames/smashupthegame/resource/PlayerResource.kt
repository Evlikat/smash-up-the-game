package net.evlikatgames.smashupthegame.resource

import net.evlikatgames.smashupthegame.card.MinionCard

sealed class PlayerResource
sealed class PlayerMinionResource : PlayerResource()
sealed class PlayerActionResource : PlayerResource()

object PlayMinion : PlayerMinionResource()
class PlayExtraMinionWithRestrictedPower(val maxPower: Int) : PlayerMinionResource()
class PlayFixedExtraMinion(val minionCard: MinionCard) : PlayerMinionResource()

object PlayAction : PlayerActionResource()