package com.xiaoshi2022.legendary_rick.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    // ================= 瑞克传奇专属栏 =================
    public static final RegistryObject<CreativeModeTab> LEGENDARY_RICK_TAB = TABS.register(
            "legendary_rick_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> ModItems.RICK_PORTAL_ITEM.get().getDefaultInstance()) // 栏图标
                    .title(Component.translatable("itemGroup.legendary_rick"))         // 栏名称
                    .displayItems((params, output) -> {
                        // 物品
                        output.accept(ModItems.RICK_PORTAL_ITEM.get());
                        // 2) Floor
                        ItemStack floorStack = new ItemStack(ModItems.RICK_PORTAL_ITEM.get());
                        floorStack.getOrCreateTag().putString("PortalVariant", "floor");
                        output.accept(floorStack);

                        // 3) Ceiling
                        ItemStack ceilingStack = new ItemStack(ModItems.RICK_PORTAL_ITEM.get());
                        ceilingStack.getOrCreateTag().putString("PortalVariant", "ceiling");
                        output.accept(ceilingStack);
                        output.accept(ModItems.RICK_PORTAL_GUN.get());
                    })
                    .build());

}
