package net.evlikatgames.smashupthegame.sets.core

import net.evlikatgames.smashupthegame.card.FactionCard
import net.evlikatgames.smashupthegame.sets.core.aliens.cards.*
import net.evlikatgames.smashupthegame.sets.core.robots.cards.*

sealed class Faction {
    open val cards: List<FactionCard> get() = emptyList()
}

object Zombie : Faction()
object Aliens : Faction() {
    override val cards: List<FactionCard>
        get() = build(
            1 x ::SupremeOverlord,
            2 x ::Invader,
            3 x ::Scout,
            4 x ::Collector,
            1 x ::Abduction,
            2 x ::BeamUp,
            1 x ::CropCircles,
            2 x ::Disintegrator,
            1 x ::Invasion,
            1 x ::JammedSignal,
            1 x ::Probe,
            1 x ::Terraforming
        )
}

object Wizards : Faction()
object Pirates : Faction()
object Ninjas : Faction()
object Tricksters : Faction()
object Robots : Faction() {
    override val cards: List<FactionCard>
        get() = build(
            1 x ::NukeBot,
            2 x ::WarBot,
            3 x ::HoverBot,
            4 x ::ZapBot,
            1 x ::MicrobotAlpha,
            1 x ::MicrobotArchive,
            2 x ::MicrobotFixer,
            2 x ::MicrobotGuard,
            2 x ::MicrobotReclaimer,
            2 x ::TechCenter
        )
}

object Dinosaurs : Faction()


private typealias CardFactory = () -> FactionCard
private typealias CardFacility = () -> List<FactionCard>

private fun build(vararg factories: CardFacility): List<FactionCard> {
    return factories.flatMap { it.invoke() }
}

private infix fun Int.x(factory: CardFactory): CardFacility {
    return { MutableList(this) { factory.invoke() } }
}