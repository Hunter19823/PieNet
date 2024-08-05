package pie.ilikepiefoo.pienet.block;

import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import pie.ilikepiefoo.pienet.PieNet;

public interface CustomBlocks {
    // Create a Deferred Register to hold CustomBlocks which will all be registered under the "pienet"
    // namespace
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PieNet.MODID);

    DeferredBlock<HoloTableBlock> HOLO_TABLE = BLOCKS.register(
        "holo_table",
        () -> new HoloTableBlock()
    );
}
