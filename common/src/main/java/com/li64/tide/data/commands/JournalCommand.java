package com.li64.tide.data.commands;

import com.li64.tide.data.player.TidePlayerData;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class JournalCommand {
    public JournalCommand(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(Commands.literal("journal").requires((stack) -> stack.hasPermission(2))
            .then(Commands.literal("lock").executes((stackCommandContext) -> lock(stackCommandContext.getSource()))));
    }

    private int lock(CommandSourceStack stack) {
        try {
            TidePlayerData data = TidePlayerData.getOrCreate(stack.getPlayer());
            data.lockAllFish();
            data.syncTo(stack.getPlayer());
            stack.sendSuccess(() -> Component.translatable("commands.journal.lockfish",
                        stack.getPlayer().getDisplayName()), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}