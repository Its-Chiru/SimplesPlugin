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

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player jugador = (Player) sender;

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("version")) {

				jugador.sendMessage("["+plugin.name+"]"+ChatColor.BLUE+" The version of the plugin is : "+ChatColor.WHITE+plugin.version);
				return true;

			}else if(args[0].equalsIgnoreCase("reload") && jugador.isOp()) {

				plugin.reloadConfig();
				for (Player player : Bukkit.getOnlinePlayers()){
					plugin.tabLoad(player);
					plugin.scoreboardLoad(player);
				}
				Bukkit.getConsoleSender().sendMessage("["+plugin.name+"]"+ChatColor.GREEN+" Config reloaded!");
				jugador.sendMessage("["+plugin.name+"]"+ChatColor.GREEN+" Config reloaded!");
				return true;


			}else if(args[0].equalsIgnoreCase("support")){

				jugador.sendMessage("["+plugin.name+"]"+ChatColor.GOLD+" Need help with our plugin?");
				jugador.sendMessage(ChatColor.WHITE+"Join our discord : "+ChatColor.BLUE+"https://discord.com/invite/JPcK4SN6RG");
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
