package pie.ilikepiefoo.pienet;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import pie.ilikepiefoo.pienet.block.CustomBlocks;
import pie.ilikepiefoo.pienet.block.entity.CustomBlockEntityTypes;
import pie.ilikepiefoo.pienet.item.CustomCreativeModeTabs;
import pie.ilikepiefoo.pienet.item.CustomItems;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PieNet.MODID)
public class PieNet {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "pienet";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in
    // automatically.
    public PieNet(IEventBus modEventBus, ModContainer modContainer) {
        // Register the Deferred register to the mod event bus so block entities get registered
        CustomBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        CustomBlocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        CustomItems.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CustomCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(
            ModConfig.Type.COMMON,
            Config.SPEC
        );
    }

}
