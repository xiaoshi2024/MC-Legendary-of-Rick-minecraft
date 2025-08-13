package com.xiaoshi2022.legendary_rick.register;

import com.xiaoshi2022.legendary_rick.entity.RickPortalEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "legendary_rick");

    public static final RegistryObject<EntityType<RickPortalEntity>> RICK_PORTAL =
            ENTITIES.register("rick_portal",
                    () -> EntityType.Builder.<RickPortalEntity>of(RickPortalEntity::new, MobCategory.MISC)
                            .sized(1.5F, 2.5F)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("rick_portal")
            );

    public static void register(IEventBus bus) { ENTITIES.register(bus); }
}