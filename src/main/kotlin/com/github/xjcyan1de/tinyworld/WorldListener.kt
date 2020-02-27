package com.github.xjcyan1de.tinyworld

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*

object WorldListener : Listener {
    @EventHandler
    fun onBlock(event: BlockBreakEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockBurnEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockExplodeEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockFadeEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockFormEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockFromToEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockGrowEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockIgniteEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockPistonExtendEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockPistonRetractEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockPlaceEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: BlockSpreadEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: CauldronLevelChangeEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: EntityBlockFormEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: FluidLevelChangeEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: LeavesDecayEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: MoistureChangeEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: SignChangeEvent) =
        handleBlockEvent(event)

    @EventHandler
    fun onBlock(event: SpongeAbsorbEvent) =
        handleBlockEvent(event)

    fun handleBlockEvent(event: BlockEvent) = worldSavers[event.block.world]?.handleChunkEdit(event.block.chunk)
}