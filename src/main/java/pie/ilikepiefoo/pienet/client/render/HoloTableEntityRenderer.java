package pie.ilikepiefoo.pienet.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;

@OnlyIn(Dist.CLIENT)
public class HoloTableEntityRenderer implements BlockEntityRenderer<HoloTableEntity> {
    private final BlockEntityRendererProvider.Context context;

    public HoloTableEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(@NotNull HoloTableEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

    }
}
