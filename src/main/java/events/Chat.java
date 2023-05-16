package events;

import chiru.simples.Simples;
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

        String chatformat = plugin.getConfig().getString("Config.chat-format");

        Player player = event.getPlayer();

        event.setFormat(chatformat.replace("{DISPLAY_NAME}", "%1$s").replace("{MESSAGE}","%2$s"));

    }
}
