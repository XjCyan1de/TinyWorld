package com.github.xjcyan1de.tinyworld

import com.github.xjcyan1de.cyanlibz.bukkit.events.listen
import org.bukkit.event.block.*

object WorldListener {
    init {
        listen<BlockBreakEvent> { handle() }
        listen<BlockBurnEvent> { handle() }
        listen<BlockExplodeEvent> { handle() }
        listen<BlockFadeEvent> { handle() }
        listen<BlockFormEvent> { handle() }
        listen<BlockFromToEvent> { handle() }
        listen<BlockGrowEvent> { handle() }
        listen<BlockIgniteEvent> { handle() }
        listen<BlockPistonExtendEvent> { handle() }
        listen<BlockPistonRetractEvent> { handle() }
        listen<BlockPlaceEvent> { handle() }
        listen<BlockSpreadEvent> { handle() }
        listen<CauldronLevelChangeEvent> { handle() }
        listen<EntityBlockFormEvent> { handle() }
        listen<FluidLevelChangeEvent> { handle() }
        listen<LeavesDecayEvent> { handle() }
        listen<MoistureChangeEvent> { handle() }
        listen<SignChangeEvent> { handle() }
        listen<SpongeAbsorbEvent> { handle() }
    }

    fun BlockEvent.handle() = block.world.worldSaver?.handleChunkEdit(block.chunk)
}