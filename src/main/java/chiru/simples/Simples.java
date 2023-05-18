package chiru.simples;

import commands.MainCommand;
import events.Chat;
import events.Enter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Simples extends JavaPlugin {

    PluginDescriptionFile pluginDescription = getDescription();
    public String name = pluginDescription.getName();
    public String version = pluginDescription.getVersion();

    //PAPI

    public boolean hasPapi;

    @Override
    public void onEnable() {

        // Plugin startup logic

        System.out.println(ChatColor.GREEN+"Plugin Activated | Made by : Chiru | Version : "+version);

        //Config

        saveDefaultConfig();

        //Register Events

        getServer().getPluginManager().registerEvents(new Enter(this), this);
        getServer().getPluginManager().registerEvents(new Chat(this), this);

        //Register Commands

        getCommand("simples").setExecutor(new MainCommand(this));

        //Place Holder Api

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            hasPapi = true;
        }
        else {
            hasPapi = false;
        }

        //Tab Scheduler
        tabScheduleLoad();

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(ChatColor.RED+"Plugin Desactivated | Made by : Chiru | Version : "+version);
    }

    //Tab loads, this event will be called from other classes to be loaded again!

    public void tabLoad(Player player){
        if(getConfig().getBoolean("Config.tab.enabled") == true){
            List<String> headerLines = getConfig().getStringList("Config.tab.header");

            String combinedHeader = String.join("\n", headerLines);

            String coloredHeader = ChatColor.translateAlternateColorCodes('&', combinedHeader);

            //Lo mismo para el footer

            List<String> footerLines = getConfig().getStringList("Config.tab.footer");

            String combinedFooter = String.join("\n", footerLines);

            String coloredFooter = ChatColor.translateAlternateColorCodes('&', combinedFooter);

            //Finally actually setting the tab visible

            if(hasPapi == true){
                String papiHeader = PlaceholderAPI.setPlaceholders(player, coloredHeader);
                String papiFooter = PlaceholderAPI.setPlaceholders(player, coloredFooter);
                player.setPlayerListHeaderFooter(papiHeader, papiFooter);
            }
            else {
                player.setPlayerListHeaderFooter(coloredHeader, coloredFooter);
            }        }
    }

    //Tab load after a time!

    public void tabScheduleLoad(){
        int tabSeconds = getConfig().getInt("Config.tab.seconds");
        Bukkit.getScheduler().runTaskTimer(this, () ->{
            for(Player player : Bukkit.getOnlinePlayers()){
                tabLoad(player);
                System.out.println(player.getName()+" Reloaded tab!");
            }
        }, 0, tabSeconds * 20);
    }

}
