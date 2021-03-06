/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.nolongnames;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class NoLongNames extends JavaPlugin implements Listener {

    private int maxNameLength;
    private String kickMessage;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    private void reload() {
        reloadConfig();
        maxNameLength = getConfig().getInt("maxnamelength");
        kickMessage = getConfig().getString("kickmessage");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reloadnln")) {
            reload();
            sender.sendMessage("NoLongName's config has been reloaded.");
            sender.sendMessage("Will kick players with names longer than " + maxNameLength + " characters with kick message:");
            sender.sendMessage("'" + kickMessage + "'");
        } else {
            sender.sendMessage("NoLongNames doesn't know about the command /" + cmd);
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent pje) {
        if (pje.getPlayer().getName().length() > maxNameLength) {
            pje.getPlayer().kickPlayer(kickMessage);
        }
    }
}
