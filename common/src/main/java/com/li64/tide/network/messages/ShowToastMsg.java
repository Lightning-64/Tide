package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.client.TideClientHelper;
import com.li64.tide.client.gui.TideToasts;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class ShowToastMsg {
    public static final ResourceLocation ID = Tide.resource("show_toast");

    public final Component title;
    public final Component description;
    public final ItemStack display;

    public ShowToastMsg(Component title, Component description, ItemStack display) {
        this.title = title;
        this.description = description;
        this.display = display;
    }

    public ShowToastMsg(FriendlyByteBuf buf) {
        title = Component.translatable(buf.readUtf());
        description = Component.translatable(buf.readUtf());
        display = BuiltInRegistries.ITEM.get(ResourceLocation.read(buf.readUtf()).get().orThrow()).getDefaultInstance();
    }

    public static void encode(ShowToastMsg message, FriendlyByteBuf buf) {
        buf.writeUtf(message.title.getString());
        buf.writeUtf(message.description.getString());
        buf.writeUtf(BuiltInRegistries.ITEM.getKey(message.display.getItem()).toString());
    }

    public static void handle(ShowToastMsg message, Player player) {
        TideClientHelper.showToast(message.title, message.description, message.display);
    }
}