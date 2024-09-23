package com.li64.tide.mixin;

import com.li64.tide.data.TideEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class TideEntityMixin implements TideEntity {
    @Unique
    public CompoundTag playerData = new CompoundTag();
    @Unique
    public String dataKey = "TidePersistentData";

    @Inject(method = "load(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("HEAD"))
    private void load(CompoundTag compound, CallbackInfo callbackInfo) {
        if (!compound.contains(dataKey)) compound.put(dataKey, playerData);
        else playerData = compound.getCompound(dataKey);
    }

    @Inject(method = "saveWithoutId(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", at = @At("HEAD"))
    private void saveWithoutId(CompoundTag compound, CallbackInfoReturnable<CompoundTag> callbackInfo) {
        compound.put(dataKey, playerData);
    }

    @Override
    public CompoundTag getTidePlayerData() {
        return playerData;
    }

    @Override
    public void setTidePlayerData(CompoundTag tag) {
        this.playerData = tag;
    }
}
