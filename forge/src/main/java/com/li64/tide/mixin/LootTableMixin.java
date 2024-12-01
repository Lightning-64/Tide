package com.li64.tide.mixin;

import com.li64.tide.loot.LootTableAccessor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(LootTable.class)
public class LootTableMixin implements LootTableAccessor {
    @Shadow @Final private List<LootPool> pools;

    @Unique
    public @Nullable LootPool tide$getPool(int index) {
        return this.pools.get(index);
    }
}
