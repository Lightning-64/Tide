package com.li64.tide.registries.entities.misc.fishing;

import com.li64.tide.Tide;
import com.li64.tide.registries.TideEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
        HookAccessor placeholder = ((HookAccessor)player.fishing);
        if (placeholder != null) return placeholder.hook;
        else return null;
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
    public void remove(RemovalReason reason) {
        hook.remove(reason);
        super.remove(reason);
    }

    @Override
    public Vec3 position() {
        return hook.position();
    }

    @Override
    public BlockPos blockPosition() {
        return hook.blockPosition();
    }

    @Nullable
    @Override
    public Player getPlayerOwner() {
        return hook.getPlayerOwner();
    }
}
