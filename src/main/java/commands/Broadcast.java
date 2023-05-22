package commands;

import chiru.simples.Simples;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Broadcast implements CommandExecutor {

    private Simples plugin;

    public Broadcast(Simples plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand( CommandSender sender,  Command command,  String s,  String[] args) {
        String message = String.join(" ", args); // Concatenate the arguments into a single message

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("Config.broadcast")+message)); // Send the broadcast message to all players


        return true; // Return "true" to indicate that the command has been executed successfully
    }
}
