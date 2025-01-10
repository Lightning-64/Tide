package com.li64.tide.data.minigame;

import com.li64.tide.Tide;
import com.li64.tide.network.messages.MinigameClientMsg;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.li64.tide.registries.items.StrengthFish;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FishCatchMinigame {
    private static final int SERVER_DELAY_MILLIS = 200;
    private static final ArrayList<FishCatchMinigame> ACTIVE_MINIGAMES = new ArrayList<>();
    private static final HashMap<ServerPlayer, Long> ACTIVE_DELAYS = new HashMap<>();

    private final TideFishingHook hook;
    private final ServerPlayer player;

    public static FishCatchMinigame getInstance(Player player) {
        for (FishCatchMinigame minigame : ACTIVE_MINIGAMES) {
            if (minigame.getPlayer() == player) return minigame;
        }
        return null;
    }

    public static FishCatchMinigame create(Player player) {
        FishCatchMinigame existingInstance = getInstance(player);
        if (existingInstance != null) return existingInstance;

        FishCatchMinigame minigame = new FishCatchMinigame((ServerPlayer) player);
        ACTIVE_MINIGAMES.add(minigame);
        return minigame;
    }

    public static boolean minigameActive(Player player) {
        for (FishCatchMinigame minigame : ACTIVE_MINIGAMES) {
            if (minigame.getPlayer() == player) return true;
        }
        return false;
    }

    protected FishCatchMinigame(ServerPlayer player) {
        this.player = player;
        this.hook = Objects.requireNonNull(HookAccessor.getHook(player));

        hook.setMinigameActive(true);

        float strength = 0f;
        if (hook.getHookedItem() instanceof StrengthFish strengthFish)
            strength = strengthFish.getStrength();

        if (hook.getHook().is(TideItems.IRON_FISHING_HOOK)) strength *= 0.89f;

        // Start client minigame gui
        Tide.NETWORK.sendToPlayer(new MinigameClientMsg(0, strength), player);
    }

    public static boolean delayActive(ServerPlayer player) {
        if (!ACTIVE_DELAYS.containsKey(player)) return false;
        if (System.currentTimeMillis() > ACTIVE_DELAYS.get(player)) {
            ACTIVE_DELAYS.remove(player);
            return false;
        } else return true;
    }

    private ServerPlayer getPlayer() {
        return player;
    }

    public void onFinish() {
        if (cancelIfNecessary()) return;
        hook.setMinigameActive(false);
        Tide.NETWORK.sendToPlayer(new MinigameClientMsg(2), player);
        ACTIVE_MINIGAMES.remove(this);
        ACTIVE_DELAYS.put(player, System.currentTimeMillis() + SERVER_DELAY_MILLIS);
    }

    public void onTimeout() {
        if (cancelIfNecessary()) return;
        onFinish();
    }

    public void onFail() {
        if (cancelIfNecessary()) return;
        if (Tide.CONFIG.minigame.doFailSound) hook.level().playSound(
                null, hook.getPlayerOwner().blockPosition(),
                SoundEvents.SHEEP_SHEAR, SoundSource.AMBIENT,
                0.9f, 1.0f);
        hook.invalidateCatch();
        hook.retrieve();
        onFinish();
    }

    public void onWin() {
        if (cancelIfNecessary()) return;
        if (Tide.CONFIG.minigame.doSuccessSound) hook.level().playSound(
                null, hook.getPlayerOwner().blockPosition(),
                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.AMBIENT,
                0.15f, 1.0f);
        hook.retrieve();
        onFinish();
    }

    public boolean cancelIfNecessary() {
        if (hook == null || player == null) {
            Tide.NETWORK.sendToPlayer(new MinigameClientMsg(2), player);
            ACTIVE_MINIGAMES.remove(this);
            return true;
        } else return false;
    }

    public void handleClientEvent(byte event) {
        cancelIfNecessary();
        switch (event) {
            case 0 -> onTimeout();
            case 1 -> onFail();
            case 2 -> onWin();
        }
    }
}
