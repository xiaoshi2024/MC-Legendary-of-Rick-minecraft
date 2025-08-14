package com.xiaoshi2022.legendary_rick.util;

import com.xiaoshi2022.legendary_rick.register.ModBlocks;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum PortalVariant implements StringRepresentable {
    WALL("wall", ModBlocks.RICK_PORTAL),
    FLOOR("floor", ModBlocks.RICK_PORTAL_FLOOR),
    CEILING("ceiling", ModBlocks.RICK_PORTAL_CEILING);

    private final String name;
    private final Supplier<Block> block;

    PortalVariant(String name, Supplier<Block> block) {
        this.name = name;
        this.block = block;
    }

    public Block getBlock() { return block.get(); }

    @Override public String getSerializedName() { return name; }

    public PortalVariant next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public static PortalVariant fromName(String name) {
        for (PortalVariant v : values()) if (v.name.equals(name)) return v;
        return WALL;
    }
}
