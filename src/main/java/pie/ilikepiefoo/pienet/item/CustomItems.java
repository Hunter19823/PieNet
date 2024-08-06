package pie.ilikepiefoo.pienet.item;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import pie.ilikepiefoo.pienet.PieNet;
import pie.ilikepiefoo.pienet.block.CustomBlocks;
import pie.ilikepiefoo.pienet.block.HoloTableBlock;

public interface CustomItems {
    // Create a Deferred Register to hold CustomItems which will all be registered under the "pienet"
    // namespace
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(PieNet.MODID);

    DeferredItem<BlockItem> HOLO_TABLE_ITEM = ITEMS.register(
        "holo_table",
        () -> new BlockItem(
            CustomBlocks.HOLO_TABLE.get(),
            HoloTableBlock.DEFAULT_ITEM_PROPERTIES
        )
    );
}
