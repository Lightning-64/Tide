package com.li64.tide.mixin;

import com.li64.tide.Tide;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
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
    public void disableStructures$AttemptStructureDisable(StructureSet.StructureSelectionEntry entry, StructureManager structureManager, RegistryAccess registries, RandomState $$3, StructureTemplateManager $$4, long $$5, ChunkAccess $$6, ChunkPos $$7, SectionPos $$8, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation structure = registries.registryOrThrow(Registries.STRUCTURE).getKey(entry.structure().value());
        if (structure == null) return;
        if (!structure.getNamespace().equals(Tide.MOD_ID)) return;

        if (structure.getPath().matches("fishing_boat") && Tide.CONFIG.worldgen.disableFishingBoat) cir.setReturnValue(false);
        if (structure.getPath().matches("fishing_hut") && Tide.CONFIG.worldgen.disableFishingHut) cir.setReturnValue(false);
    }

    @Inject(method = "findNearestMapStructure", at = @At("HEAD"), cancellable = true)
    public void disableStructures$FindNoDisabledStructuresInsteadOfLooking(ServerLevel level, HolderSet<Structure> structureHolder, BlockPos $$2, int $$3, boolean $$4, CallbackInfoReturnable<Pair<BlockPos, Holder<Structure>>> cir) {
        structureHolder.stream().forEach(configuredStructureFeatureHolder -> {
            ResourceLocation structure = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(configuredStructureFeatureHolder.value());
            if (structure == null) return;
            if (!structure.getNamespace().equals(Tide.MOD_ID)) return;

            if (structure.getPath().matches("fishing_boat") && Tide.CONFIG.worldgen.disableFishingBoat) cir.setReturnValue(null);
            if (structure.getPath().matches("fishing_hut") && Tide.CONFIG.worldgen.disableFishingHut) cir.setReturnValue(null);
        });
    }
}
