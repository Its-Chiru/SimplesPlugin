package chiru.simples.commands.home;

import chiru.simples.Simples;
import chiru.simples.files.PlayerDataConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DelHome implements CommandExecutor {
    private final Simples plugin;

    public DelHome(Simples plugin) {
        this.plugin = plugin;
    }

    // PlayerDataConfig Values
    FileConfiguration PDconfig = PlayerDataConfig.get();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            // Sets values
            Player player = (Player) sender;
            UUID playerId = player.getUniqueId();

            // Check if player has a home location saved
            if (PDconfig.contains("Players." + playerId + ".home")) {
                // Deletes the home location
                PDconfig.set("Players." + playerId + ".home", null);

                // Save changes to the configuration
                PlayerDataConfig.save();

                // Sends a message
                player.sendMessage(ChatColor.RED + "Your home has been deleted!");
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
            } else {
                player.sendMessage(ChatColor.RED + "You don't have a home to delete!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
            }

        } else {
            // If the sender is not a player, you might want to send a message to indicate that the command cannot be executed from the console.
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
        }
        return true;
    }
}
