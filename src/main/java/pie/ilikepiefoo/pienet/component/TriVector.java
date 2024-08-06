package pie.ilikepiefoo.pienet.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.joml.Vector3f;

public record TriVector<T>(
    T first,
    T second,
    T third
) {
    public static final Codec<TriVector<Integer>> CODEC_3i = codec(Codec.INT);
    public static final Codec<TriVector<Float>> CODEC_3f = codec(Codec.FLOAT);
    public static final StreamCodec<ByteBuf, TriVector<Integer>> STREAM_CODEC_3i = streamCodec(ByteBufCodecs.INT);
    public static final StreamCodec<ByteBuf, TriVector<Float>> STREAM_CODEC_3f = streamCodec(ByteBufCodecs.FLOAT);
    public TriVector(
        T all
    ) {
        this(all,
            all,
            all);
    }

    public static <T> Codec<TriVector<T>> codec(Codec<T> codec) {
        return RecordCodecBuilder.create(instance ->
            instance.group(
                codec.fieldOf("first").forGetter(TriVector::first),
                codec.fieldOf("second").forGetter(TriVector::second),
                codec.fieldOf("third").forGetter(TriVector::third)
            ).apply(
                instance,
                TriVector::new
            ));
    }

    public static <T> StreamCodec<ByteBuf, TriVector<T>> streamCodec(StreamCodec<ByteBuf, T> streamCodec) {
        return StreamCodec.composite(
            streamCodec,
            TriVector::first,
            streamCodec,
            TriVector::second,
            streamCodec,
            TriVector::third,
            TriVector::new
        );
    }

    public static Vector3f toVector3f(TriVector<Float> triVector) {
        return new Vector3f(triVector.x(),
            triVector.y(),
            triVector.z());
    }

    public T x() {
        return first;
    }

    public T y() {
        return second;
    }

    public T z() {
        return third;
    }

    public static BlockPos toBlockPos(TriVector<Integer> triVector) {
        return new BlockPos(triVector.x(),
            triVector.y(),
            triVector.z());
    }


}
