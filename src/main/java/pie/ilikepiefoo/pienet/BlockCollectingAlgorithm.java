package pie.ilikepiefoo.pienet;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import pie.ilikepiefoo.pienet.block.entity.HoloTableEntity;
import pie.ilikepiefoo.pienet.component.BlockRenderPropertiesComponent;

import java.util.List;

public interface BlockCollectingAlgorithm {
    @NotNull
    List<BlockRenderPropertiesComponent> collectBlocks(@NotNull Level level, @NotNull HoloTableEntity holoTableEntity);
}
