package com.li64.tide.registries.entities.misc.fishing;

import com.li64.tide.registries.TideEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HookAccessor extends FishingHook {
    private TideFishingHook hook = null;

    public HookAccessor(TideFishingHook hook, Level level) {
        super(TideEntityTypes.HOOK_ACCESSOR, level);
        this.hook = hook;
    }

    public HookAccessor(EntityType<? extends FishingHook> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean bobberRemoved(Player player) {
        return player == null || player.fishing == null || getHook(player) == null;
    }

    public void clearHook(Player player) {
        this.hook = null;
        player.fishing = null;
    }

    public boolean isFishing() {
        return hook != null;
    }

    public static TideFishingHook getHook(Player player) {
        if (player.fishing == null) return null;
        HookAccessor placeholder = ((HookAccessor)player.fishing);
        return placeholder.hook;
    }

    @Override
    public boolean isOpenWaterFishing() {
        return hook.isOpenWaterFishing();
    }

    @Override
    public double getX() {
        return hook.getX();
    }

    @Override
    public double getY() {
        return hook.getY();
    }

    @Override
    public double getZ() {
        return hook.getZ();
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        if (hook != null) hook.remove(reason);
        super.remove(reason);
    }

    @Override
    public @NotNull Vec3 position() {
        if (hook == null) return new Vec3(0, 0, 0);
        return hook.position();
    }

    @Override
    public @NotNull BlockPos blockPosition() {
        if (hook == null) return new BlockPos(0, 0, 0);
        return hook.blockPosition();
    }

    @Nullable
    @Override
    public Player getPlayerOwner() {
        if (hook == null) return null;
        return hook.getPlayerOwner();
    }
}
