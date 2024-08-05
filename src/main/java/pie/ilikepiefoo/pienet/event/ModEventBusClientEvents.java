package pie.ilikepiefoo.pienet.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import pie.ilikepiefoo.pienet.PieNet;
import pie.ilikepiefoo.pienet.block.entity.CustomBlockEntityTypes;
import pie.ilikepiefoo.pienet.client.render.CustomEntityRenderers;

@EventBusSubscriber(modid = PieNet.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerEntityRenderer(
        EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerBlockEntityRenderer(
            CustomBlockEntityTypes.HOLO_TABLE.get(),
            CustomEntityRenderers.HOLO_TABLE_ENTITY_RENDERER
        );
    }
}
