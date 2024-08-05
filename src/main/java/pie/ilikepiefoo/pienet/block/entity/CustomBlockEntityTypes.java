package pie.ilikepiefoo.pienet.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import pie.ilikepiefoo.pienet.PieNet;
import pie.ilikepiefoo.pienet.block.CustomBlocks;

public interface CustomBlockEntityTypes {
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        PieNet.MODID
    );


    DeferredHolder<BlockEntityType<?>, BlockEntityType<HoloTableEntity>> HOLO_TABLE = BLOCK_ENTITY_TYPES.register(
        "holo_table",
        () -> BlockEntityType
            .Builder
            .of(
                HoloTableEntity::new,
                CustomBlocks.HOLO_TABLE.get()
            )
            .build(null)
    );
}
