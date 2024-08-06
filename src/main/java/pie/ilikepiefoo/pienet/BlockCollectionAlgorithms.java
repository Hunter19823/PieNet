package pie.ilikepiefoo.pienet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import pie.ilikepiefoo.pienet.component.BlockRenderPropertiesComponent;

public interface BlockCollectionAlgorithms {
    BlockCollectingAlgorithm EXPOSED_BLOCKS = (level, holoTableEntity) -> BlockPos.betweenClosedStream(
        holoTableEntity.getBottomLeft(),
        holoTableEntity.getTopRight()
    ).filter((pos -> !level.getBlockState(pos).isAir())).map((pos) -> new BlockRenderPropertiesComponent(
        pos,
        holoTableEntity
    )).filter((pair) -> {
        for (Direction direction : Direction.values()) {
            var relative = pair.pos().offset(holoTableEntity.getBlockPos()).relative(direction);
            var state = level.getBlockState(relative);
            if (state.isEmpty() || state.isAir()) {
                return true;
            }
            if (!state.isViewBlocking(
                level,
                relative
            )) {
                return true;
            }
        }
        return false;
    }).toList();
}
