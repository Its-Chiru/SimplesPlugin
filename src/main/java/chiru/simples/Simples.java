package chiru.simples;

import commands.Broadcast;
import commands.MainCommand;
import commands.MainCommandTAB;
import events.Chat;
import events.Enter;
import files.ScoreboardManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Score;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public final class Simples extends JavaPlugin {

    PluginDescriptionFile pluginDescription = getDescription();
    public String name = pluginDescription.getName();
    public String version = pluginDescription.getVersion();

    //PAPI

    public boolean hasPapi;

    //Scoreboard Scheduler

    public ScoreboardManager sm;


    @Override
    public void onEnable() {

        // Plugin startup logic

        System.out.println(ChatColor.GREEN+"Plugin Activated | Made by : Chiru | Version : "+version);

        //Config

        //This is for the default config.yml

        saveDefaultConfig();


        //Register Events

        getServer().getPluginManager().registerEvents(new Enter(this), this);
        getServer().getPluginManager().registerEvents(new Chat(this), this);

        //Register Commands

        getCommand("simples").setExecutor(new MainCommand(this));
        getCommand("simples").setTabCompleter(new MainCommandTAB());

        getCommand("broadcast").setExecutor(new Broadcast(this));

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
        sm = new ScoreboardManager(this);
        sm.scoreboardScheduleLoad();

        //UpdateChecker

        new UpdateChecker(this, 109934).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                System.out.println("You are on the latest plugin version!!");
            } else {
                System.out.println(ChatColor.RED+"There is a new update available!");
                System.out.println(ChatColor.GREEN+"We highly recommend you to update to the newest one here: https://www.spigotmc.org/resources/"+"109934");
            }
        });

        //Stats

        // All you have to do is adding the following two lines in your onEnable method.
        // You can find the plugin ids of your plugins on the page https://bstats.org/what-is-my-plugin-id
        int pluginId = 18533; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);


    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(ChatColor.RED+"Plugin Desactivated | Made by : Chiru | Version : "+version);
    }

    //Tab

    //Tab loads, this event will be called from other classes to be loaded again!

    public void tabLoad(Player player){
        if(getConfig().getBoolean("Config.tab.enabled") == true){

            //Name of the player

            String name = getConfig().getString("Config.tab.name");
            String coloredName = ChatColor.translateAlternateColorCodes('&',name);

            //Rest of tab

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
        int tabSeconds = getConfig().getInt("Config.tab.seconds");
        Bukkit.getScheduler().runTaskTimer(this, () ->{
            for(Player player : Bukkit.getOnlinePlayers()){
                tabLoad(player);
            }
        }, 0, tabSeconds * 20);
    }

    //UpdateChecker

    public class UpdateChecker {

        private final JavaPlugin plugin;
        private final int resourceId;

        public UpdateChecker(JavaPlugin plugin, int resourceId) {
            this.plugin = plugin;
            this.resourceId = resourceId;
        }

        public void getVersion(final Consumer<String> consumer) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                    if (scanner.hasNext()) {
                        consumer.accept(scanner.next());
                    }
                } catch (IOException exception) {
                    plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
                }
            });
        }
    }


}
