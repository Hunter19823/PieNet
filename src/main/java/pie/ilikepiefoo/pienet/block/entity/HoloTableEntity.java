package pie.ilikepiefoo.pienet.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.stream.Stream;

public class HoloTableEntity extends BlockEntity {

    public HoloTableEntity(BlockPos pos, BlockState blockState) {
        super(
            CustomBlockEntityTypes.HOLO_TABLE.get(),
            pos,
            blockState
        );
    }

    public float getScaledContainerSize() {
        return this.getVisualContainerSize() / (float) this.getMatrixSize();
    }

    public float getVisualContainerSize() {
        return 3f;
    }

    public int getMatrixSize() {
        return 9;
    }

    public Stream<Pair<BlockPos, BlockState>> getArea() {
        if (this.getLevel() == null) {
            return Stream.empty();
        }

        return BlockPos
            .betweenClosedStream(
                this
                    .getBlockPos()
                    .offset(-getMatrixSize(),
                            -getMatrixSize(),
                            -getMatrixSize()
                    ),
                this
                    .getBlockPos()
                    .offset(getMatrixSize(),
                            getMatrixSize(),
                            getMatrixSize()
                    )
            )
            .map(
                (pos) -> Pair.of(
                    pos.subtract(this.getBlockPos()),
                    this
                        .getLevel()
                        .getBlockState(pos)
                )
            );
    }
}
