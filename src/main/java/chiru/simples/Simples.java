package chiru.simples;

import commands.MainCommand;
import commands.MainCommandTAB;
import events.Chat;
import events.Enter;
import fr.mrmicky.fastboard.FastBoard;
import jdk.tools.jmod.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
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
        getCommand("simples").setTabCompleter(new MainCommandTAB());

        //Place Holder Api

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            hasPapi = true;
        }
        else {
            hasPapi = false;
        }

        //Tab Scheduler
        tabScheduleLoad();

        //Scoreboard Scheduler
        scoreboardScheduleLoad();

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
            }
        }, 0, tabSeconds * 20);
    }

    //Scoreboard

    public void scoreboardLoad(Player player) {
        // Scoreboard using FastBoard
        // Link: https://github.com/MrMicky-FR/FastBoard
        FastBoard board = new FastBoard(player);

        // Set title
        String title = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Config.scoreboard.title"));
        board.updateTitle(title);

        // Change Lines
        List<String> lines = getConfig().getStringList("Config.scoreboard.values");
        List<String> coloredLines = new ArrayList<>();
        List<String> papiLines = new ArrayList<>();
        for (String line : lines) {
            //Color Lines
            String coloredLine = ChatColor.translateAlternateColorCodes('&', line);
            //Check Placeholders
            if(hasPapi){
                String papiLine = PlaceholderAPI.setPlaceholders(player, coloredLine);
                papiLines.add(papiLine);

            }
            else {

                coloredLines.add(coloredLine);

            };
        }
        //Save lines
        //Check Placeholders Again
        if(hasPapi){
            board.updateLines(papiLines);
        }
        else {
            board.updateLines(coloredLines);
        }
    }

    //Scoreboard Schedule

    public void scoreboardScheduleLoad(){
        int tabSeconds = getConfig().getInt("Config.scoreboard.seconds");
        Bukkit.getScheduler().runTaskTimer(this, () ->{
            for(Player player : Bukkit.getOnlinePlayers()){
                scoreboardLoad(player);
            }
        }, 0, tabSeconds * 20);
    }


}
