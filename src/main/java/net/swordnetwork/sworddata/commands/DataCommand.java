package net.swordnetwork.sworddata.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.swordnetwork.sworddata.SwordData;
import net.swordnetwork.sworddata.util.Util;

import java.sql.ResultSet;
import java.util.UUID;

public class DataCommand extends Command {

    public DataCommand() {
        super("data");
    }



    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 2) {
            switch (args[0]) {
                case "get" -> {
                    String player = args[1];
                    String key = args[2];

                    UUID uuid = Util.getUUID(player);

                    String result = SwordData.getInstance().getHelper().getData(uuid, key);
                    sender.sendMessage(new TextComponent("Data > " + player + ": " + key + " = " + result));
                }
                case "set" -> {
                    if (args.length > 3) {
                        String player = args[1];
                        String key = args[2];
                        String value = args[3];

                        UUID uuid = Util.getUUID(player);
                    } else {
                        sender.sendMessage(new TextComponent(ChatColor.RED + "Usage: /data <get|set> <player> <key> [value]"));
                    }
                }
                default -> {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Usage: /data <get|set> <player> <key> [value]"));
                }
            }
        }
    }
}