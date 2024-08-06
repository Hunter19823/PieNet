package pie.ilikepiefoo.pienet.component;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

import java.util.List;

public record HoloTableComponent(
    BlockPos visualContainerSize,
    BlockPos matrixArea,
    Vector3f hologramOffset,
    List<Pair<BlockPos, BlockState>> area
) {
    public static final Codec<Vector3f> VECTOR3F_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.FLOAT.fieldOf("x").forGetter(Vector3f::x),
        Codec.FLOAT.fieldOf("y").forGetter(Vector3f::y),
        Codec.FLOAT.fieldOf("z").forGetter(Vector3f::z)
    ).apply(
        instance,
        Vector3f::new
    ));
    public static final Codec<HoloTableComponent> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BlockPos.CODEC.fieldOf("hologram_visual_size").forGetter(HoloTableComponent::visualContainerSize),
            BlockPos.CODEC.fieldOf("matrix_area").forGetter(HoloTableComponent::matrixArea),
            VECTOR3F_CODEC.fieldOf("hologram_offset").forGetter(HoloTableComponent::hologramOffset),
            Codec.list(pairCodec(
                BlockPos.CODEC,
                BlockState.CODEC
            )).fieldOf("area").forGetter(HoloTableComponent::area)
        ).apply(
            instance,
            HoloTableComponent::new
        ));

    public static <A, B> Codec<Pair<A, B>> pairCodec(Codec<A> a, Codec<B> b) {
        return RecordCodecBuilder.create(instance -> instance.group(
            a.fieldOf("left").forGetter(Pair::getFirst),
            b.fieldOf("right").forGetter(Pair::getSecond)
        ).apply(
            instance,
            Pair::of
        ));
    }
}
