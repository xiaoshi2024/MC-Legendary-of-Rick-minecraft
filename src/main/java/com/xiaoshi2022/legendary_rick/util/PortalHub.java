package com.xiaoshi2022.legendary_rick.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class PortalHub {

    public record PortalInfo(ResourceKey<Level> dimension, BlockPos pos) {}

    private static final List<PortalInfo> PORTALS = new ArrayList<>();

    public static void add(ServerLevel level, BlockPos pos) {
        if (PORTALS.size() >= 2) PORTALS.remove(0); // 覆盖最早
        PORTALS.add(new PortalInfo(level.dimension(), pos));
    }

    public static void remove(ServerLevel level, BlockPos pos) {
        PORTALS.removeIf(p -> p.dimension().equals(level.dimension()) && p.pos().equals(pos));
    }

    public static Optional<PortalInfo> getTarget(ServerLevel srcLevel, BlockPos srcPos) {
        if (PORTALS.isEmpty()) return Optional.empty();
        if (PORTALS.size() == 1) return Optional.of(PORTALS.get(0));

        return PORTALS.stream()
                .filter(p -> !(p.dimension().equals(srcLevel.dimension()) && p.pos().equals(srcPos)))
                .findFirst();
    }

    public static int count() {
        return PORTALS.size();
    }
}