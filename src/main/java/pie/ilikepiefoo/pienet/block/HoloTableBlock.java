package pie.ilikepiefoo.pienet.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;

public class HoloTableBlock extends BaseEntityBlock {
    public static final Properties DEFAULT_PROPERTIES = BlockBehaviour
        .Properties
        .of()
        .isViewBlocking((state, world, pos) -> false);
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

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0f;
    }

    @Override
    protected @NotNull VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return super.getVisualShape(
            state,
            level,
            pos,
            context
        );
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}
