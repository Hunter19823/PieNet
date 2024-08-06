package pie.ilikepiefoo.pienet.component;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public final class BlockRenderPropertiesComponentStreamCodec implements StreamCodec<ByteBuf, List<BlockRenderPropertiesComponent>> {
    public static final BlockRenderPropertiesComponentStreamCodec INSTANCE = new BlockRenderPropertiesComponentStreamCodec();

    private BlockRenderPropertiesComponentStreamCodec() {
    }

    @Override
    public List<BlockRenderPropertiesComponent> decode(ByteBuf buffer) {
        int length = buffer.readInt();
        BlockRenderPropertiesComponent[] components = new BlockRenderPropertiesComponent[length];
        for (int i = 0; i < length; i++) {
            components[i] = BlockRenderPropertiesComponent.STREAM_CODEC.decode(buffer);
        }
        return List.of(components);
    }

    @Override
    public void encode(ByteBuf buffer, List<BlockRenderPropertiesComponent> value) {
        buffer.writeInt(value.size());
        for (BlockRenderPropertiesComponent blockRenderPropertiesComponent : value) {
            BlockRenderPropertiesComponent.STREAM_CODEC.encode(
                buffer,
                blockRenderPropertiesComponent
            );
        }
    }
}
