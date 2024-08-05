package pie.ilikepiefoo.pienet.client.render;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;

@OnlyIn(Dist.CLIENT)
public interface CustomEntityRenderers {
    BlockEntityRendererProvider<HoloTableEntity> HOLO_TABLE_ENTITY_RENDERER = HoloTableEntityRenderer::new;

}
