package com.li64.tide.mixin;

import com.li64.tide.data.TidePlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class TidePlayerMixin implements TidePlayer {
    @Unique public CompoundTag tide$playerData = new CompoundTag();
    @Unique public String tide$dataKey = "TidePersistentData";

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        if (!compound.contains(tide$dataKey)) compound.put(tide$dataKey, tide$playerData);
        else tide$playerData = compound.getCompound(tide$dataKey);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.put(tide$dataKey, tide$playerData);
    }

    @Override
    public CompoundTag tide$getTidePlayerData() {
        return tide$playerData;
    }

    @Override
    public void tide$setTidePlayerData(CompoundTag tag) {
        this.tide$playerData = tag;
    }
}
