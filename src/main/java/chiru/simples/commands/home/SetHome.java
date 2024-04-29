package chiru.simples.commands.home;

import chiru.simples.Simples;
import chiru.simples.files.PlayerDataConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetHome implements CommandExecutor {

    private final Simples plugin;

    public SetHome(Simples plugin) {
        this.plugin = plugin;
    }

    // PlayerDataConfig Values
    FileConfiguration PDconfig = PlayerDataConfig.get();
    //Actual Command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        //Checks if sender is a player
        if (sender instanceof Player) {
            //Sets Values
            Player player = (Player) sender;
            UUID id = player.getUniqueId();
            //Gets Location
            Location homeLocation = player.getLocation();
            //Sets on the playerdata file the location under the id of the player
            PDconfig.set("Players." + player.getUniqueId() + ".home", homeLocation);

            // Save the configuration
            PlayerDataConfig.save();

            //Plays Sound
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);

            // Send a message to the player
            player.sendMessage(ChatColor.GOLD+"Your home location has been set on "+ChatColor.RED+"( "+homeLocation.getBlockX()+","+homeLocation.getBlockY()+","+homeLocation.getBlockZ()+" )"+ChatColor.GOLD+"!");

        } else {
            // If the sender is not a player, you might want to send a message to indicate that the command cannot be executed from the console.
            sender.sendMessage("This command can only be executed by a player!");
        }
        return true;
    }
}
