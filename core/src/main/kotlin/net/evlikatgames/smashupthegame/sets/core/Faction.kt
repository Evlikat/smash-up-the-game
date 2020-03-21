package net.evlikatgames.smashupthegame.sets.core

import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.sets.core.aliens.cards.*
import net.evlikatgames.smashupthegame.sets.core.dinosaurs.cards.*
import net.evlikatgames.smashupthegame.sets.core.pirates.cards.*
import net.evlikatgames.smashupthegame.sets.core.robots.cards.*

sealed class Faction {
    open val cards: List<FactionCard> = emptyList()
}

val FACTIONS = listOf(
    Zombie, Aliens, Wizards, Pirates, Ninjas, Tricksters, Dinosaurs, Robots
)

object Zombie : Faction()
object Aliens : Faction() {
    override val cards: List<FactionCard> by lazy {
        deck {
            1 x ::SupremeOverlord
            2 x ::Invader
            3 x ::Scout
            4 x ::Collector
            1 x ::Abduction
            2 x ::BeamUp
            1 x ::CropCircles
            2 x ::Disintegrator
            1 x ::Invasion
            1 x ::JammedSignal
            1 x ::Probe
            1 x ::Terraforming
        }
    }
}

object Wizards : Faction()
object Pirates : Faction() {
    override val cards: List<FactionCard> by lazy {
        deck {
            1 x ::PirateKing
            2 x ::Buccaneer
            3 x ::SaucyWench
            4 x ::FirstMate
            2 x ::Broadside
            1 x ::Cannon
            2 x ::Dinghy
            1 x ::FullSail
            1 x ::Powderkeg
            1 x ::SeaDogs
        }
    }
}

object Ninjas : Faction()
object Tricksters : Faction()
object Robots : Faction() {
    override val cards: List<FactionCard> by lazy {
        deck {
            1 x ::NukeBot
            2 x ::WarBot
            3 x ::HoverBot
            4 x ::ZapBot
            1 x ::MicrobotAlpha
            1 x ::MicrobotArchive
            2 x ::MicrobotFixer
            2 x ::MicrobotGuard
            2 x ::MicrobotReclaimer
            2 x ::TechCenter
        }
    }
}

object Dinosaurs : Faction() {
    override val cards: List<FactionCard>by lazy {
        deck {
            1 x ::KingRex
            2 x ::Laseratops
            3 x ::ArmorStego
            4 x ::WarRaptor
            2 x ::Augmentation
            2 x ::Howl
            1 x ::NaturalSelection
            1 x ::Rampage
            1 x ::SurvivalOfTheFittest
            1 x ::ToothAndClawAndGuns
            1 x ::Upgrade
            1 x ::WildlifePreserve
        }
    }
}

private typealias CardFactory = () -> FactionCard

private class Cards {
    val cards = mutableListOf<FactionCard>()

    infix fun Int.x(factory: CardFactory) {
        repeat(this) { cards.add(factory.invoke()) }
    }
}

private fun deck(block: Cards.() -> Unit): List<FactionCard> {
    val cards = Cards().apply { block() }
    return cards.cards
}