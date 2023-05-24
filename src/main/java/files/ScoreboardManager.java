package files;

import chiru.simples.Simples;
import fr.mrmicky.fastboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {

    private final Simples plugin;
    private BukkitTask scoreboardTask;

    public ScoreboardManager(Simples plugin) {
        this.plugin = plugin;
    }

    //Scoreboard

    public void scoreboardLoad(Player player) {
        // Scoreboard using FastBoard
        // Link: https://github.com/MrMicky-FR/FastBoard
        FastBoard board = new FastBoard(player);

        // Set title
        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Config.scoreboard.title"));
        board.updateTitle(title);

        // Change Lines
        List<String> lines = plugin.getConfig().getStringList("Config.scoreboard.values");
        List<String> coloredLines = new ArrayList<>();
        List<String> papiLines = new ArrayList<>();
        for (String line : lines) {
            //Color Lines
            String coloredLine = ChatColor.translateAlternateColorCodes('&', line);
            //Check Placeholders
            if(plugin.hasPapi){
                String papiLine = PlaceholderAPI.setPlaceholders(player, coloredLine);
                papiLines.add(papiLine);

            }
            else {

                coloredLines.add(coloredLine);

            };
        }
        //Save lines
        //Check Placeholders Again
        if(plugin.hasPapi){
            board.updateLines(papiLines);
        }
        else {
            board.updateLines(coloredLines);
        }
        //Scoreboard Scheduler turn on again.
        if(scoreboardTask == null){
            scoreboardScheduleLoad();
        }
    }

    //Scoreboard Schedule

    public void scoreboardScheduleLoad(){
        int tabSeconds = plugin.getConfig().getInt("Config.scoreboard.seconds");
        scoreboardTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreboardLoad(player);
            }
        }, 0, tabSeconds * 20);
    }

    //Hide Scoreboard

    public void hideScoreboard(Player player){
        FastBoard board = new FastBoard(player);
        //Stop schedule
        if(scoreboardTask != null){
            scoreboardTask.cancel();
        }

    }

}
