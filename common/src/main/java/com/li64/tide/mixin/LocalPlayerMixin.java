package com.li64.tide.mixin;

import com.li64.tide.client.gui.screens.TornNoteScreen;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.items.TornNoteItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow @Final protected Minecraft minecraft;

    @Inject(at = @At(value = "TAIL"), method = "openItemGui")
    public void openItemGui(ItemStack stack, InteractionHand hand, CallbackInfo ci) {
        if (stack.is(TideItems.TORN_NOTE)) minecraft.setScreen(new TornNoteScreen(TornNoteItem.getData(stack)));
    }
}