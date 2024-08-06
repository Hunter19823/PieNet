package pie.ilikepiefoo.pienet.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import pie.ilikepiefoo.pienet.BlockCollectionAlgorithms;
import pie.ilikepiefoo.pienet.component.BlockRenderPropertiesComponent;
import pie.ilikepiefoo.pienet.component.CustomDataComponents;
import pie.ilikepiefoo.pienet.component.TriVector;
import pie.ilikepiefoo.pienet.util.Cache;
import pie.ilikepiefoo.pienet.util.CacheGroup;

import java.util.List;

public class HoloTableEntity extends BlockEntity {
    private final CacheGroup CACHE_GROUP = new CacheGroup();
    private TriVector<Float> visualContainerSize;
    private TriVector<Integer> scanningAreaSize;
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
    private TriVector<Float> hologramOffset;
    private final Cache<Vector3f> CENTER_OFFSET = CACHE_GROUP.lazy(() -> new Vector3f(
        getScaledContainerSize().x * ((this.getMatrixArea().getX() * 2f - 2) / (this.getVisualContainerSize()
            .x() * 2f)),
        ((this.getMatrixArea().getY() * getScaledContainerSize().y) + 2),
        getScaledContainerSize().z * ((this.getMatrixArea().getZ() * 2f - 2) / (this.getVisualContainerSize().z() * 2f))
    ).add(TriVector.toVector3f(this.hologramOffset)));
    private final Cache<AABB> RENDER_BOUNDING_BOX = CACHE_GROUP.lazy(() -> new AABB(
        new Vec3(
            this.getBlockPos().getX() + getHologramOffset().x - getVisualContainerSize().x() / 2,
            this.getBlockPos().getY() + getHologramOffset().y - getVisualContainerSize().y() / 2,
            this.getBlockPos().getZ() + getHologramOffset().z - getVisualContainerSize().z() / 2
        ),
        new Vec3(
            this.getBlockPos().getX() + getHologramOffset().x + getVisualContainerSize().x() / 2,
            this.getBlockPos().getY() + getHologramOffset().y + getVisualContainerSize().y() / 2,
            this.getBlockPos().getZ() + getHologramOffset().z + getVisualContainerSize().z() / 2
        )
    ).minmax(new AABB(this.getBlockPos())));
    private List<BlockRenderPropertiesComponent> blockRenderProperties;

    public HoloTableEntity(BlockPos pos, BlockState blockState) {
        super(
            CustomBlockEntityTypes.HOLO_TABLE.get(),
            pos,
            blockState
        );
        this.setVisualContainerSize(new TriVector<>(
            1f,
            0.5f,
            1f
        ));
        this.setScanningAreaSize(new TriVector<>(
            32,
            16,
            32
        ));
        this.setHologramOffset(new TriVector<>(
            0f,
            0f,
            0f
        ));
        this.setBlockRenderProperties(List.of());
    }

    /**
     * Called when this is first added to the world (by {@link LevelChunk#addAndRegisterBlockEntity(BlockEntity)})
     * or right before the first tick when the chunk is generated or loaded from disk.
     * Override instead of adding {@code if (firstTick)} stuff in update.
     */
    @Override
    public void onLoad() {
        super.onLoad();
    }


    public Vector3f getScaledContainerSize() {
        return SCALE.get();
    }

    public void setScanningAreaSize(TriVector<Integer> scanningAreaSize) {
        if (scanningAreaSize == null) {
            scanningAreaSize = new TriVector<>(
                32,
                16,
                32
            );
        }
        this.scanningAreaSize = scanningAreaSize;
        CACHE_GROUP.update();
    }

    public Vec3i getMatrixArea() {
        return MATRIX_AREA.get();
    }

    public Vector3f getHologramOffset() {
        return CENTER_OFFSET.get();
    }

    public void setHologramOffset(TriVector<Float> hologramOffset) {
        if (hologramOffset == null) {
            hologramOffset = new TriVector<>(
                0f,
                0f,
                0f
            );
        }
        this.hologramOffset = hologramOffset;
        CACHE_GROUP.update();
    }

    public void setBlockRenderProperties(List<BlockRenderPropertiesComponent> blockRenderProperties) {
        if (blockRenderProperties == null) {
            blockRenderProperties = List.of();
        }
        this.blockRenderProperties = blockRenderProperties;
        CACHE_GROUP.update();
    }

    public TriVector<Float> getVisualContainerSize() {
        return this.visualContainerSize;
    }

