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
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;

import java.util.function.Consumer;

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
        blockRenderDispatcher.renderSingleBlock(
            Blocks.CRAFTING_TABLE.defaultBlockState(),
            poseStack,
            bufferSource,
            packedLight,
            packedOverlay,
            ModelData.EMPTY,
            RenderType.solid()
        );
        float scale = blockEntity.getScaledContainerSize();
        float centering_offset = -scale;
        blockEntity
            .getArea()
            .forEach(renderBlockState(
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay,
                scale,
                centering_offset,
                blockEntity.getMatrixSize()
            ));
    }

    private @NotNull Consumer<Pair<BlockPos, BlockState>> renderBlockState(
        @NotNull PoseStack poseStack,
        @NotNull MultiBufferSource bufferSource,
        int packedLight,
        int packedOverlay,
        float scale,
        float centering_offset,
        int matrixSize
    ) {
        return (pair) -> {
            poseStack.pushPose();
            poseStack.translate(
                pair
                    .getFirst()
                    .getX() * scale - centering_offset,
                pair
                    .getFirst()
                    .getY() * scale + (matrixSize * scale) + 1,
                pair
                    .getFirst()
                    .getZ() * scale - centering_offset
            );
            poseStack.scale(
                scale,
                scale,
                scale
            );
            blockRenderDispatcher.renderSingleBlock(
                pair.getSecond(),
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay,
                ModelData.EMPTY,
                RenderType.SOLID
            );
            poseStack.popPose();
        };
    }
}
