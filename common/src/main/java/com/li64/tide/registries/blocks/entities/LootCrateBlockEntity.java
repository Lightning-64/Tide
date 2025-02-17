package com.li64.tide.registries.blocks.entities;

import com.li64.tide.Tide;
import com.li64.tide.registries.blocks.AbstractLootCrateBlock;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LootCrateBlockEntity extends RandomizableContainerBlockEntity {
    private float fishingLuck;
    private Vec3 pullOrigin;
    private UUID player;
    private ItemStack fishingRod;
    private UUID fishingHook;
    private boolean isFresh;

    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            LootCrateBlockEntity.this.playSound(state, SoundEvents.BARREL_OPEN);
            LootCrateBlockEntity.this.updateBlockState(state, true);
        }

        @Override
        protected void onClose(Level level, BlockPos pos, BlockState state) {
            LootCrateBlockEntity.this.playSound(state, SoundEvents.BARREL_CLOSE);
            LootCrateBlockEntity.this.updateBlockState(state, false);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155069_, int p_155070_) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu)player.containerMenu).getContainer();
                return container == LootCrateBlockEntity.this;
            } else {
                return false;
            }
        }
    };

    public LootCrateBlockEntity(BlockEntityType<? extends LootCrateBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putFloat("FishingLuck", this.fishingLuck);
        tag.putBoolean("IsFresh", this.isFresh);

        if (this.pullOrigin != null) {
            Vec3.CODEC.encodeStart(NbtOps.INSTANCE, this.pullOrigin)
                    .resultOrPartial(Tide.LOG::error)
                    .ifPresent(compound -> tag.put("PullOrigin", compound));
        }

        if (this.fishingRod != null) {
            ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, this.fishingRod)
                    .resultOrPartial(Tide.LOG::error)
                    .ifPresent(compound -> tag.put("FishingRod", compound));
        }

        if (this.player != null) tag.putUUID("PlayerID", this.player);
        if (this.fishingHook != null) tag.putUUID("HookID", this.fishingHook);

        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items, registries);
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("FishingLuck")) this.fishingLuck = tag.getFloat("FishingLuck");
        if (tag.contains("IsFresh")) this.isFresh = tag.getBoolean("IsFresh");

        if (tag.contains("PullOrigin")) {
            Vec3.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("PullOrigin"))
                    .resultOrPartial(Tide.LOG::error)
                    .ifPresent(value -> this.pullOrigin = value);
        }

        if (tag.contains("FishingRod")) {
            ItemStack.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("FishingRod"))
                    .resultOrPartial(Tide.LOG::error)
                    .ifPresent(value -> this.fishingRod = value);
        }

        if (tag.contains("PlayerID")) this.player = tag.getUUID("PlayerID");
        if (tag.contains("HookID")) this.fishingHook = tag.getUUID("HookID");

        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items, registries);
        }
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.tide.loot_crate");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int var, @NotNull Inventory inventory) {
        return ChestMenu.threeRows(var, inventory, this);
    }

    @Override
    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean value) {
        this.level.setBlock(this.getBlockPos(), state.setValue(AbstractLootCrateBlock.OPEN, Boolean.valueOf(value)), 3);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        double d0 = (double)this.worldPosition.getX() + 0.5;
        double d1 = (double)this.worldPosition.getY() + 1.0;
        double d2 = (double)this.worldPosition.getZ() + 0.5;
        this.level.playSound(null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    public float getFishingLuck() {
        return fishingLuck;
    }

    public Vec3 getPullOrigin() {
        return pullOrigin;
    }

    public Player getPlayer(ServerLevel level) {
        return level.getPlayerByUUID(this.player);
    }

    public ItemStack getFishingRod() {
        return fishingRod;
    }

    public @Nullable TideFishingHook getFishingHook(ServerLevel level) {
        Entity entity = level.getEntity(this.fishingHook);
        if (entity instanceof TideFishingHook hook) return hook;
        else return null;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public void markNotFresh() {
        this.isFresh = false;
        this.setChanged();
    }

    public void setCrateParams(float luck, Vec3 pullOrigin, Player player, ItemStack fishingRod, TideFishingHook fishingHook) {
        if (!(player instanceof ServerPlayer)) return;
        this.fishingLuck = luck;
        this.pullOrigin = pullOrigin;
        this.player = player.getUUID();
        this.fishingRod = fishingRod;
        this.fishingHook = fishingHook.getUUID();
        this.isFresh = true;
        this.setChanged();
    }
}