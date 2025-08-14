package com.xiaoshi2022.legendary_rick.register;

import com.xiaoshi2022.legendary_rick.block.RickPortalBlock;
import com.xiaoshi2022.legendary_rick.block.RickPortalCeilingBlock;
import com.xiaoshi2022.legendary_rick.block.RickPortalFloorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS,MOD_ID);

    public static final RegistryObject<Block> RICK_PORTAL = BLOCKS.register("rick_portal",
            () -> new RickPortalBlock(BlockBehaviour.Properties.of()
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noCollission()
                    .lightLevel(state -> 11)
                    .sound(SoundType.GLASS)));
    public static final RegistryObject<Block> RICK_PORTAL_FLOOR =
            BLOCKS.register("rick_portal_floor",
                    () -> new RickPortalFloorBlock(
                            Block.Properties.copy(Blocks.STONE)
                                    .noOcclusion()
                                    .noCollission()
                            .lightLevel(state -> 11)
                            .sound(SoundType.GLASS)));

    public static final RegistryObject<Block> RICK_PORTAL_CEILING =
            BLOCKS.register("rick_portal_ceiling",
                    () -> new RickPortalCeilingBlock(
                            Block.Properties.copy(Blocks.STONE)
                                    .noOcclusion()
                                    .noCollission()
                                    .lightLevel(state -> 11)
                                    .sound(SoundType.GLASS)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}