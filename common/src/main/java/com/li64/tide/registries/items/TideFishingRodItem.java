package com.li64.tide.registries.items;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.overlays.CastBarOverlay;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.minigame.FishCatchMinigame;
import com.li64.tide.data.rods.CustomRodManager;
import com.li64.tide.registries.TideEntityTypes;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.li64.tide.util.TideUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class TideFishingRodItem extends FishingRodItem {
    public static final ResourceLocation CAST_PROPERTY = Tide.resource("cast");

    public TideFishingRodItem(double baseDurability, Properties properties) {
        super(properties.durability((int) (baseDurability *
                (Tide.CONFIG == null ? 1.0 : Tide.CONFIG.general.rodDurabilityMultiplier))));
    }

    public boolean isLavaproof(RegistryAccess registryAccess, ItemStack stack) {
        return CustomRodManager.getHook(stack, registryAccess).is(TideItems.LAVAPROOF_FISHING_HOOK);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (isHookActive(player)) {
            TideFishingHook hook = getHook(player);

            if (!isMinigameActive(player) && Tide.CONFIG.general.doMinigame) {
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
                    if (!level.isClientSide() && !isMinigameActive(player)) {
                        Tide.LOG.info("Starting tide fishing minigame");
                        FishCatchMinigame.create(player);
                    }
                } else retrieveHook(player.getItemInHand(hand), player, level);

            } else {
                // Minigame is either active or disabled
                if (!level.isClientSide()) {
                    if (Tide.CONFIG.general.doMinigame) {
                        FishCatchMinigame minigame = FishCatchMinigame.getInstance(player);
                        if (minigame != null) {
                            minigame.interact();
                        }
                    } else {
                        hook.retrieve();
                    }
                }
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        } else {
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

    private boolean isMinigameActive(Player player) {
        return FishCatchMinigame.minigameActive(player);
    }

    @Override
    public void releaseUsing(ItemStack rod, Level level, LivingEntity user, int charge) {
        if (user instanceof Player player) {

            int chargeDifference = this.getUseDuration(rod, user) - charge;
            int chargeDuration = getChargeDuration(rod, level.registryAccess());

            // Actually cast the hook
            if (chargeDifference > chargeDuration) chargeDifference = chargeDuration;
            float chargeMultiplier = ((float) chargeDifference / (float) chargeDuration) + 0.5f;
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
                int speed = (int)(EnchantmentHelper.getFishingTimeReduction((ServerLevel) level, rod, player) / 5f);
                int luck = EnchantmentHelper.getFishingLuckBonus((ServerLevel) level, rod, player);

                if (TideUtils.isHoldingBait(player)) {
                    speed += TideUtils.getBaitSpeed(TideUtils.getHeldBaitItem(player));
                    luck += TideUtils.getBaitLuck(TideUtils.getHeldBaitItem(player));
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
    public void onUseTick(Level level, LivingEntity user, ItemStack rod, int charge) {
        super.onUseTick(level, user, rod, charge);

        int chargeDuration = getChargeDuration(rod, level.registryAccess());

        if (level.isClientSide() && user == Minecraft.getInstance().player) {
            int chargeDifference = this.getUseDuration(rod, user) - charge;
            if (chargeDifference > chargeDuration) chargeDifference = chargeDuration;

            CastBarOverlay.rodChargeTick((float) chargeDifference / (float) chargeDuration);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 60000;
    }

    public int getChargeDuration(ItemStack rod, HolderLookup.Provider registries) {
        return CustomRodManager.getLine(rod, registries).is(TideItems.BRAIDED_LINE) ? 15 : 25;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        ItemStack bobber = CustomRodManager.getBobber(stack, context.registries());
        ItemStack hook = CustomRodManager.getHook(stack, context.registries());
        ItemStack line = CustomRodManager.getLine(stack, context.registries());

        MutableComponent bobberComponent = CustomRodManager.getTranslation(bobber);
        tooltip.add(bobberComponent.withStyle(bobberComponent.getStyle()
                .withItalic(true)
                .withColor(ChatFormatting.YELLOW)));

        MutableComponent hookComponent = CustomRodManager.getTranslation(hook);
        tooltip.add(hookComponent.withStyle(hookComponent.getStyle()
                .withItalic(true)
                .withColor(ChatFormatting.YELLOW)));

        MutableComponent lineComponent = CustomRodManager.getTranslation(line);
        tooltip.add(lineComponent.withStyle(lineComponent.getStyle()
                .withItalic(true)
                .withColor(ChatFormatting.YELLOW)));
    }
}