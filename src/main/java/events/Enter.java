package events;

import chiru.simples.Simples;
import chiru.simples.Simples;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Enter implements Listener{
	
private final Simples plugin;
	
	public Enter(Simples plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void alEntrar(PlayerJoinEvent event) {

	    Player jugador = event.getPlayer();
	   	    
	    FileConfiguration config = plugin.getConfig();
	    
	    String path = "Config.SimpleServerWelcome.enabled";
	    if(config.getString(path).equals("true")) {
	    	
	    	String texto = PlaceholderAPI.setPlaceholders(jugador, config.getString("Config.SimpleServerWelcome.welcome-message"));
			event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', texto));
	    	
	    }

	}


}
