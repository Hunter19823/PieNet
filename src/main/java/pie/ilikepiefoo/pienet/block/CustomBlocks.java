package pie.ilikepiefoo.pienet.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import pie.ilikepiefoo.pienet.PieNet;

public interface CustomBlocks {
    // Create a Deferred Register to hold CustomBlocks which will all be registered under the "pienet"
    // namespace
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PieNet.MODID);
    // Creates a new Block with the id "pienet:example_block", combining the namespace and path
    DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock(
        "example_block",
        BlockBehaviour.Properties
            .of()
            .mapColor(MapColor.STONE)
    );
}
