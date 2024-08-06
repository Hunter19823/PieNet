package pie.ilikepiefoo.pienet.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;

@OnlyIn(Dist.CLIENT)
public class HoloTableEntityRenderer implements BlockEntityRenderer<HoloTableEntity> {
    private final BlockEntityRendererProvider.Context context;
    private final BlockRenderDispatcher blockRenderDispatcher;

    public HoloTableEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(@NotNull HoloTableEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() == null) {
            return;
        }
        poseStack.pushPose();
        blockRenderDispatcher.renderSingleBlock(
            Blocks.CRAFTING_TABLE.defaultBlockState(),
            poseStack,
            bufferSource,
            packedLight,
            packedOverlay,
            ModelData.EMPTY,
            RenderType.solid()
        );
        poseStack.popPose();
        Vector3f scale = blockEntity.getScaledContainerSize();
        Vector3f hologramOffset = blockEntity.getHologramOffset();
        for (Pair<BlockPos, BlockState> pair : blockEntity.getArea()) {
            poseStack.pushPose();
            poseStack.translate(
                pair.getFirst().getX() * scale.x + hologramOffset.x,
                pair.getFirst().getY() * scale.y + hologramOffset.y,
                pair.getFirst().getZ() * scale.z + hologramOffset.z
            );
            poseStack.scale(
                scale.x,
                scale.y,
                scale.z
            );
            blockRenderDispatcher.renderSingleBlock(
                pair.getSecond(),
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay,
                ModelData.EMPTY,
                RenderType.cutoutMipped()
            );
            poseStack.popPose();
        }
    }
}
