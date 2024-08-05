package pie.ilikepiefoo.pienet.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class HoloTable extends Block {
    public static final Properties DEFAULT_PROPERTIES = BlockBehaviour
        .Properties
        .of();

    public HoloTable(Properties properties) {
        super(properties);
    }
}
