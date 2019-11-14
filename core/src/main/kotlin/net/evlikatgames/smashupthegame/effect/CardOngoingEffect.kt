package net.evlikatgames.smashupthegame.effect

import net.evlikatgames.smashupthegame.game.GameContext
import net.evlikatgames.smashupthegame.game.GameObject

interface CardOngoingEffect {

    fun selectObjects(objects: List<GameObject>, ctx: GameContext): List<GameObject>

    fun enable(objects: List<GameObject>, ctx: GameContext)
}