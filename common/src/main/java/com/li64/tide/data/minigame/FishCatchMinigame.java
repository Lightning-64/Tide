package com.li64.tide.data.minigame;

import com.li64.tide.Tide;
import com.li64.tide.data.rods.HookModifier;
import com.li64.tide.network.messages.MinigameClientMsg;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.li64.tide.registries.items.StrengthFish;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Objects;

public class FishCatchMinigame {
    public static ArrayList<FishCatchMinigame> ACTIVE_MINIGAMES = new ArrayList<>();

    protected TideFishingHook hook;
    protected ServerPlayer player;

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

        if (hook.getHookModifier() == HookModifier.IRON) strength *= 0.89f;

        // Start client minigame gui
        Tide.NETWORK.sendToPlayer(new MinigameClientMsg(0, strength), player);
    }

    private ServerPlayer getPlayer() {
        return player;
    }

    public void interact() {
        if (cancelIfNecessary()) return;
        // Request a result from the client (either win or fail)
        Tide.NETWORK.sendToPlayer(new MinigameClientMsg(1), player);
    }

    public void onFinish() {
        if (cancelIfNecessary()) return;
        hook.setMinigameActive(false);
        Tide.NETWORK.sendToPlayer(new MinigameClientMsg(2), player);
        ACTIVE_MINIGAMES.remove(this);
    }

    public void onTimeout() {
        if (cancelIfNecessary()) return;
        onFinish();
    }

    public void onFail() {
        if (cancelIfNecessary()) return;
        hook.invalidateCatch();
        hook.retrieve();
        onFinish();
    }

    public void onWin() {
        if (cancelIfNecessary()) return;
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
