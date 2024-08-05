package pie.ilikepiefoo.pienet.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HoloTableEntity extends BlockEntity {

    public HoloTableEntity(BlockPos pos, BlockState blockState) {
        super(
            CustomBlockEntityTypes.HOLO_TABLE.get(),
            pos,
            blockState
        );
    }
}
