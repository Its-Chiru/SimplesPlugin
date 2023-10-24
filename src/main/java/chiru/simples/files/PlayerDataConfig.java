package chiru.simples.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

//Tutorial from https://www.youtube.com/watch?v=3en6w7PNL08

public class PlayerDataConfig {

    //File here in coding
    private static File file;
    //File generated in sv w java
    private static FileConfiguration customFile;

    //Runs every time it starts up
    public static void setup(){
        //Gets the "player-data,yml"
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Simples").getDataFolder(), "player-data.yml");

        //If it doesnt exist generate it
        if(!file.exists()){
           try {
               file.createNewFile();
           }catch (IOException e){
               //Oww
           }
        }
        //Allows editing config
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Couldnt save file");
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration((file));
    }
}
