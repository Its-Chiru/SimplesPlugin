package commands;

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

            return arguments;

        }
        return null;
    }
}
