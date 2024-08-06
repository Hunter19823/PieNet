package pie.ilikepiefoo.pienet.component;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import pie.ilikepiefoo.pienet.PieNet;

import java.util.List;

public interface CustomDataComponents {
    DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(
        PieNet.MODID
    );

    DeferredHolder<DataComponentType<?>, DataComponentType<TriVector<Float>>> HOLO_TABLE_VISUAL_SIZE = DATA_COMPONENTS.registerComponentType(
        "holo_table_visual_size",
        builder -> builder
            .persistent(TriVector.CODEC_3f)
            .networkSynchronized(TriVector.STREAM_CODEC_3f)
    );
    DeferredHolder<DataComponentType<?>, DataComponentType<TriVector<Integer>>> HOLO_TABLE_SCANNED_AREA_SIZE = DATA_COMPONENTS.registerComponentType(
        "holo_table_scanned_area_size",
        builder -> builder
            .persistent(TriVector.CODEC_3i)
            .networkSynchronized(TriVector.STREAM_CODEC_3i)
    );

    DeferredHolder<DataComponentType<?>, DataComponentType<TriVector<Float>>> HOLO_TABLE_HOLOGRAM_OFFSET = DATA_COMPONENTS.registerComponentType(
        "holo_table_hologram_offset",
        builder -> builder
            .persistent(TriVector.CODEC_3f)
            .networkSynchronized(TriVector.STREAM_CODEC_3f)
    );

    DeferredHolder<DataComponentType<?>, DataComponentType<List<BlockRenderPropertiesComponent>>> BLOCK_RENDER_PROPERTIES = DATA_COMPONENTS.registerComponentType(
        "hologram_grid",
        builder -> builder
            .persistent(BlockRenderPropertiesComponent.CODEC.listOf())
            .networkSynchronized(BlockRenderPropertiesComponentStreamCodec.INSTANCE)
    );


}
