package pie.ilikepiefoo.pienet.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;

import java.util.Objects;

public record BlockRenderPropertiesComponent(
    BlockPos pos,
    BlockState blockState
) {
    public static final Codec<BlockRenderPropertiesComponent> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BlockPos.CODEC.fieldOf("pos").forGetter(BlockRenderPropertiesComponent::pos),
            BlockState.CODEC.fieldOf("blockState").forGetter(BlockRenderPropertiesComponent::blockState)
        ).apply(
            instance,
            BlockRenderPropertiesComponent::new
        ));
    public static final StreamCodec<ByteBuf, BlockRenderPropertiesComponent> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC,
        BlockRenderPropertiesComponent::pos,
        ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY),
        BlockRenderPropertiesComponent::blockState,
        BlockRenderPropertiesComponent::new
    );

    public BlockRenderPropertiesComponent(
        BlockPos pos,
        HoloTableEntity holoTableEntity
    ) {
        this(
            pos.subtract(holoTableEntity.getBlockPos()),
            Objects.requireNonNull(holoTableEntity.getLevel()).getBlockState(pos)
        );
    }
}
