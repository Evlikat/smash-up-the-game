package net.evlikatgames.smashupthegame.messaging

abstract class Intention

class ScoreBase: Intention()
class DiscardCard: Intention()
class ReturnMinionToItsOwnerHand: Intention()
class DestroyMinion: Intention()
class ShuffleMinion: Intention()