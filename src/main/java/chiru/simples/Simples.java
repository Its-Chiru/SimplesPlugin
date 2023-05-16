package chiru.simples;

import commands.MainCommand;
import events.Chat;
import events.Enter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

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

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(ChatColor.RED+"Plugin Desactivated | Made by : Chiru | Version : "+version);
    }
}
