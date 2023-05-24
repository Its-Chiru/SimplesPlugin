package chiru.simples.events;

import chiru.simples.Simples;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    private final Simples plugin;

    public Chat(Simples plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){

        String text = plugin.getConfig().getString("Config.chat-format");

        Player player = event.getPlayer();

        String formatedText = ChatColor.translateAlternateColorCodes('&',text.replace("{DISPLAY_NAME}", "%1$s").replace("{MESSAGE}","%2$s"));

        //If it has papi installed
        if(plugin.hasPapi == true){
            String papiText = formatedText.replace("{GROUP_PREFIX}", PlaceholderAPI.setPlaceholders(player,"%vault_groupprefix%"));
            String coloredPapiText = ChatColor.translateAlternateColorCodes('&',papiText);
            event.setFormat(coloredPapiText);
        }
        else {
            //If it doest
            event.setFormat(formatedText);
        }

    }
}
