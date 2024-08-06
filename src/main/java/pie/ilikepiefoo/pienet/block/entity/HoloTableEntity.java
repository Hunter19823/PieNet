package pie.ilikepiefoo.pienet.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import pie.ilikepiefoo.pienet.util.Cache;
import pie.ilikepiefoo.pienet.util.CacheGroup;

import java.util.List;

public class HoloTableEntity extends BlockEntity {
    private static Vec3i scanningArea = new Vec3i(
        3,
        3,
        3
    );
    private static Vector3f renderArea = new Vector3f(
        3,
        3,
        3
    );
    private final CacheGroup CACHE_GROUP = new CacheGroup();
    private final Cache<Vec3i> MATRIX_AREA = CACHE_GROUP.lazy(() -> new Vec3i(
        (Math.abs(this.getBottomLeft().getX() - this.getTopRight().getX())) / 2,
        (Math.abs(this.getBottomLeft().getY() - this.getTopRight().getY())) / 2,
        (Math.abs(this.getBottomLeft().getZ() - this.getTopRight().getZ())) / 2
    ));
    private final Cache<Vector3f> SCALE = CACHE_GROUP.lazy(() -> new Vector3f(
        this.getVisualContainerSize().x() / (this.getMatrixArea().getX() * 2f + 1f),
        this.getVisualContainerSize().y() / (this.getMatrixArea().getY() * 2f + 1f),
        this.getVisualContainerSize().z() / (this.getMatrixArea().getZ() * 2f + 1f)
    ));
    private final Cache<Vector3f> CENTER_OFFSET = CACHE_GROUP.lazy(() -> new Vector3f(
        getScaledContainerSize().x * ((this.getMatrixArea().getX() * 2f - 2) / (this.getVisualContainerSize().x * 2f)),
        ((this.getMatrixArea().getY() * getScaledContainerSize().y) + 2),
        getScaledContainerSize().z * ((this.getMatrixArea().getZ() * 2f - 2) / (this.getVisualContainerSize().z * 2f))
    ));
    private final Cache<AABB> RENDER_BOUNDING_BOX = CACHE_GROUP.lazy(() -> new AABB(
        new Vec3(
            this.getBlockPos().getX() + getHologramOffset().x - getVisualContainerSize().x / 2,
            this.getBlockPos().getY() + getHologramOffset().y - getVisualContainerSize().y / 2,
            this.getBlockPos().getZ() + getHologramOffset().z - getVisualContainerSize().z / 2
        ),
        new Vec3(
            this.getBlockPos().getX() + getHologramOffset().x + getVisualContainerSize().x / 2,
            this.getBlockPos().getY() + getHologramOffset().y + getVisualContainerSize().y / 2,
            this.getBlockPos().getZ() + getHologramOffset().z + getVisualContainerSize().z / 2
        )
    ).minmax(new AABB(this.getBlockPos())));
    private final Cache<List<Pair<BlockPos, BlockState>>> BLOCK_LIST = CACHE_GROUP.lazy(() -> {
        if (this.getLevel() == null) {
            return List.of();
        }
        // Set the scan point at the top middle point of the scanning area.
        Vec3 scanPoint = Vec3.atLowerCornerOf(this.getBlockPos().above(scanningArea.getY()));

        return BlockPos.betweenClosedStream(
            this.getBottomLeft(),
            this.getTopRight()
        ).filter((pos -> !this.getLevel().getBlockState(pos).isAir())).map((pos) -> Pair.of(
            pos.subtract(this.getBlockPos()),
            this.getLevel()
                .getBlockState(pos)
                .getStateAtViewpoint(this.getLevel(),
                    pos,
                    scanPoint
                )
        )).filter((pair) -> {
            for (Direction direction : Direction.values()) {
                var relative = pair.getFirst().offset(this.getBlockPos()).relative(direction);
                var state = this.getLevel().getBlockState(relative);
                if (state.isEmpty() || state.isAir()) {
                    return true;
                }
                if (!state.isViewBlocking(
                    this.getLevel(),
                    relative
                )) {
                    return true;
                }
            }
            return false;
        }).toList();
    });
    private final Cache<AABB> SCANNING_BOUNDING_BOX = CACHE_GROUP.lazy(() -> new AABB(
        new Vec3(
            this.getBottomLeft().getX(),
            this.getBottomLeft().getY(),
            this.getBottomLeft().getZ()
        ),
        new Vec3(
            this.getTopRight().getX(),
            this.getTopRight().getY(),
            this.getTopRight().getZ()
        )
    ));

    public HoloTableEntity(BlockPos pos, BlockState blockState) {
        super(
            CustomBlockEntityTypes.HOLO_TABLE.get(),
            pos,
            blockState
        );
    }

    /**
     * Called when this is first added to the world (by {@link LevelChunk#addAndRegisterBlockEntity(BlockEntity)})
     * or right before the first tick when the chunk is generated or loaded from disk.
     * Override instead of adding {@code if (firstTick)} stuff in update.
     */
    @Override
    public void onLoad() {
        super.onLoad();
        this.setScanningArea(
            32,
            16,
            32
        );
        this.setRenderArea(
            1,
            0.5f,
            1
        );
    }

    public void setScanningArea(int x, int y, int z) {
        scanningArea = new Vec3i(
            x,
            y,
            z
        );
        CACHE_GROUP.update();
    }

    public void setRenderArea(float x, float y, float z) {
        renderArea = new Vector3f(
            x,
            y,
            z
        );
        CACHE_GROUP.update();
    }

    public Vector3f getScaledContainerSize() {
        return SCALE.get();
    }

    public Vector3f getVisualContainerSize() {
        return renderArea;
    }

    public Vec3i getMatrixArea() {
        return MATRIX_AREA.get();
    }

    public Vector3f getHologramOffset() {
        return CENTER_OFFSET.get();
    }

    public List<Pair<BlockPos, BlockState>> getArea() {
        return BLOCK_LIST.get();
    }

    public BlockPos getTopRight() {
        return this.getBlockPos().offset(
            scanningArea.getX(),
            scanningArea.getY(),
            scanningArea.getZ()
        );
    }

    public BlockPos getBottomLeft() {
        return this.getBlockPos().offset(
            -scanningArea.getX(),
            -scanningArea.getY(),
            -scanningArea.getZ()
        );
    }

    public AABB getScanningBoundingBox() {
        return SCANNING_BOUNDING_BOX.get();
    }

    public AABB getRenderBoundingBox() {
        return RENDER_BOUNDING_BOX.get();
    }


}
