package net.evlikatgames.smashupthegame

sealed class PlayerEvent

class PlayerResourceAdded(val resource: PlayerResource): PlayerEvent()