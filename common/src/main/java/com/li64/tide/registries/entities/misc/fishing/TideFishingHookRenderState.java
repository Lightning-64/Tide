package com.li64.tide.registries.entities.misc.fishing;

import com.li64.tide.registries.TideItems;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class TideFishingHookRenderState extends EntityRenderState {
    public ItemStack bobber;
    public ItemStack line;
    public ItemStack hook;

    public Vec3 lineOriginOffset;
    public BlockPos ownerPos;
    public float initialYaw;
    public float partialTick;

    public TideFishingHookRenderState() {
        this.bobber = new ItemStack(TideItems.RED_FISHING_BOBBER);
        this.line = new ItemStack(TideItems.FISHING_LINE);
        this.hook = new ItemStack(TideItems.FISHING_HOOK);

        this.lineOriginOffset = Vec3.ZERO;
        this.ownerPos = BlockPos.ZERO;
        this.initialYaw = 0.0f;
        this.partialTick = 0.0f;
    }
}
