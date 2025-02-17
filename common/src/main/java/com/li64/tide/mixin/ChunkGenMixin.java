package com.li64.tide.mixin;

import com.li64.tide.Tide;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGenerator.class)
public class ChunkGenMixin {
    @Inject(method = "tryGenerateStructure", at = @At("HEAD"), cancellable = true)
    public void disableStructures$AttemptStructureDisable(
            StructureSet.StructureSelectionEntry entry,
            StructureManager manager, RegistryAccess registryAccess,
            RandomState random, StructureTemplateManager structureTemplateManager,
            long seed, ChunkAccess chunk, ChunkPos chunkPos, SectionPos sectionPos,
            ResourceKey<Level> level, CallbackInfoReturnable<Boolean> cir) {

        ResourceLocation structure = registryAccess.lookup(Registries.STRUCTURE).orElseThrow().getKey(entry.structure().value());
        if (structure == null) return;
        if (!structure.getNamespace().equals(Tide.MOD_ID)) return;

        if (structure.getPath().matches("fishing_boat") && Tide.CONFIG.worldgen.disableFishingBoat) cir.setReturnValue(false);
    }

    @Inject(method = "findNearestMapStructure", at = @At("HEAD"), cancellable = true)
    public void disableStructures$FindNoDisabledStructuresInsteadOfLooking(
            ServerLevel level, HolderSet<Structure> structureHolder,
            BlockPos blockPos, int $$3, boolean $$4,
            CallbackInfoReturnable<Pair<BlockPos, Holder<Structure>>> cir) {

        structureHolder.stream().forEach(holder -> {
            ResourceLocation structure = level.registryAccess().lookup(Registries.STRUCTURE).orElseThrow().getKey(holder.value());
            if (structure == null) return;
            if (!structure.getNamespace().equals(Tide.MOD_ID)) return;

            if (structure.getPath().matches("fishing_boat") && Tide.CONFIG.worldgen.disableFishingBoat) cir.setReturnValue(null);
        });
    }
}
