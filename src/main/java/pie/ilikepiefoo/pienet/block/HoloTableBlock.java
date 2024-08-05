package pie.ilikepiefoo.pienet.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;

public class HoloTableBlock extends BaseEntityBlock {
    public static final Properties DEFAULT_PROPERTIES = BlockBehaviour
        .Properties
        .of();
    public static final MapCodec<? extends BaseEntityBlock> CODEC = BlockBehaviour.simpleCodec(
        HoloTableBlock::new
    );

    public HoloTableBlock() {
        this(DEFAULT_PROPERTIES);
    }

    public HoloTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new HoloTableEntity(
            pos,
            state
        );
    }
}
