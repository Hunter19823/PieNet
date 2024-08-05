package pie.ilikepiefoo.pienet.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import pie.ilikepiefoo.pienet.PieNet;

public interface CustomCreativeModeTabs {

    // Create a Deferred Register to hold CustomCreativeModeTabs which will all be registered under the
    // "pienet" namespace
    DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        PieNet.MODID
    );
    // Creates a creative tab with the id "pienet:example_tab" for the example item, that is
    // placed after the combat tab
    DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(
        "example_tab",
        () -> CreativeModeTab
            .builder()
            .title(Component.translatable("itemGroup.pienet"))
            .withTabsBefore(net.minecraft.world.item.CreativeModeTabs.COMBAT)
            .icon(
                () -> CustomItems.HOLO_TABLE_ITEM
                    .get()
                    .getDefaultInstance()
            )
            .displayItems(
                (parameters, output) -> {
                    output.accept(CustomItems.HOLO_TABLE_ITEM.get());
                }
            )
            .build()
    );
}
