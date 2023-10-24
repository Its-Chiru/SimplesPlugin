package chiru.simples.events;

import chiru.simples.Simples;
import chiru.simples.files.PlayerDataConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Random;

public class Enter implements Listener{
	
    private final Simples plugin;
	
	public Enter(Simples plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void alEntrar(PlayerJoinEvent event) {

		//Values

		Player jugador = event.getPlayer();

		//Default Config Values
		FileConfiguration config = plugin.getConfig();
		String path = "Config.SimpleServerWelcome.enabled";
		Integer every = config.getInt("Config.SimpleServerWelcome.every");

		//PlayerDataConfig Values
		FileConfiguration PDconfig = PlayerDataConfig.get();

		// Checking if the message is enabled
		if (config.getBoolean(path)) {
			int enters = PDconfig.getInt("Players." + jugador.getUniqueId() + ".enters");
			if ((every != 0 && enters < every)) {
				// Adds one count to that player
				PDconfig.set("Players." + jugador.getUniqueId() + ".enters", enters + 1);
				if(!config.getBoolean(config.getString("Config.SimpleServerWelcome.display-default"))){
					event.setJoinMessage("");
				}
			} else if (enters >= every || every == 0) {
				// If it reaches that count, send message and restart value
				PDconfig.set("Players." + jugador.getUniqueId() + ".enters", 0);

				// Display the welcome message

// Inside your class or method
				List<String> welcomeMessages = config.getStringList("Config.SimpleServerWelcome.welcome-messages");
				String welcomeMessage;

				if (welcomeMessages.size() > 0) {
					// Randomly select a welcome message from the list
					Random random = new Random();
					welcomeMessage = welcomeMessages.get(random.nextInt(welcomeMessages.size()));
				} else {
					// Use a default welcome message if the list is empty
					welcomeMessage = config.getString("Config.SimpleServerWelcome.welcome-message");
				}

				if (plugin.hasPapi) {
					String texto = PlaceholderAPI.setPlaceholders(jugador, welcomeMessage);
					event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', texto));
				} else {
					event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', welcomeMessage));
				}
			}

			// Save the changes to the PlayerDataConfig file
			PlayerDataConfig.save();
		}else if(!config.getBoolean(config.getString("Config.SimpleServerWelcome.display-default"))){
			event.setJoinMessage("");
		}


		//Tab

		plugin.tab.tabLoad(jugador);

		//Scoreboard

		plugin.sm.scoreboardLoad(jugador);

	}

}
