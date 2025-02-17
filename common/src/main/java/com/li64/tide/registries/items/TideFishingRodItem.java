package com.li64.tide.registries.items;

import com.google.common.collect.ImmutableList;
import com.li64.tide.Tide;
import com.li64.tide.client.gui.overlays.CastBarOverlay;
import com.li64.tide.client.gui.overlays.CatchMinigameOverlay;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.minigame.FishCatchMinigame;
import com.li64.tide.data.rods.AccessoryData;
import com.li64.tide.data.rods.BaitContents;
import com.li64.tide.data.rods.CustomRodManager;
import com.li64.tide.data.rods.FishingRodTooltip;
import com.li64.tide.registries.TideEntityTypes;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.li64.tide.util.BaitUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TideFishingRodItem extends FishingRodItem {
    public static final ResourceLocation CAST_PROPERTY = Tide.resource("cast");

    public TideFishingRodItem(double baseDurability, Properties properties) {
        super(properties
                .durability((int) (baseDurability * (Tide.CONFIG == null ? 1.0 : Tide.CONFIG.general.rodDurabilityMultiplier)))
                .component(TideDataComponents.BAIT_CONTENTS, BaitContents.EMPTY)
        );
    }

    public static List<Component> getDescriptionLines(ItemStack stack, HolderLookup.Provider registries) {
        ArrayList<Component> builder = new ArrayList<>();

        ItemStack bobber = CustomRodManager.getBobber(stack, registries);
        ItemStack hook = CustomRodManager.getHook(stack, registries);
        ItemStack line = CustomRodManager.getLine(stack, registries);

        if (CustomRodManager.hasBobber(stack, registries)) {
            MutableComponent bobberComponent = AccessoryData.getTranslation(bobber);
            builder.add(bobberComponent.withStyle(ChatFormatting.BLUE));
        }

        if (CustomRodManager.hasHook(stack, registries)) {
            MutableComponent hookComponent = AccessoryData.getTranslation(hook);
            builder.add(hookComponent.withStyle(ChatFormatting.BLUE));
        }

        if (CustomRodManager.hasLine(stack, registries)) {
            MutableComponent lineComponent = AccessoryData.getTranslation(line);
            builder.add(lineComponent.withStyle(ChatFormatting.BLUE));
        }

        if (!builder.isEmpty()) {
            builder.addFirst(Component.translatable("text.tide.rod_tooltip.accessories_prefix")
                    .withStyle(ChatFormatting.GRAY));
            builder.addFirst(Component.empty());
            builder.add(Component.empty());
        }

        return ImmutableList.copyOf(builder);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
                ? Optional.ofNullable(stack.get(TideDataComponents.BAIT_CONTENTS)).map(FishingRodTooltip::new)
                : Optional.empty();
    }

    @Override
    public boolean overrideStackedOnOther(@NotNull ItemStack stack, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player) {
        if (action != ClickAction.SECONDARY) return false;
        else {
            BaitContents contents = stack.get(TideDataComponents.BAIT_CONTENTS);
            if (contents == null) return false;

            BaitContents.Mutable mutable = new BaitContents.Mutable(contents);
            ItemStack slotStack = slot.getItem();

            if (slotStack.isEmpty() && !mutable.isEmpty()) {
                // place next stack
                ItemStack removedStack = mutable.removeStack();
                if (removedStack != null) slot.safeInsert(removedStack);

            } else if (slotStack.getItem().canFitInsideContainerItems() && BaitUtils.isBait(slotStack)) {
                // insert stack
                mutable.tryTransfer(slot, player);
            }

            stack.set(TideDataComponents.BAIT_CONTENTS, mutable.toImmutable());
            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(@NotNull ItemStack stack, @NotNull ItemStack other, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player, @NotNull SlotAccess access) {
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            BaitContents contents = stack.get(TideDataComponents.BAIT_CONTENTS);
            if (contents == null) return false;

            BaitContents.Mutable mutableContents = new BaitContents.Mutable(stack.get(TideDataComponents.BAIT_CONTENTS));

            if (other.isEmpty()) {
                // pull next stack
                ItemStack itemstack = mutableContents.removeStack();
                if (itemstack != null) access.set(itemstack);

            } else if (other.getItem().canFitInsideContainerItems() && BaitUtils.isBait(other)) {
                // insert stack
                mutableContents.tryInsert(other);
            }

            stack.set(TideDataComponents.BAIT_CONTENTS, mutableContents.toImmutable());
            return true;
        } else {
            return false;
        }
    }

    public boolean isLavaproof(RegistryAccess registryAccess, ItemStack stack) {
        return CustomRodManager.getHook(stack, registryAccess).is(TideItems.LAVAPROOF_FISHING_HOOK)
                || (this == TideItems.NETHERITE_FISHING_ROD);
    }

    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (isHookActive(player)) {
            TideFishingHook hook = getHook(player);

            if (isMinigameStopped(player, level.isClientSide()) && Tide.CONFIG.minigame.doMinigame) {
                // No minigame active, create a new one if necessary

                if (Tide.PLATFORM.isModLoaded("stardew_fishing")) {
                    if (hook.getCatchType() == TideFishingHook.CatchType.FISH
                        || hook.getCatchType() == TideFishingHook.CatchType.ITEM) {

                        if (!level.isClientSide()) {
                            Tide.LOG.info("Starting stardew fishing minigame");

                            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE,
                                    SoundSource.NEUTRAL, 1.2F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

                            if (!Tide.PLATFORM.stardewStart((ServerPlayer) player, (HookAccessor) player.fishing,
                                    player.getItemInHand(hand), List.of(hook.getHookedItem().getDefaultInstance()))) {
                                retrieveHook(player.getItemInHand(hand), player, level);
                            }
                        }
                    } else retrieveHook(player.getItemInHand(hand), player, level);

                } else if (hook.getCatchType() == TideFishingHook.CatchType.FISH) {
                    if (!level.isClientSide() && isMinigameStopped(player, level.isClientSide())) {
                        Tide.LOG.info("Starting tide fishing minigame");
                        FishCatchMinigame.create(player);
                    }
                } else {
                    if (!level.isClientSide() && FishCatchMinigame.delayActive((ServerPlayer) player))
                        return InteractionResult.CONSUME;
                    retrieveHook(player.getItemInHand(hand), player, level);
                }

            } else {
                // Minigame is either active or disabled
                if (!Tide.CONFIG.minigame.doMinigame) {
                    if (!level.isClientSide()) hook.retrieve();
                } else if (level.isClientSide()) {
                    // Interact with minigame (from the client)
                    CatchMinigameOverlay.interact();
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            if (!level.isClientSide() && FishCatchMinigame.delayActive((ServerPlayer) player))
                return InteractionResult.CONSUME;

            if (Tide.CONFIG.general.holdToCast) {
                // Charge the cast if the hook isn't active
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE,
                        SoundSource.NEUTRAL, 1.5F, 0.3F / (level.getRandom().nextFloat() * 0.4F + 0.7F));
                player.startUsingItem(hand);
                return InteractionResult.CONSUME;
            } else {
                // Cast the hook normally
                castHook(player.getItemInHand(hand), player, level, 1f);
                return InteractionResult.SUCCESS;
            }
        }
    }

    private boolean isMinigameStopped(Player player, boolean clientSide) {
        return clientSide ? !CatchMinigameOverlay.isActive() : !FishCatchMinigame.minigameActive(player);
    }

    @Override
    public boolean releaseUsing(@NotNull ItemStack rod, @NotNull Level level, @NotNull LivingEntity user, int charge) {
        if (user instanceof Player player) {
            int chargeDifference = this.getUseDuration(rod, user) - charge;
            int chargeDuration = getChargeDuration(rod, level.registryAccess());

            // Actually cast the hook
            if (chargeDifference > chargeDuration) chargeDifference = chargeDuration;
            float chargeMultiplier = ((float) chargeDifference / (float) chargeDuration) + 0.5f;
            castHook(rod, player, level, chargeMultiplier);
            return true;
        }
        return false;
    }

    public boolean isHookActive(Player player) {
        TideFishingHook hook = HookAccessor.getHook(player);
        return hook != null;
    }

    public TideFishingHook getHook(Player player) {
        return HookAccessor.getHook(player);
    }

    public void castHook(ItemStack rod, Player player, Level level, float charge) {
        TideFishingHook activeHook = HookAccessor.getHook(player);
        if (activeHook == null) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW,
                    SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                int speed = (int)(EnchantmentHelper.getFishingTimeReduction((ServerLevel) level, rod, player) / 5f);
                int luck = EnchantmentHelper.getFishingLuckBonus((ServerLevel) level, rod, player);

                if (BaitUtils.isHoldingBait(rod)) {
                    speed += BaitUtils.getBaitSpeed(BaitUtils.getPrimaryBait(rod));
                    luck += BaitUtils.getBaitLuck(BaitUtils.getPrimaryBait(rod));
                }

                level.addFreshEntity(new TideFishingHook(TideEntityTypes.FISHING_BOBBER,
                        player, level, luck, speed, charge, rod));
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            player.gameEvent(GameEvent.ITEM_INTERACT_START);
        }
    }

    public void retrieveHook(ItemStack rod, Player player, Level level) {
        TideFishingHook activeHook = HookAccessor.getHook(player);
        if (activeHook != null) {
            if (!level.isClientSide) {
                int durabilityLoss = activeHook.retrieve(rod, (ServerLevel) level, player);
                rod.hurtAndBreak(durabilityLoss, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE,
                    SoundSource.NEUTRAL, 1.2F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        }
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack rod, int charge) {
        super.onUseTick(level, user, rod, charge);

        int chargeDuration = getChargeDuration(rod, level.registryAccess());

        if (level.isClientSide() && user == Minecraft.getInstance().player) {
            int chargeDifference = this.getUseDuration(rod, user) - charge;
            if (chargeDifference > chargeDuration) chargeDifference = chargeDuration;

            CastBarOverlay.rodChargeTick((float) chargeDifference / (float) chargeDuration);
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 60000;
    }

    public int getChargeDuration(ItemStack rod, HolderLookup.Provider registries) {
        return CustomRodManager.getLine(rod, registries).is(TideItems.BRAIDED_LINE) ? 15 : 25;
    }

    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
        return ItemUseAnimation.BOW;
    }
}