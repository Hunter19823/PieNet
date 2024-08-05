package pie.ilikepiefoo.pienet.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import pie.ilikepiefoo.pienet.PieNet;
import pie.ilikepiefoo.pienet.block.CustomBlocks;

public interface CustomItems {
    // Create a Deferred Register to hold CustomItems which will all be registered under the "pienet"
    // namespace
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(PieNet.MODID);
    // Creates a new BlockItem with the id "pienet:example_block", combining the namespace and path
    DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
        "example_block",
        CustomBlocks.EXAMPLE_BLOCK
    );
    // Creates a new food item with the id "pienet:example_id", nutrition 1 and saturation 2
    DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem(
        "example_item",
        new Item
            .Properties()
            .food(
                new FoodProperties
                    .Builder()
                    .alwaysEdible()
                    .nutrition(1)
                    .saturationModifier(2f)
                    .build()
            )
    );
}
