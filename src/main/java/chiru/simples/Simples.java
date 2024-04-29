package chiru.simples;

import chiru.simples.commands.*;
import chiru.simples.commands.home.DelHome;
import chiru.simples.commands.home.Home;
import chiru.simples.commands.home.SetHome;
import chiru.simples.events.Chat;
import chiru.simples.events.Enter;
import chiru.simples.files.PlayerDataConfig;
import chiru.simples.files.ScoreboardManager;
import chiru.simples.files.TabManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
//Allot of help from https://youtube.com/@KodySimpson

public final class Simples extends JavaPlugin {

    PluginDescriptionFile pluginDescription = getDescription();
    public String name = pluginDescription.getName();
    public String version = pluginDescription.getVersion();

    //PAPI

    public boolean hasPapi;

    //Scoreboard
    public ScoreboardManager sm;

    //Tab
    public TabManager tab;

    @Override
    public void onEnable() {

        // Plugin startup logic

        System.out.println(ChatColor.GREEN+"Plugin Activated | Made by : Chiru | Version : "+version);

        //Config

        //This is for the default config.yml

        saveDefaultConfig();

        //This is for player-data.yml

        PlayerDataConfig.setup();
        PlayerDataConfig.get().options().copyDefaults(true);
        PlayerDataConfig.save();

        //Register Events

        getServer().getPluginManager().registerEvents(new Enter(this), this);
        getServer().getPluginManager().registerEvents(new Chat(this), this);

        //Register Commands

        getCommand("simples").setExecutor(new MainCommand(this));
        getCommand("simples").setTabCompleter(new MainCommandTAB());

        getCommand("broadcast").setExecutor(new Broadcast(this));

        getCommand("home").setExecutor(new Home(this));
        getCommand("delhome").setExecutor(new DelHome(this));
        getCommand("sethome").setExecutor(new SetHome(this));

        //Place Holder Api

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            hasPapi = true;
        }
        else {
            hasPapi = false;
        }

        //Tab Scheduler
        tab = new TabManager(this);
        tab.tabScheduleLoad();

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
