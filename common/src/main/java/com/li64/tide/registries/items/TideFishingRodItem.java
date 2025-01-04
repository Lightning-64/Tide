package com.li64.tide.registries.items;

import com.google.common.collect.ImmutableList;
import com.li64.tide.Tide;
import com.li64.tide.client.gui.overlays.CastBarOverlay;
import com.li64.tide.client.gui.overlays.CatchMinigameOverlay;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.InteractionResultHolder;
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
        );
    }

    public boolean isLavaproof(ItemStack stack) {
        return CustomRodManager.getHook(stack).is(TideItems.LAVAPROOF_FISHING_HOOK) || stack.is(TideItems.NETHERITE_FISHING_ROD);
    }

    public static List<Component> getDescriptionLines(ItemStack stack) {
        ArrayList<Component> builder = new ArrayList<>();

        ItemStack bobber = CustomRodManager.getBobber(stack);
        ItemStack hook = CustomRodManager.getHook(stack);
        ItemStack line = CustomRodManager.getLine(stack);

        if (CustomRodManager.hasBobber(stack)) {
            MutableComponent bobberComponent = AccessoryData.getTranslation(bobber);
            builder.add(bobberComponent.withStyle(ChatFormatting.BLUE));
        }

        if (CustomRodManager.hasHook(stack)) {
            MutableComponent hookComponent = AccessoryData.getTranslation(hook);
            builder.add(hookComponent.withStyle(ChatFormatting.BLUE));
        }

        if (CustomRodManager.hasLine(stack)) {
            MutableComponent lineComponent = AccessoryData.getTranslation(line);
            builder.add(lineComponent.withStyle(ChatFormatting.BLUE));
        }

        if (!builder.isEmpty()) {
            builder.add(0, Component.translatable("text.tide.rod_tooltip.accessories_prefix").withStyle(ChatFormatting.GRAY));
            builder.add(0, Component.empty());
            builder.add(Component.empty());
        }

        return ImmutableList.copyOf(builder);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        return Optional.of(new FishingRodTooltip(getContents(stack)));
    }

    public static BaitContents getContents(ItemStack stack) {
        if (!stack.getOrCreateTag().contains("bait-contents")) setContents(stack, new BaitContents());
        return BaitContents.fromNbt(stack.getOrCreateTag().getCompound("bait-contents"));
    }

    public static void setContents(ItemStack stack, BaitContents contents) {
        stack.getOrCreateTag().put("bait-contents", contents.toNbt());
    }

    @Override
    public boolean overrideStackedOnOther(@NotNull ItemStack stack, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player) {
        if (action != ClickAction.SECONDARY) return false;
        else {
            BaitContents.Mutable contents = new BaitContents.Mutable(getContents(stack));
            ItemStack slotStack = slot.getItem();

            if (slotStack.isEmpty() && !contents.isEmpty()) {
                // place next stack
                ItemStack removedStack = contents.removeStack();
                if (removedStack != null) slot.safeInsert(removedStack);

            } else if (slotStack.getItem().canFitInsideContainerItems() && BaitUtils.isBait(slotStack)) {
                // insert stack
                contents.tryTransfer(slot, player);
            }

            setContents(stack, contents.toImmutable());
            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(@NotNull ItemStack stack, @NotNull ItemStack other, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player, @NotNull SlotAccess access) {
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            BaitContents.Mutable contents = new BaitContents.Mutable(getContents(stack));

            if (other.isEmpty()) {
                // pull next stack
                ItemStack itemstack = contents.removeStack();
                if (itemstack != null) access.set(itemstack);

            } else if (other.getItem().canFitInsideContainerItems() && BaitUtils.isBait(other)) {
                // insert stack
                contents.tryInsert(other);
            }

            setContents(stack, contents.toImmutable());
            return true;
        } else {
            return false;
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (isHookActive(player)) {
            TideFishingHook hook = getHook(player);

            if (isMinigameStopped(player, level.isClientSide()) && Tide.CONFIG.general.doMinigame) {
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
                        return InteractionResultHolder.consume(player.getItemInHand(hand));
                    retrieveHook(player.getItemInHand(hand), player, level);
                }

            } else {
                // Minigame is either active or disabled
                if (!Tide.CONFIG.general.doMinigame) {
                    if (!level.isClientSide()) hook.retrieve();
                } else if (level.isClientSide()) {
                    // Interact with minigame (from the client)
                    CatchMinigameOverlay.interact();
                }
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        } else {
            if (!level.isClientSide() && FishCatchMinigame.delayActive((ServerPlayer) player))
                return InteractionResultHolder.consume(player.getItemInHand(hand));

            if (Tide.CONFIG.general.holdToCast) {
                // Charge the cast if the hook isn't active
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE,
                        SoundSource.NEUTRAL, 1.5F, 0.3F / (level.getRandom().nextFloat() * 0.4F + 0.7F));
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(player.getItemInHand(hand));
            } else {
                // Cast the hook normally
                castHook(player.getItemInHand(hand), player, level, 1f);
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
            }
        }
    }

    private boolean isMinigameStopped(Player player, boolean clientSide) {
        return clientSide ? !CatchMinigameOverlay.isActive() : !FishCatchMinigame.minigameActive(player);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack rod, @NotNull Level level, @NotNull LivingEntity user, int charge) {
        if (user instanceof Player player) {

            int chargeDifference = this.getUseDuration(rod) - charge;

            // Actually cast the hook
            if (chargeDifference > getChargeDuration(rod)) chargeDifference = getChargeDuration(rod);
            float chargeMultiplier = ((float) chargeDifference / (float) getChargeDuration(rod)) + 0.5f;
            castHook(rod, player, level, chargeMultiplier);
        }
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
                int speed = EnchantmentHelper.getFishingSpeedBonus(rod);
                int luck = EnchantmentHelper.getFishingLuckBonus(rod);

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
                rod.hurtAndBreak(durabilityLoss, player, (playerTemp) -> playerTemp.broadcastBreakEvent(player.getUsedItemHand()));
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE,
                    SoundSource.NEUTRAL, 1.2F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        }
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity user, @NotNull ItemStack rod, int charge) {
        super.onUseTick(level, user, rod, charge);

        if (level.isClientSide() && user == Minecraft.getInstance().player) {
            int chargeDifference = this.getUseDuration(rod) - charge;
            if (chargeDifference > getChargeDuration(rod)) chargeDifference = getChargeDuration(rod);

            CastBarOverlay.rodChargeTick((float) chargeDifference / (float) getChargeDuration(rod));
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 60000;
    }

    public int getChargeDuration(ItemStack rod) {
        return CustomRodManager.getLine(rod).is(TideItems.BRAIDED_LINE) ? 15 : 25;
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }
}