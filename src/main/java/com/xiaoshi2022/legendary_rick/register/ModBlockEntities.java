package com.xiaoshi2022.legendary_rick.register;

import com.xiaoshi2022.legendary_rick.block.client.RickPortalBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    // 注册一次即可
    public static final RegistryObject<BlockEntityType<RickPortalBlockEntity>> RICK_PORTAL =
            BLOCK_ENTITIES.register("rick_portal",
                    () -> BlockEntityType.Builder.of(
                            RickPortalBlockEntity::new,
                            ModBlocks.RICK_PORTAL.get(),
                            ModBlocks.RICK_PORTAL_FLOOR.get(),
                            ModBlocks.RICK_PORTAL_CEILING.get()
                    ).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}