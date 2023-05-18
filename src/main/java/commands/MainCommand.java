package commands;

import chiru.simples.Simples;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor{
	
	private Simples plugin;
	
	public MainCommand(Simples plugin) {

		this.plugin = plugin;

	}

	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		Player jugador = (Player) sender;
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("version")) {
				
				jugador.sendMessage("["+plugin.name+"]"+ChatColor.GOLD+" The version of the plugin is : "+ChatColor.WHITE+plugin.version);
				return true;
				
			}else if(args[0].equalsIgnoreCase("reload") && jugador.isOp()) {
				
				plugin.reloadConfig();
				for (Player player : Bukkit.getOnlinePlayers()){
					plugin.tabLoad(player);
				}
				Bukkit.getConsoleSender().sendMessage("["+plugin.name+"]"+ChatColor.GREEN+" Config reloaded!");
				jugador.sendMessage("["+plugin.name+"]"+ChatColor.GREEN+" Config reloaded!");
				return true;
				
			}else {
				
				jugador.sendMessage("["+plugin.name+"]"+ChatColor.RED+" That command doesnt exist!");
				return true;
				
			}
		}else {
			jugador.sendMessage("["+plugin.name+"]"+ChatColor.RED+" That command doesnt exist, try another one!");
			return true;
		}
	}
}
