package pie.ilikepiefoo.pienet.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.world.AuxiliaryLightManager;
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
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
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
        return Blocks.CRAFTING_TABLE
            .defaultBlockState()
            .getVisualShape(
                level,
                pos,
                context
            );
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    /**
     * Get a light value for this block, taking into account the given state and coordinates, normal ranges are between 0 and 15
     *
     * @param state The state of this block
     * @param level The level this block is in, may be {@link EmptyBlockGetter#INSTANCE}, see implementation notes
     * @param pos   The position of this block in the level, may be {@link BlockPos#ZERO}, see implementation notes
     * @return The light value
     * @implNote <ul>
     * <li>
     * If the given state of this block may emit light but requires position context to determine the light
     * value, then it must return {@code true} from {@link #hasDynamicLightEmission(BlockState)}, otherwise
     * this method will be called with {@link EmptyBlockGetter#INSTANCE} and {@link BlockPos#ZERO} during
     * chunk generation or loading to determine whether a chunk may contain a light-emitting block,
     * resulting in erroneous data if it's determined with the given level and/or the given position.
     * </li>
     * <li>
     * The given {@link BlockGetter} may be a chunk. Block, fluid or block entity accesses outside of its bounds
     * will cause issues such as wrapping coordinates returning values from the opposing chunk edge
     * </li>
     * <li>
     * If the light value depends on data from a {@link BlockEntity} then the light level must be stored in
     * the {@link AuxiliaryLightManager} by the {@code BlockEntity} and retrieved from the
     * {@code AuxiliaryLightManager} in this method. This is to ensure thread-safety and availability of
     * the data during chunk load from disk.
     * </li>
     * </ul>
     */
    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }
}