    public AABB getScanningBoundingBox() {
        return SCANNING_BOUNDING_BOX.get();
    }

    public AABB getRenderBoundingBox() {
        return RENDER_BOUNDING_BOX.get();
    }

    public void setVisualContainerSize(TriVector<Float> visualContainerSize) {
        if (visualContainerSize == null) {
            visualContainerSize = new TriVector<>(
                1f,
                0.5f,
                1f
            );
        }
        this.visualContainerSize = visualContainerSize;
        CACHE_GROUP.update();
    }

    public List<BlockRenderPropertiesComponent> getArea() {
        if (blockRenderProperties == null && this.getLevel() != null) {
            this.setBlockRenderProperties(BlockCollectionAlgorithms.EXPOSED_BLOCKS.collectBlocks(
                this.getLevel(),
                this
            ));
        }
        if (blockRenderProperties == null) {
            this.setBlockRenderProperties(null);
        }
        return blockRenderProperties == null ? List.of() : blockRenderProperties;
    }

    public BlockPos getTopRight() {
        return this.getBlockPos().offset(
            scanningAreaSize.x(),
            scanningAreaSize.y(),
            scanningAreaSize.z()
        );
    }

    public BlockPos getBottomLeft() {
        return this.getBlockPos().offset(
            -scanningAreaSize.x(),
            -scanningAreaSize.y(),
            -scanningAreaSize.z()
        );
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(
            tag,
            registries
        );

        CustomDataComponents.HOLO_TABLE_SCANNED_AREA_SIZE.get().codecOrThrow().decode(
            NbtOps.INSTANCE,
            tag
        ).ifSuccess((size) -> this.setScanningAreaSize(size.getFirst()));

        CustomDataComponents.HOLO_TABLE_VISUAL_SIZE.get().codecOrThrow().decode(
            NbtOps.INSTANCE,
            tag
        ).ifSuccess((size) -> this.setVisualContainerSize(size.getFirst()));

        CustomDataComponents.HOLO_TABLE_HOLOGRAM_OFFSET.get().codecOrThrow().decode(
            NbtOps.INSTANCE,
            tag
        ).ifSuccess((size) -> this.setHologramOffset(size.getFirst()));

        CustomDataComponents.BLOCK_RENDER_PROPERTIES.get().codecOrThrow().decode(
            NbtOps.INSTANCE,
            tag
        ).ifSuccess((size) -> this.setBlockRenderProperties(size.getFirst()));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(
            tag,
            registries
        );
        CustomDataComponents.HOLO_TABLE_SCANNED_AREA_SIZE.get().codecOrThrow().encode(
            this.scanningAreaSize,
            NbtOps.INSTANCE,
            tag
        );
        CustomDataComponents.HOLO_TABLE_VISUAL_SIZE.get().codecOrThrow().encode(
            this.visualContainerSize,
            NbtOps.INSTANCE,
            tag
        );
        CustomDataComponents.HOLO_TABLE_HOLOGRAM_OFFSET.get().codecOrThrow().encode(
            this.hologramOffset,
            NbtOps.INSTANCE,
            tag
        );
        CustomDataComponents.BLOCK_RENDER_PROPERTIES.get().codecOrThrow().encode(
            this.blockRenderProperties,
            NbtOps.INSTANCE,
            tag
        );
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithFullMetadata(registries);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.@NotNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.setVisualContainerSize(componentInput.get(CustomDataComponents.HOLO_TABLE_VISUAL_SIZE));
        this.setScanningAreaSize(componentInput.get(CustomDataComponents.HOLO_TABLE_SCANNED_AREA_SIZE));
        this.setHologramOffset(componentInput.get(CustomDataComponents.HOLO_TABLE_HOLOGRAM_OFFSET));
        this.setBlockRenderProperties(componentInput.get(CustomDataComponents.BLOCK_RENDER_PROPERTIES));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
        super.collectImplicitComponents(components);
        components.set(
            CustomDataComponents.HOLO_TABLE_VISUAL_SIZE,
            this.visualContainerSize
        );
        components.set(
            CustomDataComponents.HOLO_TABLE_SCANNED_AREA_SIZE,
            this.scanningAreaSize
        );
        components.set(
            CustomDataComponents.HOLO_TABLE_HOLOGRAM_OFFSET,
            this.hologramOffset
        );
        components.set(
            CustomDataComponents.BLOCK_RENDER_PROPERTIES,
            this.blockRenderProperties
        );
    }
}
