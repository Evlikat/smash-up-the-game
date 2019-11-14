package net.evlikatgames.smashupthegame

import net.evlikatgames.smashupthegame.resource.PlayerResource

sealed class PlayerEvent

class PlayerResourceAdded(val resource: PlayerResource): PlayerEvent()