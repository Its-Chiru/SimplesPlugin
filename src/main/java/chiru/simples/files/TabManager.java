package chiru.simples.files;

import chiru.simples.Simples;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class TabManager {

    private final Simples plugin;

    public TabManager(Simples plugin) {
        this.plugin = plugin;
    }

    //Tab

    //Tab loads, this event will be called from other classes to be loaded again!

    public void tabLoad(Player player){
        if(plugin.getConfig().getBoolean("Config.tab.enabled") == true){

            //Name of the player

            String name = plugin.getConfig().getString("Config.tab.name");
            String coloredName = ChatColor.translateAlternateColorCodes('&',name);

            //Rest of tab

            List<String> headerLines = plugin.getConfig().getStringList("Config.tab.header");

            String combinedHeader = String.join("\n", headerLines);

            String coloredHeader = ChatColor.translateAlternateColorCodes('&', combinedHeader);

            //Lo mismo para el footer

            List<String> footerLines = plugin.getConfig().getStringList("Config.tab.footer");

            String combinedFooter = String.join("\n", footerLines);

            String coloredFooter = ChatColor.translateAlternateColorCodes('&', combinedFooter);

            //Finally actually setting the tab visible

            if(plugin.hasPapi == true){
                String papiHeader = PlaceholderAPI.setPlaceholders(player, coloredHeader);
                String papiFooter = PlaceholderAPI.setPlaceholders(player, coloredFooter);
                player.setPlayerListHeaderFooter(papiHeader, papiFooter);
                //For the name of the tab
                String papiName = PlaceholderAPI.setPlaceholders(player,coloredName);
                String coloredPapiName = ChatColor.translateAlternateColorCodes('&', papiName);
                player.setPlayerListName(coloredPapiName);
            }
            else {
                player.setPlayerListHeaderFooter(coloredHeader, coloredFooter);
                player.setPlayerListName(coloredName);
            }
        }
    }

    //Tab load after a time!

    public void tabScheduleLoad(){
        int tabSeconds = plugin.getConfig().getInt("Config.tab.seconds");
        Bukkit.getScheduler().runTaskTimer(plugin, () ->{
            for(Player player : Bukkit.getOnlinePlayers()){
                tabLoad(player);
            }
        }, 0, tabSeconds * 20);
    }

}
