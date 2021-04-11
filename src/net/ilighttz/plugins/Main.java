package net.ilighttz.plugins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("§a[LGuerreiro] foi ativado, desenvolvido por: github.com/ILighttz");
        getCommand("guerreiro").setExecutor(new GuerreiroCommands ());
    }


    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§a[LGuerreiro] foi desativado, desenvolvido por: github.com/ILighttz");
    }
}
