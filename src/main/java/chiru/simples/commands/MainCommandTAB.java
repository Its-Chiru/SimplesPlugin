package chiru.simples.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTAB implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias,  String[] args) {
        if(args.length == 1){

            List<String> arguments = new ArrayList<>();
            arguments.add("reload");
            arguments.add("version");
            arguments.add("support");
            arguments.add("toggle");
            return arguments;

        }
        if(args.length == 2 && args[0].equalsIgnoreCase("toggle")){

            List<String> arguments = new ArrayList<>();
            arguments.add("scoreboard");
            return arguments;

        }
        if(args.length == 3 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("scoreboard")){

            List<String> arguments = new ArrayList<>();
            arguments.add("on");
            arguments.add("off");
            return arguments;

        }
        return null;
    }
}
