package pie.ilikepiefoo.pienet.event;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;
import pie.ilikepiefoo.pienet.Config;
import pie.ilikepiefoo.pienet.PieNet;
import pie.ilikepiefoo.pienet.item.CustomItems;

@EventBusSubscriber(modid = PieNet.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onCreativeModeTabContentEvent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(CustomItems.HOLO_TABLE_ITEM);
        }
    }

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) {
            LOGGER.info(
                "DIRT BLOCK >> {}",
                BuiltInRegistries.BLOCK.getKey(Blocks.DIRT)
            );
        }

        LOGGER.info(
            "{}{}",
            Config.magicNumberIntroduction,
            Config.magicNumber
        );

        Config.items.forEach((item) -> LOGGER.info(
            "ITEM >> {}",
            item.toString()
        ));
    }
}
