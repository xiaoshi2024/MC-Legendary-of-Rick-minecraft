package com.xiaoshi2022.legendary_rick.register;

import com.xiaoshi2022.legendary_rick.Items.RickPortalGun;
import com.xiaoshi2022.legendary_rick.Items.RickPortalItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

public class ModItems {
    // 1. 创建物品注册器
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    // 2. 只注册传送门方块的物品形式
    public static final RegistryObject<RickPortalItem> RICK_PORTAL_ITEM =
            ITEMS.register("rick_portal",
                    () -> new RickPortalItem(new Item.Properties()
            ));

    // ModItems.java
    public static final RegistryObject<RickPortalGun> RICK_PORTAL_GUN =
            ITEMS.register("rick_portal_gun", ()-> new RickPortalGun(new Item.Properties().stacksTo(1)));

    // 3. 注册方法
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}