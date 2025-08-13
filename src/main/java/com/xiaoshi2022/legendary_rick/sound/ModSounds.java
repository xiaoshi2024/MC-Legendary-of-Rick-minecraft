package com.xiaoshi2022.legendary_rick.sound;

import com.xiaoshi2022.legendary_rick.Legendary_Rick;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = 
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Legendary_Rick.MODID);


    public static final RegistryObject<SoundEvent> SHIT_KING_EAT = REGISTRY.register("shit_king_eating",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("legendary_rick", "shit_king_eating")));
    public static final RegistryObject<SoundEvent> RICKTOOLS_USING = REGISTRY.register("rick_tools_using"
            , () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("legendary_rick", "rick_tools_using")));
    public static final RegistryObject<SoundEvent> PORTAL_GUN_SHOOTING = REGISTRY.register("portal_gun_shooting"
            , () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("legendary_rick", "portal_gun_shooting")));
    public static final RegistryObject<SoundEvent> MINING_RICK_ORE = REGISTRY.register("mining_rick_ore"
            , () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("legendary_rick", "mining_rick_ore")));
    public static final RegistryObject<SoundEvent> AND_HERE_WE_GO = REGISTRY.register("and_here_we_go"
            , () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("legendary_rick", "and_here_we_go")));
    public static final RegistryObject<SoundEvent> PORTAL_FLUID_SOUND = REGISTRY.register("portal_fluid_sound"
            , () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("legendary_rick", "portal_fluid_sound")));
    public static final RegistryObject<SoundEvent> SHOW_BADGE = REGISTRY.register("show_badge"
            , () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("legendary_rick", "show_badge")));

}
